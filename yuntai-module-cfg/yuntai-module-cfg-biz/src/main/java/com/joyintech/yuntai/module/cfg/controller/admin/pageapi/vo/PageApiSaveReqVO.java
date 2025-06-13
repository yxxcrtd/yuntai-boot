package com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo.SubTableSettingSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo.PageGroupSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition.vo.PageListConditionSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo.PageParameterSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.SubTableSetting.SubTableSettingDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 页面api新增/修改 Request VO")
@Data
public class PageApiSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10363")
    private Long id;

    @Schema(description = "页面ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9079")
    @NotNull(message = "页面ID不能为空")
    private Long pageId;

    @Schema(description = "apiID")
    private Long apiId;

    @Schema(description = "数据模型", requiredMode = Schema.RequiredMode.REQUIRED, example = "11753")
    @NotNull(message = "数据模型不能为空")
    private Long moduleId;

    @Schema(description = "服务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1318")
    @NotNull(message = "服务ID不能为空")
    private Long serverId;

    @Schema(description = "api编码")
    private String apiCode;

    @Schema(description = "api名称", example = "李四")
    private String apiName;

    @Schema(description = "查询条件")
    private List<PageListConditionSaveReqVO> pageListConditions;

    @Schema(description = "分组字段列表")
    private List<PageListConfigSaveReqVO> pageListConfigs;

    @Schema(description = "分组视图")
    private List<PageGroupSaveReqVO> pageGroups;

    @Schema(description = "模型使用到的表")
    private List<ModuleTableSaveReqVO> moduleTables;

    @Schema(description = "模型数据主表ID")
    private Long mainTableId;

    @Schema(description = "模型数据子表")
    private List<SubTableSettingSaveReqVO> tableLayoutConfig;
}
