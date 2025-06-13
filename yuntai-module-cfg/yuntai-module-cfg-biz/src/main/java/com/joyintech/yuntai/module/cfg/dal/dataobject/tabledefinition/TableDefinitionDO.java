package com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 表定义 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_table_definition")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDefinitionDO extends BaseDO {
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 数据源id
     */
    private Long datasourceId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 描述
     */
    private String remark;

    /**
     * 是否生效,true:生效,false:不生效
     */
    private Boolean status;

    /**
     * 是否系统表,true:是,false:不是
     */
    private Boolean isSys;

    /**
     * 表类型
     */
    private String tableType;

    /**
     * 表sql
     */
    private String tableSql;
}
