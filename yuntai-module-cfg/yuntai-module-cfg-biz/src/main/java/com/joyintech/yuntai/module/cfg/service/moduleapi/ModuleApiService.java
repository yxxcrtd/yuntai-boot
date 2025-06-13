package com.joyintech.yuntai.module.cfg.service.moduleapi;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleapi.vo.*;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleApiParamVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapi.ModuleApiDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 模型API Service 接口
 *
 * @author 兆尹云台
 */
public interface ModuleApiService {

    /**
     * 创建模型API
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createModuleApi(@Valid ModuleApiSaveReqVO createReqVO);

    /**
     * 更新模型API
     *
     * @param updateReqVO 更新信息
     */
    void updateModuleApi(@Valid ModuleApiSaveReqVO updateReqVO);

    /**
     * 删除模型API
     *
     * @param id 编号
     */
    void deleteModuleApi(Long id);

    /**
     * 获得模型API
     *
     * @param id 编号
     * @return 模型API
     */
    ModuleApiDO getModuleApi(Long id);

    /**
     * 获得模型API分页
     *
     * @param pageReqVO 分页查询
     * @return 模型API分页
     */
    PageResult<ModuleApiDO> getModuleApiPage(ModuleApiPageReqVO pageReqVO);

    /**
     * 获得模型API分页
     *
     * @param moduleId 分页查询
     * @return 模型API分页
     */
    List<ModuleApiDO> getModuleApiList(Long moduleId);

    /**
     * 获得模型API参数
     *
     * @param apiId 分页查询
     * @return 模型API分页
     */
    List<ModuleApiParamVO> getModuleApiParamList(Long apiId);

    /**
     * 获得模型API参数
     *
     * @param apiIds 分页查询
     * @return 模型API分页
     */
    Map<Long,List<ModuleApiParamVO>> getParamList(String apiIds);


}
