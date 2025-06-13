package com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo.TemplateContentSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 模版新增/修改 Request VO")
@Data
public class TemplateInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26312")
    private Long id;

    @Schema(description = "模版分组ID", example = "8214")
    private Long groupId;

    @Schema(description = "模版code")
    private String templateCode;

    @Schema(description = "模版名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "模版名称不能为空")
    private String templateName;

    @Schema(description = "模版地址")
    private String templateAddress;

    @Schema(description = "模版类型")
    private String templateType;

    @Schema(description = "模版图片")
    private String templateImage;

    @Schema(description = "api列表")
    private List<TemplateContentSaveReqVO> apiList;

    @Schema(description = "参数列表")
    private List<TemplateContentSaveReqVO> paramList;


}
