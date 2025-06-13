package com.joyintech.yuntai.module.cfg.dal.mysql.pageattachmentuploadfile;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.PageAttachmentUploadfilePageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentuploadfile.PageAttachmentUploadfileDO;

/**
 * 表单页配置-附件管理-指定上传文件 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageAttachmentUploadfileMapper extends BaseMapperX<PageAttachmentUploadfileDO> {

    default PageResult<PageAttachmentUploadfileDO> selectPage(PageAttachmentUploadfilePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageAttachmentUploadfileDO>()
                .eqIfPresent(PageAttachmentUploadfileDO::getAttachmentId, reqVO.getAttachmentId())
                .eqIfPresent(PageAttachmentUploadfileDO::getFileTypeId, reqVO.getFileTypeId())
                .eqIfPresent(PageAttachmentUploadfileDO::getFileTypeText, reqVO.getFileTypeText())
                .eqIfPresent(PageAttachmentUploadfileDO::getIsRequire, reqVO.getIsRequire())
                .eqIfPresent(PageAttachmentUploadfileDO::getIsValidFileName, reqVO.getIsValidFileName())
                .betweenIfPresent(PageAttachmentUploadfileDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PageAttachmentUploadfileDO::getId));
    }

    void updateDeletedByAttachmentId(@Param("attachmentId") List<Long> attachmentIdList);
}