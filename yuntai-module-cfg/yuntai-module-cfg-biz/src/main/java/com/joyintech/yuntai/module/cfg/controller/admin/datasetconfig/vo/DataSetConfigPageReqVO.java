package com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpSaveReqVO;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 数据集管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataSetConfigPageReqVO extends PageParam {

    @Schema(description = "数据集类型", example = "1")
    private String type;

    @Schema(description = "数据集名称", example = "张三")
    private String name;

    @Schema(description = "数据集编码")
    private String code;

    @Schema(description = "数据源")
    private Long sourceCode;

    @Schema(description = "描述", example = "你猜")
    private String remark;

    @Schema(description = "JSON数据")
    private String jsonData;

    @Schema(description = "SQL语句")
    private String sqlData;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "请求头部")
    private List<DataSetConfigHttpPageReqVO> headerList;

    @Schema(description = "请求参数")
    private List<DataSetConfigHttpPageReqVO> configList;

    @Schema(description = "调用方式")
    private String callMethod;

    @Schema(description = "请求地址")
    private String url;

    @Schema(description = "请求地址")
    private String requestMethods;

    @Schema(description = "JSON数据、SQL语句")
    private List<Map> data;
}