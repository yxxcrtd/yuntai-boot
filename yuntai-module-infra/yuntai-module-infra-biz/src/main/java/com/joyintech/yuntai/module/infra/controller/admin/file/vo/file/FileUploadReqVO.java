package com.joyintech.yuntai.module.infra.controller.admin.file.vo.file;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 上传文件 Request VO")
@Data
public class FileUploadReqVO {

    @Schema(description = "文件附件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "文件附件不能为空")
    private MultipartFile file;

    @Schema(description = "文件附件", example = "yuntaiyuanma.png")
    private String path;

    @Schema(description = "附件类型")
    private String attachmentType;

    @Schema(description = "上传时间")
    private LocalDateTime uploadTime;

    @Schema(description = "上传人")
    private String uploadUser;

    @Schema(description = "备注")
    private String remark;
}
