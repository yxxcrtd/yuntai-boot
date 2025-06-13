package com.joyintech.yuntai.module.cfg.service.pageformconfig;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pageformconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageformconfig.PageFormConfigDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 表单页配置 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageFormConfigService {

    /**
     * 创建表单页配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageFormConfig(@Valid PageFormConfigSaveReqVO createReqVO);

    /**
     * 更新表单页配置
     *
     * @param updateReqVO 更新信息
     */
    void updatePageFormConfig(@Valid PageFormConfigSaveReqVO updateReqVO);

    /**
     * 删除表单页配置
     *
     * @param id 编号
     */
    void deletePageFormConfig(Long id);

    /**
     * 获得表单页配置
     *
     * @param id 编号
     * @return 表单页配置
     */
    PageFormConfigDO getPageFormConfig(Long id);

    /**
     * 获得表单页配置分页
     *
     * @param pageReqVO 分页查询
     * @return 表单页配置分页
     */
    PageResult<PageFormConfigDO> getPageFormConfigPage(PageFormConfigPageReqVO pageReqVO);

}