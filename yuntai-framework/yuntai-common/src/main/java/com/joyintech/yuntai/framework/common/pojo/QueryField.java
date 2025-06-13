package com.joyintech.yuntai.framework.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/14
 */
@Data
@Schema(description="通用参数,查询字段")
public class QueryField {

    @Schema(description = "字段名")
    private String field;

    @Schema(description = "字段值,根据queryOperator的不同，value可能是对象、List")
    private Object value;

    @Schema(description = "查询操作符")
    private String queryOperator;

    @Schema(description = "数据类型 没有可为空")
    private String jdbcType;


}
