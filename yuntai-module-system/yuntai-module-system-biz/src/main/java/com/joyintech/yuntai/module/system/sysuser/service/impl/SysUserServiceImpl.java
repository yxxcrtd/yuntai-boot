package com.joyintech.yuntai.module.system.sysuser.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import com.joyintech.yuntai.module.system.controller.admin.user.vo.user.UserPageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dept.DeptDO;
import com.joyintech.yuntai.module.system.dal.dataobject.user.AdminUserDO;
import com.joyintech.yuntai.module.system.sysuser.mapper.SysDeptMapper;
import com.joyintech.yuntai.module.system.sysuser.mapper.SysUserMapper;
import com.joyintech.yuntai.module.system.sysuser.model.SysDepart;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;
import com.joyintech.yuntai.module.system.sysuser.service.ISysUserService;
import com.joyintech.yuntai.module.system.util.PasswordUtil;
import com.joyintech.yuntai.module.system.util.VerifyTools;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private Environment environment;

    @Override
    public List<DeptDO> getDeptListNew(DeptListReqVO reqVO) {
        List<SysDepart> sysDeptList = sysDeptMapper.selectList(new LambdaQueryWrapperX<SysDepart>()
                .likeIfPresent(SysDepart::getDepartName, reqVO.getName())
                .eqIfPresent(SysDepart::getStatus, reqVO.getStatus()==null||reqVO.getStatus()==0?"1":"0")
                .eq(SysDepart::getDelFlag, "0"));
        List<DeptDO> deptDOS = sysDeptList.stream().map(sysDepart ->
                DeptDO.builder()
                        .idStr(sysDepart.getId())
                        .name(sysDepart.getDepartName())
                        .parentId(Long.valueOf(sysDepart.getParentId()!=null?sysDepart.getParentId():"-1"))
                        .status("1".equals(sysDepart.getStatus())?0:1)
                        .phone(sysDepart.getMobile())
//                        .sort(Integer.valueOf(sysDepart.getsort()))
//                        .leaderUserId(Long.valueOf(sysDepart.getLeaderUserId()))
//                        .email(sysDepart.getEmail())
                        .build()
        ).collect(Collectors.toList());

        if(deptDOS!=null && !deptDOS.isEmpty()){
            for(DeptDO dept : deptDOS) {
                try {
                    dept.setId(Long.valueOf(dept.getIdStr()));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }

        return deptDOS;
    }

    @Override
    public List<DeptDO> getDeptListNew(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        Collection<String> idss = ids.stream().distinct().map(item->item.toString()).collect(Collectors.toList());
        List<SysDepart> sysDeptList = sysDeptMapper.selectList(new LambdaQueryWrapperX<SysDepart>()
                     .inIfPresent(SysDepart::getId, idss)
                    .eq(SysDepart::getDelFlag, "0"));

        List<DeptDO> deptDOS = sysDeptList.stream().map(sysDepart ->
                DeptDO.builder()
                        .idStr(sysDepart.getId())
                        .name(sysDepart.getDepartName())
                        .parentId(Long.valueOf(sysDepart.getParentId()!=null?sysDepart.getParentId():"-1"))
                        .status("1".equals(sysDepart.getStatus())?0:1)
                        .phone(sysDepart.getMobile())
//                        .sort(Integer.valueOf(sysDepart.getsort()))
//                        .leaderUserId(Long.valueOf(sysDepart.getLeaderUserId()))
//                        .email(sysDepart.getEmail())
                        .build()
        ).collect(Collectors.toList());

        if(deptDOS!=null && !deptDOS.isEmpty()){
            for(DeptDO dept : deptDOS) {
                try {
                    dept.setId(Long.valueOf(dept.getIdStr()));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }

        return deptDOS;
    }


    @Override
    public List<AdminUserDO> getUserListByStatusNew(Integer status) {
        //AdminUserDO的status、对应SysUser的delFlag
        List<SysUser> sysUsers = sysUserMapper.selectList(SysUser::getDelFlag, status);
        List<AdminUserDO> adminUserDOS = sysUsers.stream().map(sysUser ->
                AdminUserDO.builder()
                        .id(Long.valueOf(sysUser.getId()))
                        .username(sysUser.getUsername())
                        .password(sysUser.getPassword())
                        .nickname(sysUser.getRealname())
                        .deptId(Long.valueOf(sysUser.getDepartId()))
                        .email(sysUser.getEmail())
                        .mobile(sysUser.getPhone())
                        .sex(sysUser.getSex())
                        .avatar(sysUser.getAvatar())
                        .status(sysUser.getStatus()==1?0:1)
//                      .remark(sysUser.getRemark())
//                      .postIds(sysUser.getPostIds())
//                      .loginIp(sysUser.getLoginIp())
//                      .loginDate(sysUser.getLoginDate())
                        .build()
        ).collect(Collectors.toList());
        return adminUserDOS;
    }

    @Override
    public PageResult<AdminUserDO> getUserPageNew(UserPageReqVO reqVO, Collection<Long> deptIds) {
        PageResult<SysUser> sysUsers2 = sysUserMapper.selectPage(reqVO, new LambdaQueryWrapperX<SysUser>()
                .likeIfPresent(SysUser::getUsername, reqVO.getUsername())
                .likeIfPresent(SysUser::getPhone, reqVO.getMobile())
                .eqIfPresent(SysUser::getDelFlag, reqVO.getStatus())
                .betweenIfPresent(SysUser::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(SysUser::getDepartId, deptIds)
                .orderByDesc(SysUser::getId));
        PageResult<AdminUserDO> pageResult = new PageResult<>();
        pageResult.setTotal(sysUsers2.getTotal());
        pageResult.setList(sysUsers2.getList().stream().map(sysUser ->
                AdminUserDO.builder()
                        .id(Long.valueOf(sysUser.getId()))
                        .username(sysUser.getUsername())
                        .password(sysUser.getPassword())
                        .nickname(sysUser.getRealname())
                        .deptId(Long.valueOf(sysUser.getDepartId()))
                        .email(sysUser.getEmail())
                        .mobile(sysUser.getPhone())
                        .sex(sysUser.getSex())
                        .avatar(sysUser.getAvatar())
                        .status(sysUser.getStatus()==1?0:1)
//                      .remark(sysUser.getRemark())
//                      .postIds(sysUser.getPostIds())
//                      .loginIp(sysUser.getLoginIp())
//                      .loginDate(sysUser.getLoginDate())
                        .build()
        ).collect(Collectors.toList()));

        return pageResult;
    }

    @Override
    public SysUser getUserByUsername(String username) {
        return sysUserMapper.selectOne(SysUser::getUsername,username);
    }

    @Override
    public String create(String digestType, String id, String userCode, String password, String salt) {
        if (digestType == null) {
            digestType = getDefaultDigestType();
        }
        if ("SHA-256".equalsIgnoreCase(digestType)) {
            return PasswordUtil.encryptSha256(userCode, password, salt);
        } else if ("SM3".equals(digestType)) {
            return PasswordUtil.encryptSm3(userCode, password, salt);
        } else {
            return PasswordUtil.encryptDefault(userCode, password, salt);
        }
    }

    @Override
    public SysUser getUser(String id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public List<SysUser> getUserList(List<String> idList) {
        return sysUserMapper.selectList("id", idList);
    }

    private String getDefaultDigestType() {
        return this.getStringValue("security.password.digestType", "MD5");
    }

    public String getStringValue(String key, String defvalue) {
        String value = getStringValue(key);
        return VerifyTools.isBlank(value) ? defvalue : value;
    }

    public String getStringValue(String key) {
        return environment.getProperty(key);
    }
}
