package com.joyintech.yuntai.module.cfg.service.moduleapi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo.ModuleApiPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo.ModuleApiSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleApiParamVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapi.ModuleApiDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleapi.ModuleApiMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleapiparam.ModuleApiParamMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleinfo.ModuleInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduletable.ModuleTableMapper;
import com.joyintech.yuntai.module.cfg.enums.CfgParamTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.MODULE_API_NOT_EXISTS;

/**
 * 模型API Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ModuleApiServiceImpl implements ModuleApiService {
    @Resource
    private ModuleApiMapper moduleApiMapper;
    @Resource
    private ModuleApiParamMapper moduleApiParamMapper;
    @Resource
    private ModuleTableMapper moduleTableMapper;
    @Resource
    private ModuleInfoMapper moduleInfoMapper;

    @Override
    public Long createModuleApi(ModuleApiSaveReqVO createReqVO) {
        // 插入
        ModuleApiDO moduleApi = BeanUtils.toBean(createReqVO, ModuleApiDO.class);
        moduleApiMapper.insert(moduleApi);
        // 返回
        return moduleApi.getId();
    }

    @Override
    public void updateModuleApi(ModuleApiSaveReqVO updateReqVO) {
        // 校验存在
        validateModuleApiExists(updateReqVO.getId());
        // 更新
        ModuleApiDO updateObj = BeanUtils.toBean(updateReqVO, ModuleApiDO.class);
        moduleApiMapper.updateById(updateObj);
    }

    @Override
    public void deleteModuleApi(Long id) {
        // 校验存在
        validateModuleApiExists(id);
        // 删除
        moduleApiMapper.deleteById(id);
    }

    private void validateModuleApiExists(Long id) {
        if (moduleApiMapper.selectById(id) == null) {
            throw exception(MODULE_API_NOT_EXISTS);
        }
    }

    @Override
    public ModuleApiDO getModuleApi(Long id) {
        return moduleApiMapper.selectById(id);
    }

    @Override
    public PageResult<ModuleApiDO> getModuleApiPage(ModuleApiPageReqVO pageReqVO) {
        if (Objects.nonNull(pageReqVO.getMenuId())) {
           List<ModuleInfoDO> list = moduleInfoMapper.selectList(ModuleInfoDO::getMenuId, pageReqVO.getMenuId());
            if (CollUtil.isNotEmpty(list)) {
                Set<Long> moduleIds = list.stream().map(ModuleInfoDO::getId).collect(Collectors.toSet());
                pageReqVO.setModuleIdSet(moduleIds);
            }else {
                return PageResult.empty();
            }
        }else {//没有菜单的时候不返回api列表
            return PageResult.empty();
        }
        return moduleApiMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ModuleApiDO> getModuleApiList(Long moduleId) {
        return moduleApiMapper.selectList(ModuleApiDO::getModuleId, moduleId);
    }

    @Override
    public List<ModuleApiParamVO> getModuleApiParamList(Long apiId) {
        List<ModuleApiParamVO> list = moduleApiParamMapper.selectByApiId(apiId);
        if(CollUtil.isNotEmpty(list)) {
            //List<ModuleTableSaveReqVO> tables = moduleTableMapper.selectTable(list.get(0).getModuleId());
            //Map<Long, List<ModuleTableSaveReqVO>> tabMap = tables.stream().collect(Collectors.groupingBy(ModuleTableSaveReqVO::getId));
            list.forEach(item -> {
                //if(Objects.nonNull(item.getParentModuleTableId())) {
                //    item.setParentModuleTableName(tabMap.get(item.getParentModuleTableId()).get(0).getTableName());
                //}
                item.setParamType(CfgParamTypeEnum.getByName(item.getParamType()));
            });
        }
        return list;
    }

    @Override
    public Map<Long,List<ModuleApiParamVO>> getParamList(String apiIds) {
        String[] apiIdArr = apiIds.split(",");
        Map<Long,List<ModuleApiParamVO>> map = new HashMap<>();
        for (String apiId : apiIdArr) {
            if (StrUtil.isNotEmpty(apiId)) {
                map.put(Long.valueOf(apiId), getModuleApiParamList(Long.valueOf(apiId)));
            }
        }
        return map;
    }
}
