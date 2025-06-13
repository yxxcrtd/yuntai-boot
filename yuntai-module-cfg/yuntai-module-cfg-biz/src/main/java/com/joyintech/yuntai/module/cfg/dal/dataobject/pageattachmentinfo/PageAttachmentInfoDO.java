package com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentinfo;

import java.math.BigDecimal;
import java.util.List;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 表单页配置-附件管理 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_attachment_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageAttachmentInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 附件模式
     */
    private String attachmentType;
    /**
     * 是否必传
     */
    private String isRequire;
    /**
     * 是否允许下载
     */
    private String isAllowDownload;
    /**
     * 是否允许预览
     */
    private String isAllowPreview;
    /**
     * 允许上传的文件类型
     */
    private String allowFileSuffixCode;

    /**
     * 上传文件大小范围
     */
    private String allowSizeCode;

    /**
     * 列表页ID
     */
    private Long pageId;

    /**
     * 文件类型（字典）
     */
    private String fileSourceDict;

    /**
     * 是否允许选择多个文件
     */
    private String isAllowMultiple;

    /**
     * 最大文件限制
     */
    private BigDecimal maxSize;

    /**
     * 最大文件限制单位
     */
    private String maxSizeUnit;

    /**
     * 上传提示语
     */
    private String uploadTips;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 上传文件过滤类型配置
     */
    private String filterFileTypeValue;

    /**
     * 是否查询业务系统附件
     */
    private Boolean isQueryBusinessFileList;

    /**
     * 附件业务类型
     */
    private String dirType;

    /**
     * 是否禁止上传
     */
    private Boolean isNotAllowUpload;
}