package com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo.PageApiSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.PageAttachmentInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo.PageButtonSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo.PageExtendEventSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo.PageParameterSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo.ParamterListSaveReqVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 页面基本信息新增/修改 Request VO")
@Data
public class PageInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13835")
    private Long id;

    @Schema(description = "菜单ID")
    private Long menuId;

    @Schema(description = "页面名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "页面名称不能为空")
    private String pageName;

    @Schema(description = "页面编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "页面编码不能为空")
    private String pageCode;

    @Schema(description = "页面类型;1:列表 2-表单", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "页面类型;1:列表 2-表单不能为空")
    private String pageType;

    @Schema(description = "数据模型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1799")
//    @NotNull(message = "数据模型不能为空")
    private Long moduleId;

    @Schema(description = "服务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19352")
//    @NotNull(message = "服务ID不能为空")
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

    @Schema(description = "支持附件")
    private String isSupportAttachment;

    @Schema(description = "表单排版")
    private String columnSpan;

    @Schema(description = "标签长度")
    private Long labelWidth;

    @Schema(description = "标签位置")
    private String labelPosition;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "更新时的更新时间")
    private LocalDateTime updateTimeStamp;

    @Schema(description = "操作按钮")
    private List<PageButtonSaveReqVO> pageButtons;

    @Schema(description = "模型字段列表")
    private List<PageListConfigSaveReqVO> pageListConfigs;

    @Schema(description = "页面扩展事件表")
    private List<PageExtendEventSaveReqVO> eventList;

    @Schema(description = "页面API表")
    private List<PageApiSaveReqVO> pageApiRespVOS;

    @Schema(description = "页面参数表")
    private List<PageParameterSaveReqVO> templateParamList;

    @Schema(description = "页面路由参数")
    private List<ParamterListSaveReqVO> paramterList;

    @Schema(description = "表单页配置-附件管理")
    private PageAttachmentInfoSaveReqVO attachmentInfo;

    @Schema(description = "页面版本")
    private String pageVersion;


}
