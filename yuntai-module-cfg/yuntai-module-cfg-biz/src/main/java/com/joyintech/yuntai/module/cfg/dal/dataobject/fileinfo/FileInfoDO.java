package com.joyintech.yuntai.module.cfg.dal.dataobject.fileinfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 上传附件 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_file_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 列表页ID
     */
    private Long pageId;
    /**
     * 流程id
     */
    private String flowId;
    /**
     * 流程节点id
     */
    private String flowNodeId;
    /**
     * 附件文件类型
     */
    private String fileType;
    /**
     * 附件文件类型
     */
    private String fileTypeText;
    /**
     * 附件文件名称
     */
    private String fileName;
    /**
     * 上传人
     */
    private String uploadUserId;
    /**
     * 上传时间
     */
    private LocalDateTime uploadDate;
    /**
     * 是否必传
     */
    private Long isRequire;
    /**
     * 文件上传统一ID
     */
    private String fileId;
    /**
     * 文件地址
     */
    private String fileUrl;
    /**
     * 文件大小
     */
    private BigDecimal fileSize;

    /**
     * 附件类型
     */
    private String attachmentType;

    private String oriAttachmentId;


    /**
     * 流程节点名称
     */
    @TableField(exist = false)
    private String flowNodeText;

    /**
     * 上传人
     */
    @TableField(exist = false)
    private String uploadUserText;

    /**
     * 上传人id
     */
    @TableField(exist = false)
    private String uploadUser;

    /**
     *是否对外披露
     */
    private boolean isPublic;

    /**
     * 文件标题
     */
    private String fileTitle;
}