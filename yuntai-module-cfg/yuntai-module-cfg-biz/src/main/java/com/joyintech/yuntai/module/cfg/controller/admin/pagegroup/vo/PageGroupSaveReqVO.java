package com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面分组新增/修改 Request VO")
@Data
public class PageGroupSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "830")
    private Long id;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14381")
    @NotNull(message = "列表页ID不能为空")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "分组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "分组名称不能为空")
    private String groupName;

    @Schema(description = "分组编码")
    private String groupCode;

    @Schema(description = "分组排序")
    private String groupSort;

    @Schema(description = "插槽")
    private String groupSlot;

    @Schema(description = "是否隐藏")
    private Boolean isHidden;

    @Schema(description = "分组字段列表")
    private List<PageListConfigSaveReqVO> pageListConfigs;

    @Schema(description = "分组字段列表")
    private List<ConditionalTableSaveReqVO> showConfig;

    @Schema(description = "父分组ID")
    private String parentId;

    @Schema(description = "分组头部提示语")
    private String groupHeadTips;

    @Schema(description = "分组尾部提示语")
    private String groupFootTips;

    @Schema(description = "分组展示方式")
    private String groupDisplay;

    @Schema(description = "标题")
    private String groupTitle;

    @Schema(description = "提示文本")
    private String groupTips;

    @Schema(description = "链接地址")
    private String groupLinkAddress;

    @Schema(description = "链接名称")
    private String groupLinkName;

    @Schema(description = "链接类型")
    private String groupLinkType;

    @Schema(description = "尾部插槽")
    private String groupFailSlot;

}
