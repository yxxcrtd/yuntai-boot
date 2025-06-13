package com.joyintech.yuntai.module.cfg.service.pagelinkage;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage.PageLinkageDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面联动配置 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageLinkageService {

    /**
     * 创建页面联动配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageLinkage(@Valid PageLinkageSaveReqVO createReqVO);

    /**
     * 更新页面联动配置
     *
     * @param updateReqVO 更新信息
     */
    void updatePageLinkage(@Valid PageLinkageSaveReqVO updateReqVO);

    /**
     * 删除页面联动配置
     *
     * @param id 编号
     */
    void deletePageLinkage(Long id);

    /**
     * 获得页面联动配置
     *
     * @param id 编号
     * @return 页面联动配置
     */
    PageLinkageDO getPageLinkage(Long id);

    /**
     * 获得页面联动配置分页
     *
     * @param pageReqVO 分页查询
     * @return 页面联动配置分页
     */
    PageResult<PageLinkageDO> getPageLinkagePage(PageLinkagePageReqVO pageReqVO);

}