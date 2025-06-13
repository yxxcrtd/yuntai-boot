package com.joyintech.yuntai.module.cfg.dal.dataobject.indexdefinition;

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
 * 索引定义 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_index_definition")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexDefinitionDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 定义表主键
     */
    private Long tableId;
    /**
     * 索引名称
     */
    private String indexName;
    /**
     * 索引列
     */
    private String indexColumns;
    /**
     * 是否唯一索引
     */
    private Boolean isUniqueKey;
    /**
     * 数据源id
     */
    private Long datasourceId;

}