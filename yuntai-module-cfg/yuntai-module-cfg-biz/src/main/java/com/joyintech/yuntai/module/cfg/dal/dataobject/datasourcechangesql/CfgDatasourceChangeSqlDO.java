package com.joyintech.yuntai.module.cfg.dal.dataobject.datasourcechangesql;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("CFG_DATASOURCE_CHANGE_SQL")
public class CfgDatasourceChangeSqlDO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * SQL执行的时间戳
     */
    private Long orderNo;

    /**
     * 待执行SQL的类型 (I: insert, U: update, D: delete)
     */
    private String sqlType;

    /**
     * 表名
     */
    private String tableName;

    /**
     * SQL内容
     */
    private String sqlContent;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}