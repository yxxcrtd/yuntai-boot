package com.joyintech.yuntai.module.cfg.dal.dataobject.dbtypeconfig;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 数据库字段映射表,默认从mysql映射到其他数据库 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_db_type_config")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbTypeConfigDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 数据库类型,mysql
     */
    private String typeSource;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * 目标数据库,db2/dm/oracle
     */
    private String targetTypeSource;
    /**
     * 目标数据库列类型
     */
    private String targetColumnType;
    /**
     * 输入规则：0-任意输入,1-需输入长度,2-需输入精度,3-需输入长度和精度
     */
    private Integer inputRule;

}