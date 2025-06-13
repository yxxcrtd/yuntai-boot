package com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo.TemplateContentSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 模版 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TemplateInfoRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26312")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "模版分组ID", example = "8214")
    @ExcelProperty("模版分组ID")
    private Long groupId;

    @Schema(description = "模版code")
    @ExcelProperty("模版code")
    private String templateCode;

    @Schema(description = "模版名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("模版名称")
    private String templateName;

    @Schema(description = "模版地址")
    @ExcelProperty("模版地址")
    private String templateAddress;

    @Schema(description = "模版图片")
    @ExcelProperty("模版图片")
    private String templateImage;

    @Schema(description = "模版类型")
    private String templateType;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "api列表")
    private List<TemplateContentSaveReqVO> apiList;

    @Schema(description = "参数列表")
    private List<TemplateContentSaveReqVO> paramList;

}
