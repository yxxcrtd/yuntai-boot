package com.joyintech.yuntai.module.system.service.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.joyintech.yuntai.framework.common.enums.CommonStatusEnum;
import com.joyintech.yuntai.framework.common.util.collection.CollectionUtils;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.common.util.tree.TreeUtil;
import com.joyintech.yuntai.framework.datapermission.core.annotation.DataPermission;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.system.api.dept.dto.DeptRespDTO;
import com.joyintech.yuntai.module.system.api.user.dto.AdminUserRespDTO;
import com.joyintech.yuntai.module.system.api.user.dto.DeptUserTreeNode;
import com.joyintech.yuntai.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dept.vo.dept.DeptUserRespVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dept.DeptDO;
import com.joyintech.yuntai.module.system.dal.dataobject.user.AdminUserDO;
import com.joyintech.yuntai.module.system.dal.mysql.dept.DeptMapper;
import com.joyintech.yuntai.module.system.dal.mysql.user.AdminUserMapper;
import com.joyintech.yuntai.module.system.dal.redis.RedisKeyConstants;
import com.google.common.annotations.VisibleForTesting;
import com.joyintech.yuntai.module.system.sysuser.mapper.SysDeptMapper;
import com.joyintech.yuntai.module.system.sysuser.mapper.SysUserMapper;
import com.joyintech.yuntai.module.system.sysuser.model.SysDepart;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;
import com.joyintech.yuntai.module.system.sysuser.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.framework.common.util.collection.CollectionUtils.convertSet;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.*;

/**
 * 部门 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Value("${yuntainew.system.dbswitch}")
    private Boolean dbswitch;

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public Long createDept(DeptSaveReqVO createReqVO) {
        if (createReqVO.getParentId() == null) {
            createReqVO.setParentId(DeptDO.PARENT_ID_ROOT);
        }
        // 校验父部门的有效性
        validateParentDept(null, createReqVO.getParentId());
        // 校验部门名的唯一性
        validateDeptNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入部门
        DeptDO dept = BeanUtils.toBean(createReqVO, DeptDO.class);
        deptMapper.insert(dept);
        return dept.getId();
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public void updateDept(DeptSaveReqVO updateReqVO) {
        if (updateReqVO.getParentId() == null) {
            updateReqVO.setParentId(DeptDO.PARENT_ID_ROOT);
        }
        // 校验自己存在
        validateDeptExists(updateReqVO.getId());
        // 校验父部门的有效性
        validateParentDept(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验部门名的唯一性
        validateDeptNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新部门
        DeptDO updateObj = BeanUtils.toBean(updateReqVO, DeptDO.class);
        deptMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public void deleteDept(Long id) {
        // 校验是否存在
        validateDeptExists(id);
        // 校验是否有子部门
        if (deptMapper.selectCountByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
        // 删除部门
        deptMapper.deleteById(id);
    }

    @VisibleForTesting
    void validateDeptExists(Long id) {
        if (id == null) {
            return;
        }
        DeptDO dept = deptMapper.selectById(id);
        if (dept == null) {
            throw exception(DEPT_NOT_FOUND);
        }
    }

    @VisibleForTesting
    void validateParentDept(Long id, Long parentId) {
        if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父部门
        if (Objects.equals(id, parentId)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 2. 父部门不存在
        DeptDO parentDept = deptMapper.selectById(parentId);
        if (parentDept == null) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父部门，如果父部门是自己的子部门，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentDept.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(DEPT_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父部门
            if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentDept = deptMapper.selectById(parentId);
            if (parentDept == null) {
                break;
            }
        }
    }

    @VisibleForTesting
    void validateDeptNameUnique(Long id, Long parentId, String name) {
        DeptDO dept = deptMapper.selectByParentIdAndName(parentId, name);
        if (dept == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的部门
        if (id == null) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dept.getId(), id)) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
    }

    @Override
    public DeptDO getDept(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<DeptDO> getDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return deptMapper.selectBatchIds(ids);
    }

    @Override
    public List<DeptDO> getDeptList(DeptListReqVO reqVO) {
        if(dbswitch){
            List<DeptDO> sysDeptList = sysUserService.getDeptListNew(reqVO);
            return sysDeptList;
        }
        List<DeptDO> list = deptMapper.selectList(reqVO);
        list.sort(Comparator.comparing(DeptDO::getSort));
        return list;
    }

    @Override
    public List<DeptUserRespVO> getSelectDeptList(Long deptId,String name,String userIds) {
        List<String> userIdList = new ArrayList<>();
        if(StrUtil.isNotBlank(userIds)){
            userIdList = Arrays.asList(userIds.split(","));
        }
        List<AdminUserDO> userList = userMapper.selectList(new LambdaQueryWrapperX<AdminUserDO>()
                        .inIfPresent(AdminUserDO::getId, userIdList).eqIfPresent(AdminUserDO::getDeptId,deptId)
                        .likeIfPresent(AdminUserDO::getNickname, name));
        List<DeptUserRespVO> result1 = new ArrayList<>();
        if (CollUtil.isEmpty(userList)) {
            return result1;
        }
        if (Objects.nonNull(deptId)) {
            userList.forEach(user -> {
                DeptUserRespVO dept = new DeptUserRespVO();
                dept.setId(user.getId());
                dept.setName(user.getNickname());
                dept.setParentId(deptId);
                dept.setType("user");
                dept.setSort(1000000);
                dept.setIsLeaf(true);
                result1.add(dept);
            });
            return result1;
        }
        List<DeptDO> list = deptMapper.selectList();
        List<DeptUserRespVO> result = BeanUtils.toBean(list, DeptUserRespVO.class);
        Map<Long,List<AdminUserDO>> deptUserMap = userList.stream().filter(item->Objects.nonNull(item.getDeptId())).collect(Collectors.groupingBy(AdminUserDO::getDeptId));
        if (StrUtil.isEmpty(name)) {
            result.forEach(user -> user.setType("dept"));
            if(StrUtil.isEmpty(userIds)) {
                result.sort(Comparator.comparing(DeptUserRespVO::getSort));
            }else {
                Set<DeptUserRespVO> set = new HashSet<>();
                deptUserMap.forEach((k,v) -> {
                    if (CollUtil.isNotEmpty(v)) {
                        v.forEach(user -> {
                            DeptUserRespVO dept = new DeptUserRespVO();
                            dept.setId(user.getId());
                            dept.setName(user.getNickname());
                            dept.setParentId(k);
                            dept.setType("user");
                            dept.setSort(1000000);
                            dept.setIsLeaf(true);
                            set.add(dept);
                        });
                    }
                });
                result.addAll(set);
            }
            return result;
        }
        for (DeptUserRespVO dept : result) {
            for (DeptUserRespVO dept1 : result) {
                if (Objects.equals(dept.getParentId(), dept1.getId())) {
                    if (CollUtil.isNotEmpty(dept.getChildren())) {
                        dept.getChildren().add(dept1);
                    } else {
                        dept.setChildren(Lists.newArrayList(dept1));
                    }
                }
            }
            dept.setType("dept");
        }
        Map<Long,DeptUserRespVO> map = result.stream().collect(Collectors.toMap(DeptUserRespVO::getId, Function.identity()));
        Set<DeptUserRespVO> set = new HashSet<>();
        deptUserMap.forEach((k,v) -> {
            getParent(map.get(k),map,set);
            if (CollUtil.isNotEmpty(v)) {
                v.forEach(user -> {
                    DeptUserRespVO dept = new DeptUserRespVO();
                    dept.setId(user.getId());
                    dept.setName(user.getNickname());
                    dept.setParentId(k);
                    dept.setType("user");
                    dept.setSort(1000000);
                    dept.setIsLeaf(true);
                    set.add(dept);
                });
            }
        });
        result1.addAll(set);
        result1.sort(Comparator.comparing(DeptUserRespVO::getSort));
        return result1;
    }

    @Override
    public Map<String, DeptDO> getDeptMap(Collection<Long> ids) {
        if(dbswitch){
            List<DeptDO> deptDOS = sysUserService.getDeptListNew(ids);
            return CollectionUtils.convertMap(deptDOS, DeptDO::getIdStr);
        }
        List<DeptDO> list = getDeptList(ids);
        return CollectionUtils.convertMap(list, DeptDO::getIdStr);
    }


    /**
     * 递归获取所有父节点
     * @param node
     * @param map
     * @param set
     */
    public void getParent(DeptUserRespVO node,Map<Long,DeptUserRespVO> map,Set<DeptUserRespVO> set) {
        if (node == null) {
            return;
        }
        set.add(node);
        // 递归打印父节点，直到达到根节点
        if (node.getParentId() != 0) {
            getParent(map.get(node.getParentId()),map,set);
        }
    }


    @Override
    public List<DeptDO> getChildDeptList(Long id) {
        List<DeptDO> children = new LinkedList<>();
        // 遍历每一层
        Collection<Long> parentIds = Collections.singleton(id);
        for (int i = 0; i < Short.MAX_VALUE; i++) { // 使用 Short.MAX_VALUE 避免 bug 场景下，存在死循环
            // 查询当前层，所有的子部门
            List<DeptDO> depts = deptMapper.selectListByParentId(parentIds);
            // 1. 如果没有子部门，则结束遍历
            if (CollUtil.isEmpty(depts)) {
                break;
            }
            // 2. 如果有子部门，继续遍历
            children.addAll(depts);
            parentIds = convertSet(depts, DeptDO::getId);
        }
        return children;
    }

    @Override
    @DataPermission(enable = false) // 禁用数据权限，避免建立不正确的缓存
    @Cacheable(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST, key = "#id")
    public Set<Long> getChildDeptIdListFromCache(Long id) {
        List<DeptDO> children = getChildDeptList(id);
        return convertSet(children, DeptDO::getId);
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        Map<String, DeptDO> deptMap = getDeptMap(ids);
        // 校验
        ids.forEach(id -> {
            DeptDO dept = deptMap.get(id);
            if (dept == null) {
                throw exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
                throw exception(DEPT_NOT_ENABLE, dept.getName());
            }
        });
    }

    @Override
    public List<DeptUserTreeNode> buildDeptUserTree() {
        // 获取所有部门
        List<SysDepart> depts = sysDeptMapper.selectList();
        // 获取所有用户
        List<SysUser> users = sysUserMapper.selectList();
        // 将部门转换为 Map，方便查找
        Map<String, DeptUserTreeNode> deptMap = new HashMap<>();
        for (SysDepart dept : depts) {
            DeptUserTreeNode node = new DeptUserTreeNode(dept.getId(), dept.getDepartName());
            deptMap.put(dept.getId(), node);
        }
        // 将用户分配到对应的部门
        for (SysUser user : users) {
            AdminUserRespDTO adminUserRespDTO = new AdminUserRespDTO();
            adminUserRespDTO.setId(user.getId());
            adminUserRespDTO.setNickname(user.getRealname());
            adminUserRespDTO.setUsername(user.getUsername());
            adminUserRespDTO.setStatus(user.getStatus());
            adminUserRespDTO.setDeptId(user.getDepartId());
            adminUserRespDTO.setMobile(user.getPhone());

            String deptId = user.getDepartId();
            if (deptMap.containsKey(deptId)) {
                deptMap.get(deptId).addUser(adminUserRespDTO);
            }
        }
        // 构建树形结构
        List<DeptUserTreeNode> rootNodes = new ArrayList<>();
        for (SysDepart dept : depts) {
            DeptUserTreeNode node = deptMap.get(dept.getId());
            if ("0".equals(dept.getParentId())) {
                rootNodes.add(node);
            } else {
                DeptUserTreeNode parent = deptMap.get(dept.getParentId());
                if (parent != null) {
                    parent.addChild(node);
                }
            }
        }
        return rootNodes;
    }

}
