package com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo;

import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 模型涉及字段新增/修改 Request VO")
@Data
public class ModuleFieldSaveReqVO extends PageListConfigSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10873")
    private Long id;

    @Schema(description = "模型id", example = "26488")
    private Long moduleId;

    @Schema(description = "表id", example = "27854")
    private Long moduleTableId;

    @Schema(description = "字段id", example = "赵六")
    private Long columnId;

    @Schema(description = "模型映射字段id,对应这个表里的id", example = "赵六")
    private Long mappingFieldId;

    @Schema(description = "字段名", example = "张三")
    private String columnName;

    @Schema(description = "字段备注", example = "张三")
    private String columnComment;

    @Schema(description = "字段别名", example = "张三")
    private String columnAliasName;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "是否只读字段,默认否")
    private Boolean readonly;

    @Schema(description = "是否只读字段,默认否")
    private Boolean isSys;

    @Schema(description = "字段类型")
    private String columnType;

    @Schema(description = "字段长度")
    private Integer columnLength;

    @Schema(description = "默认值")
    private String defaultValue;

    @Schema(description = "是否必填")
    private Boolean required;

    @Schema(description = "是否主键")
    private Boolean isPrimaryKey;

    @Schema(description = "数据库字段映射成的java类型")
    private String javaType;

    @Schema(description = "数据库字段映射成的jdbcType类型")
    private String jdbcType;

    @Schema(description = "计算字段sql")
    private String computeSql;

    @Schema(description = "回显表")
    private String textTableId;

    @Schema(description = "回显字段")
    private String textColumnId;

    @Schema(description = "回显表外键")
    private String textTableKey;

    @Schema(description = "单选0 多选 1")
    private Integer valueType;

    @Schema(description = "回显sql语句")
    private String textSql;

    @Schema(description = "字段排序")
    private Integer sort;

    @Schema(description = "是否回推")
    private Integer isBackData;
}
