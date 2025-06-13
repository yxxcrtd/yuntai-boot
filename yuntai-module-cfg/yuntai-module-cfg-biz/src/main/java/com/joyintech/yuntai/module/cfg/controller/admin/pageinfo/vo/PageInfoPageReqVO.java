package com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo;

import static com.joyintech.yuntai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.joyintech.yuntai.framework.common.pojo.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 页面基本信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageInfoPageReqVO extends PageParam {

    @Schema(description = "菜单ID")
    private Long menuId;

    @Schema(description = "页面名称", example = "李四")
    private String pageName;

    @Schema(description = "页面编码")
    private String pageCode;

    @Schema(description = "页面类型;1:列表 2-表单", example = "1")
    private String pageType;

    @Schema(description = "数据模型", example = "1799")
    private Long moduleId;

    @Schema(description = "服务ID", example = "19352")
    private Long serverId;

    @Schema(description = "页面风格")
    private String pageStyle;

    @Schema(description = "页面状态")
    private String pageState;

    @Schema(description = "默认查询")
    private String defaultQuery;

    @Schema(description = "页面模板")
    private String pageTemplate;

    @Schema(description = "页面模版下服务ID")
    private String treeServerId;

    @Schema(description = "页面模版下数据模型")
    private String treeModuleId;

    @Schema(description = "子表格")
    private String subTable;

    @Schema(description = "子表格下数据模型")
    private String tableModuleId;

    @Schema(description = "子表格下服务ID")
    private String tableServerId;

    @Schema(description = "父页面套壳")
    private String parentPage;

    @Schema(description = "内部插槽")
    private String innerSlot;

    @Schema(description = "页头插槽")
    private String headerSlot;

    @Schema(description = "中间插槽")
    private String middleSlot;

    @Schema(description = "尾部插槽")
    private String tailSlot;

    @Schema(description = "外部JS")
    private String externalJsFile;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "支持附件")
    private String isSupportAttachment;

    @Schema(description = "表单排版")
    private String columnSpan;

    @Schema(description = "标签长度")
    private Long labelWidth;

    @Schema(description = "标签位置")
    private String labelPosition;

    @Schema(description = "页面版本")
    private String pageVersion;
}