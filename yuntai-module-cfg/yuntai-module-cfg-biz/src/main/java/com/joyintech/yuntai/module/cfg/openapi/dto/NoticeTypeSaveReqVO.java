package com.joyintech.yuntai.module.cfg.openapi.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import com.joyintech.yuntai.module.cfg.dal.dataobject.fileinfo.FileInfoDO;

/**
 * 消息通知入参
 *
 * @author hzz
 * @since 2024-12-17
 */
@Schema(description = "消息通知入参")
@Data
public class NoticeTypeSaveReqVO {

    @Schema(description = "唯一标识")
    @NotEmpty(message = "标识不能为空")
    private String serialNum;

    @Schema(description = "部门ID")
    private String deptId;

    @Schema(description = "节点ID")
    @NotEmpty(message = "节点ID不能为空")
    private String nodeId;

    @Schema(description = "流程ID")
    @NotEmpty(message = "流程不能为空")
    private String workFlowId;

    @Schema(description = "实例ID")
    @NotEmpty(message = "实例ID不能为空")
    private String requestid;

    @Schema(description = "通知类型")
    @NotEmpty(message = "通知类型不能为空")
    private String noticeType;

    @Schema(description = "增量字段")
    private String otherParameter;

    @Schema(description = "流程状态")
    @NotEmpty(message = "流程状态不能为空")
    private String flowStatus;

    @Schema(description = "表单ID")
    private Long pageInfoId;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "备用字段1")
    private String backupField1;

    @Schema(description = "备用字段2")
    private String backupField2;

    @Schema(description = "备用字段3")
    private String backupField3;

    @Schema(description = "备用字段4")
    private String backupField4;

    @Schema(description = "备用字段5")
    private String backupField5;

    @Schema(description = "返回模型数据")
    private String data;

    @Schema(description = "返回映射数据")
    private String mappingData;

    @Schema(description = "是否本地调用")
    private Boolean isLocal;

    @Schema(description = "附件信息")
    private List<FileInfoDO> fileData;

    @Schema(description = "数据模型主表id")
    private String modId;

    @Schema(description = "流程是否结束")
    private String isFinal;
}
