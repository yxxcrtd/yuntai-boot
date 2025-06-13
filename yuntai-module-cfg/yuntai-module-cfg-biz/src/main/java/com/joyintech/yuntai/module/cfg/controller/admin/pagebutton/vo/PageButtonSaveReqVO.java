package com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo.ButtonActionSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableRespVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.buttonaction.ButtonActionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面操作按钮新增/修改 Request VO")
@Data
public class PageButtonSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28049")
    private Long id;

    @Schema(description = "按钮类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "按钮类型不能为空")
    private String buttonType;

    @Schema(description = "操作类型", example = "1")
    private String operationType;

    @Schema(description = "按钮名称", example = "张三")
    private String buttonName;

    @Schema(description = "列表页ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23130")
    @NotNull(message = "列表页ID不能为空")
    private Long pageId;

    @Schema(description = "apiID")
    private Long pageApiId;

    @Schema(description = "关联服务ID")
    private Long modelServerId;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "服务参数")
    private List<String> serverParams;

    @Schema(description = "服务参数编码")
    private String serverParamsCode;

    @Schema(description = "按钮样式")
    private String buttonStyle;

    @Schema(description = "按钮图标")
    private String buttonIcon;

    @Schema(description = "按钮类型")
    private String buttonShape;

    @Schema(description = "按钮权限标识")
    private String permissionSign;

    @Schema(description = "打开方式")
    private String openWay;

    @Schema(description = "关联页面")
    private String relevancePage;

    @Schema(description = "自定义方法")
    private String customMethod;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "条件配置")
    private ConditionalTableRespVO conditionalTableRespVO;

    @Schema(description = "按钮动作")
    private ButtonActionSaveReqVO showButtonActionCfg;

    @Schema(description = "按钮默认参数")
    private String actionDefaultParams;

    @Schema(description = "子表id")
    private Long moduleTableId;

    @Schema(description = "显示组件")
    private String columnDisplayComponent;

    @Schema(description = "显示组件名称")
    private String ColumnDisplayComponentName;

    @Schema(description = "组件属性")
    private List<ColumnComponentAttributeDO> columnComponentAttributeDO;

    @Schema(description = "按钮位置")
    private String btnPosition;
}
