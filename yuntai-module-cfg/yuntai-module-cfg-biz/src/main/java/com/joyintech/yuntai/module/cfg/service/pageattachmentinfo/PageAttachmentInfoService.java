package com.joyintech.yuntai.module.cfg.service.pageattachmentinfo;

import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentinfo.PageAttachmentInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;

/**
 * 表单页配置-附件管理 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageAttachmentInfoService {

    /**
     * 创建表单页配置-附件管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageAttachmentInfo(@Valid PageAttachmentInfoSaveReqVO createReqVO);

    /**
     * 更新表单页配置-附件管理
     *
     * @param updateReqVO 更新信息
     */
    void updatePageAttachmentInfo(@Valid PageAttachmentInfoSaveReqVO updateReqVO);

    /**
     * 删除表单页配置-附件管理
     *
     * @param id 编号
     */
    void deletePageAttachmentInfo(Long id);

    /**
     * 获得表单页配置-附件管理
     *
     * @param id 编号
     * @return 表单页配置-附件管理
     */
    PageAttachmentInfoDO getPageAttachmentInfo(Long id);

    /**
     * 获得表单页配置-附件管理分页
     *
     * @param pageReqVO 分页查询
     * @return 表单页配置-附件管理分页
     */
    PageResult<PageAttachmentInfoDO> getPageAttachmentInfoPage(PageAttachmentInfoPageReqVO pageReqVO);

}