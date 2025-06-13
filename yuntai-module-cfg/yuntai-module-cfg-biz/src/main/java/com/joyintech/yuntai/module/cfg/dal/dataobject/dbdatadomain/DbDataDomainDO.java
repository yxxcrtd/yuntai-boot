package com.joyintech.yuntai.module.cfg.dal.dataobject.dbdatadomain;

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
 * 数据库字段类型表(数据域) DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_db_data_domain")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbDataDomainDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 类型名
     */
    private String dataType;
    /**
     * 数据库类型（MySQL）;设计：其他数据库根据MySQL类型代码中做映射
     */
    private String dbType;
    /**
     * 长度
     */
    private Integer dataLength;
    /**
     * 小数位数
     */
    private Integer dataScale;
    /**
     * 备注
     */
    private String remark;

}