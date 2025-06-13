package com.joyintech.yuntai.module.cfg.dal.dataobject.modulesql;

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
 * 模型新建后生成的SQL DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_module_sql")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleSqlDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 模型Id
     */
    private Long moduleId;

    /**
     * 关联表id
     */
    private Long moduleTableId;
    /**
     * 表名称,新增/修改/删除时使用
     */
    private String tableName;
    /**
     * 操作类型,新增/修改/删除/查询
     */
    private String actionType;
    /**
     * sql内容
     */
    private String actionSql;

    /**
     * 复制数据的id
     */
    private Long oldId;
}
