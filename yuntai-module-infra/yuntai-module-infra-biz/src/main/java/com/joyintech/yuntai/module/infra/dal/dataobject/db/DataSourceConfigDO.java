package com.joyintech.yuntai.module.infra.dal.dataobject.db;

import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import com.joyintech.yuntai.framework.mybatis.core.type.EncryptTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 数据源配置
 *
 * @author 兆尹云台
 */
@TableName(value = "infra_data_source_config", autoResultMap = true)
@KeySequence("infra_data_source_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
public class DataSourceConfigDO extends BaseDO {

    /**
     * 主键编号 - Master 数据源
     */
    public static final Long ID_MASTER = 0L;

    /**
     * 主键编号
     */
    private Long id;
    /**
     * 连接名
     */
    private String name;

    /**
     * 数据源连接
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码  加密方法暂时注释掉，加密规则没有确认。
     */
//    @TableField(typeHandler = EncryptTypeHandler.class)
    private String password;

    /**
     * 数据源类型
     */
    private String typeSource;

    /**
     * 数据源类型名称
     */
    private String typeName;

    /**
     * 动态数据源 0->是；1->否；
     */
    private Integer source;

    /**
     * 数据源编码
     */
    private String code;

    /**
     * ip
     */
    private String ip;

    /**
     * 备注
     */
    private String remark;

}
