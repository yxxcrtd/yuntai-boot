package com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo;

import java.util.List;
import javax.validation.constraints.NotNull;

import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.PageLinkageRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 表单页配置-附件管理-指定上传文件新增/修改 Request VO")
@Data
public class PageAttachmentUploadfileSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7681")
    private Long id;

    @Schema(description = "附件管理ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16753")
    @NotNull(message = "附件管理ID不能为空")
    private Long attachmentId;

    @Schema(description = "文件名称", example = "李四")
    private String fileTypeId;

    @Schema(description = "文件名称", example = "李四")
    private String fileTypeText;

    @Schema(description = "是否必传")
    private Integer isRequire;

    @Schema(description = "是否校验文件名", example = "云台")
    private Integer isValidFileName;

    @Schema(description = "流程节点Id")
    private String flowNodeId;

    @Schema(description = "文件名校验规则（正则）")
    private String validRule;

    @Schema(description = "联动配置")
    private List<PageLinkageRespVO> pageLinkageRespVOS;

    @Schema(description = "上传模版文件id")
    private String uploadTemplateFileId;

    @Schema(description = "上传模版文件名称")
    private String uploadTemplateFileName;

    @Schema(description = "文件类型")
    private String fileAllowSuffix;
}