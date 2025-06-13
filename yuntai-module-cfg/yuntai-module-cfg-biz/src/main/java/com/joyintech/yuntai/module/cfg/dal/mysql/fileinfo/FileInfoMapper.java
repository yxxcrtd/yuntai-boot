package com.joyintech.yuntai.module.cfg.dal.mysql.fileinfo;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.fileinfo.FileInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.fileinfo.vo.*;

/**
 * 上传附件 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface FileInfoMapper extends BaseMapperX<FileInfoDO> {

    default PageResult<FileInfoDO> selectPage(FileInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FileInfoDO>()
                .eqIfPresent(FileInfoDO::getPageId, reqVO.getPageId())
                .eqIfPresent(FileInfoDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(FileInfoDO::getFlowNodeId, reqVO.getFlowNodeId())
                .eqIfPresent(FileInfoDO::getFileType, reqVO.getFileType())
                .likeIfPresent(FileInfoDO::getFileName, reqVO.getFileName())
                .eqIfPresent(FileInfoDO::getUploadUserId, reqVO.getUploadUserId())
                .betweenIfPresent(FileInfoDO::getUploadDate, reqVO.getUploadDate())
                .eqIfPresent(FileInfoDO::getIsRequire, reqVO.getIsRequire())
                .eqIfPresent(FileInfoDO::getFileId, reqVO.getFileId())
                .eqIfPresent(FileInfoDO::getFileUrl, reqVO.getFileUrl())
                .eqIfPresent(FileInfoDO::getFileSize, reqVO.getFileSize())
                .eqIfPresent(FileInfoDO::getAttachmentType, reqVO.getAttachmentType())
                .betweenIfPresent(FileInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileInfoDO::getId));
    }

}