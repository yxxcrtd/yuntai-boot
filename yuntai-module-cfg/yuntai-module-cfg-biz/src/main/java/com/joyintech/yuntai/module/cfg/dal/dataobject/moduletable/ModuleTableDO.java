package com.joyintech.yuntai.module.cfg.dal.dataobject.moduletable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模型关联 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_module_table")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleTableDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 模型id
     */
    private Long moduleId;
    /**
     * 关联Id
     */
    private Long tableId;
    /**
     * 是否主表
     */
    private Boolean isMain;
    /**
     * 是否只读
     */
    private Boolean readonly;
    /**
     * 是否子表
     */
    private Boolean isChild;
    /**
     * 主表id
     */
    private Long mainTableId;
    /**
     * 关联类型 left /right join
     */
    private String relationType;
    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * 参数类型
     */
    private String parameterType;
    /**
     * 其他查询条件
     */
    private String searchSql;
    /**
     * 表别名
     */
    private String tableAlias;

    private String tableKey;

    private String mainTableKey;

    /**
     * 复制数据的id
     */
    private Long oldId;
}
