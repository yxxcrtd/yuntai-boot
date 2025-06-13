package com.joyintech.yuntai.module.cfg.service.pagegroup;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面分组 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageGroupService {

    /**
     * 创建页面分组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageGroup(@Valid PageGroupSaveReqVO createReqVO);

    /**
     * 更新页面分组
     *
     * @param updateReqVO 更新信息
     */
    void updatePageGroup(@Valid PageGroupSaveReqVO updateReqVO);

    /**
     * 删除页面分组
     *
     * @param id 编号
     */
    void deletePageGroup(Long id);

    /**
     * 获得页面分组
     *
     * @param id 编号
     * @return 页面分组
     */
    PageGroupDO getPageGroup(Long id);

    /**
     * 获得页面分组分页
     *
     * @param pageReqVO 分页查询
     * @return 页面分组分页
     */
    PageResult<PageGroupDO> getPageGroupPage(PageGroupPageReqVO pageReqVO);

}