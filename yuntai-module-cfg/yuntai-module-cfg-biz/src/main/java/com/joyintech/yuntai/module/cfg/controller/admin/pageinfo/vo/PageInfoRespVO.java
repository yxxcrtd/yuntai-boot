package com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo.PageApiSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.PageAttachmentInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo.PageButtonSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo.PageExtendEventRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo.PageParameterSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo.ParamterListSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo.TemplateInfoRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 页面基本信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageInfoRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13835")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "菜单ID")
    private Long menuId;

    @Schema(description = "页面名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("页面名称")
    private String pageName;

    @Schema(description = "页面编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("页面编码")
    private String pageCode;

    @Schema(description = "页面类型;1:列表 2-表单", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("页面类型;1:列表 2-表单")
    private String pageType;

    @Schema(description = "数据模型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1799")
    @ExcelProperty("数据模型")
    private Long moduleId;

    @Schema(description = "服务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19352")
    @ExcelProperty("服务ID")
    private Long serverId;

    @Schema(description = "页面风格")
    @ExcelProperty("页面风格")
    private String pageStyle;

    @Schema(description = "页面状态")
    private String pageState;

    @Schema(description = "默认查询")
    @ExcelProperty("默认查询")
    private String defaultQuery;

    @Schema(description = "页面模板")
    @ExcelProperty("页面模板")
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
    @ExcelProperty("父页面套壳")
    private String parentPage;

    @Schema(description = "内部插槽")
    @ExcelProperty("内部插槽")
    private String innerSlot;

    @Schema(description = "页头插槽")
    @ExcelProperty("页头插槽")
    private String headerSlot;

    @Schema(description = "中间插槽")
    @ExcelProperty("中间插槽")
    private String middleSlot;

    @Schema(description = "尾部插槽")
    @ExcelProperty("尾部插槽")
    private String tailSlot;

    @Schema(description = "外部JS")
    private String externalJsFile;

    @Schema(description = "支持附件")
    private String isSupportAttachment;

    @Schema(description = "表单排版")
    private String columnSpan;

    @Schema(description = "标签长度")
    private Long labelWidth;

    @Schema(description = "标签位置")
    private String labelPosition;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "上次修改人")
    private String updater;

    @Schema(description = "操作按钮")
    private List<PageButtonSaveReqVO> pageButtons;

    @Schema(description = "页面扩展事件")
    private List<PageExtendEventRespVO> eventList;

    @Schema(description = "全部字段列表")
    private List<PageListConfigSaveReqVO> pageListConfigs;

    @Schema(description = "页面API表")
    private List<PageApiSaveReqVO> pageApiRespVOS;

    @Schema(description = "页面参数表")
    private List<PageParameterSaveReqVO> templateParamList;

    @Schema(description = "页面路由参数")
    private List<ParamterListSaveReqVO> paramterList;

    @Schema(description = "页面模板信息")
    private TemplateInfoRespVO templateInfo;

    @Schema(description = "表单页配置-附件管理")
    private PageAttachmentInfoSaveReqVO AttachmentInfo;

    @Schema(description = "页面版本")
    private String pageVersion;

}
