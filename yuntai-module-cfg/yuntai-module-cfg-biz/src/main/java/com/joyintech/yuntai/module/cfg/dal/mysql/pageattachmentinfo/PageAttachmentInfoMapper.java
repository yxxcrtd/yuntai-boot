package com.joyintech.yuntai.module.cfg.dal.mysql.pageattachmentinfo;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentinfo.PageAttachmentInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.*;

/**
 * 表单页配置-附件管理 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface PageAttachmentInfoMapper extends BaseMapperX<PageAttachmentInfoDO> {

    default PageResult<PageAttachmentInfoDO> selectPage(PageAttachmentInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PageAttachmentInfoDO>()
                .eqIfPresent(PageAttachmentInfoDO::getAttachmentType, reqVO.getAttachmentType())
                .eqIfPresent(PageAttachmentInfoDO::getIsRequire, reqVO.getIsRequire())
                .eqIfPresent(PageAttachmentInfoDO::getIsAllowDownload, reqVO.getIsAllowDownload())
                .eqIfPresent(PageAttachmentInfoDO::getIsAllowPreview, reqVO.getIsAllowPreview())
                .eqIfPresent(PageAttachmentInfoDO::getAllowFileSuffixCode, reqVO.getAllowFileSuffixCode())
                .eqIfPresent(PageAttachmentInfoDO::getAllowSizeCode, reqVO.getAllowSizeCode())
                .eqIfPresent(PageAttachmentInfoDO::getFileSourceDict, reqVO.getFileSourceDict())
                .betweenIfPresent(PageAttachmentInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PageAttachmentInfoDO::getPageId, reqVO.getPageId())
                .orderByDesc(PageAttachmentInfoDO::getId));
    }

}