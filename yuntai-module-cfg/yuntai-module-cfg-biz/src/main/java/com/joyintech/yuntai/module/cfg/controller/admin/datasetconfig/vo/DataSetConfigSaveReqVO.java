package com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo;

import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpSaveReqVO;

@Schema(description = "管理后台 - 数据集管理新增/修改 Request VO")
@Data
public class DataSetConfigSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5051")
    private Long id;

    @Schema(description = "数据集类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "数据集类型不能为空")
    private String type;

    @Schema(description = "数据集名称", example = "张三")
    @NotEmpty(message = "数据集名称不能为空")
    private String name;

    @Schema(description = "数据集编码")
    @NotEmpty(message = "数据集编码不能为空")
    private String code;

    @Schema(description = "数据源")
    private Long sourceCode;

    @Schema(description = "描述", example = "你猜")
    private String remark;

    @Schema(description = "JSON数据")
    private String jsonData;

    @Schema(description = "SQL语句")
    private String sqlData;

    @Schema(description = "请求头部")
    private List<DataSetConfigHttpSaveReqVO> headerList;

    @Schema(description = "请求参数")
    private List<DataSetConfigHttpSaveReqVO> configList;

    @Schema(description = "调用方式")
    private String callMethod;

    @Schema(description = "请求地址")
    private String url;

    @Schema(description = "请求地址")
    private String requestMethods;

    @Schema(description = "JSON数据、SQL语句")
    private List<Map> data;
}