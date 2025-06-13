package com.joyintech.yuntai.module.cfg.service.pageattachmentuploadfile;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentuploadfile.PageAttachmentUploadfileDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 表单页配置-附件管理-指定上传文件 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageAttachmentUploadfileService {

    /**
     * 创建表单页配置-附件管理-指定上传文件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageAttachmentUploadfile(@Valid PageAttachmentUploadfileSaveReqVO createReqVO);

    /**
     * 更新表单页配置-附件管理-指定上传文件
     *
     * @param updateReqVO 更新信息
     */
    void updatePageAttachmentUploadfile(@Valid PageAttachmentUploadfileSaveReqVO updateReqVO);

    /**
     * 删除表单页配置-附件管理-指定上传文件
     *
     * @param id 编号
     */
    void deletePageAttachmentUploadfile(Long id);

    /**
     * 获得表单页配置-附件管理-指定上传文件
     *
     * @param id 编号
     * @return 表单页配置-附件管理-指定上传文件
     */
    PageAttachmentUploadfileDO getPageAttachmentUploadfile(Long id);

    /**
     * 获得表单页配置-附件管理-指定上传文件分页
     *
     * @param pageReqVO 分页查询
     * @return 表单页配置-附件管理-指定上传文件分页
     */
    PageResult<PageAttachmentUploadfileDO> getPageAttachmentUploadfilePage(PageAttachmentUploadfilePageReqVO pageReqVO);

}