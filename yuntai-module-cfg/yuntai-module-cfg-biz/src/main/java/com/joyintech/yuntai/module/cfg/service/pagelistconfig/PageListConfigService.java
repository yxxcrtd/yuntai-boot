package com.joyintech.yuntai.module.cfg.service.pagelistconfig;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistconfig.PageListConfigDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 列表页配置 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageListConfigService {

    /**
     * 创建列表页配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageListConfig(@Valid PageListConfigSaveReqVO createReqVO);

    /**
     * 更新列表页配置
     *
     * @param updateReqVO 更新信息
     */
    void updatePageListConfig(@Valid PageListConfigSaveReqVO updateReqVO);

    /**
     * 删除列表页配置
     *
     * @param id 编号
     */
    void deletePageListConfig(Long id);

    /**
     * 获得列表页配置
     *
     * @param id 编号
     * @return 列表页配置
     */
    PageListConfigDO getPageListConfig(Long id);

    /**
     * 获得列表页配置分页
     *
     * @param pageReqVO 分页查询
     * @return 列表页配置分页
     */
    PageResult<PageListConfigDO> getPageListConfigPage(PageListConfigPageReqVO pageReqVO);

}