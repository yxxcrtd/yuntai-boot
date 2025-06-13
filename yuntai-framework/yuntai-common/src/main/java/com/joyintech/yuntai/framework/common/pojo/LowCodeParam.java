package com.joyintech.yuntai.framework.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Schema(description="通用参数,支持分页")
public class LowCodeParam extends PageParam {
    @Schema(description = "服务编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "10572")
    private Long serviceId;

    @Schema(description = "模型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10572")
    private Long pageId;

    @Schema(description = "页面ApiCode", requiredMode = Schema.RequiredMode.REQUIRED, example = "10572")
    private String pageApiCode;

    @Schema(description = "是否启用分页,true-启用,false不启用,默认启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "10572")
    private Boolean enablePage;

    @Schema(description = "增删查改-通用参数")
    private Map<String, Object> params;

    @Schema(description = "列表分页查询-排序字段")
    private List<SortingField> sortingFields;

    @Schema(description = "列表分页查询-搜索字段")
    private List<QueryField> queryFields;

    @Schema(description = "是否映射为业务数据")
    private Boolean mapped;

    @Schema(description = "流程节点")
    private Map<String, Object> flowInfo;

    @Schema(description = "子表id")
    private List<String> childIdList;

    @Schema(description = "接口版本")
    private String pageVersion;

}
