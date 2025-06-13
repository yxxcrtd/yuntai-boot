package com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.alibaba.excel.annotation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpRespVO;

@Schema(description = "管理后台 - 数据集管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DataSetConfigRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5051")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "数据集类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("数据集类型")
    private String type;

    @Schema(description = "数据集名称", example = "张三")
    @ExcelProperty("数据集名称")
    private String name;

    @Schema(description = "数据集编码")
    @ExcelProperty("数据集编码")
    private String code;

    @Schema(description = "数据源")
    @ExcelProperty("数据源")
    private Long sourceCode;

    @Schema(description = "描述", example = "你猜")
    @ExcelProperty("描述")
    private String remark;

    @Schema(description = "JSON数据")
    @ExcelProperty("JSON数据")
    private String jsonData;

    @Schema(description = "SQL语句")
    @ExcelProperty("SQL语句")
    private String sqlData;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "请求头部")
    private List<DataSetConfigHttpRespVO> headerList;

    @Schema(description = "请求参数")
    private List<DataSetConfigHttpRespVO> configList;

    @Schema(description = "调用方式")
    private String callMethod;

    @Schema(description = "请求地址")
    private String url;

    @Schema(description = "请求地址")
    private String requestMethods;

    @Schema(description = "JSON数据、SQL语句")
    private List<Map> data;
}