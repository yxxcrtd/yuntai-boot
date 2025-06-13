package com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentuploadfile;

import java.util.List;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.PageLinkageRespVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 表单页配置-附件管理-指定上传文件 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_page_attachment_uploadfile")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageAttachmentUploadfileDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 附件管理ID
     */
    private Long attachmentId;
    /**
     * 文件名称
     */
    private String fileTypeId;
    /**
     * 文件名称
     */
    private String fileTypeText;
    /**
     * 是否必传
     */
    private Integer isRequire;
    /**
     * 是否校验文件名
     */
    private Integer isValidFileName;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 流程节点Id
     */
    private String flowNodeId;

    /**
     * 文件名校验规则（正则）
     */
    private String validRule;

    /**
     * 联动配置
     */
    @TableField(exist = false)
    private List<PageLinkageRespVO> pageLinkageRespVOS;

    /**
     * 上传模版文件id
     */
    private String uploadTemplateFileId;

    /**
     * 上传模版文件名称
     */
    private String uploadTemplateFileName;

    /**
     *文件类型
     */
    private String fileAllowSuffix;

}