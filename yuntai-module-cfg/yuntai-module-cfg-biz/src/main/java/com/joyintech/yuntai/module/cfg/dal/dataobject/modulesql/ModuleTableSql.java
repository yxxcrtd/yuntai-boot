package com.joyintech.yuntai.module.cfg.dal.dataobject.modulesql;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 模型新建后生成的SQL DO
 *
 * @author 兆尹云台
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ModuleTableSql {

    /**
     * 模型编码
     */
    private Long moduleId;

    /**
     * 模型表Id
     */
    private Long moduleTableId;

    /**
     * 数据源id
     */
    private Long dataSourceId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * sql内容
     */
    private String actionSql;

    /**
     * sql类型
     */
    private String actionType;

    /**
     * 模型类型
     */
    private String moduleType;

    /**
     * 模型JavaBean
     */
    private String moduleBean;

    /**
     * 模型方法
     */
    private String moduleMethod;

    /**
     * 接口地址
     */
    private String apiUrl;

    /**
     * 接口类型：get，post
     */
    private String apiType;

    /**
     * 是否主表
     */
    private Boolean isMain;

    /**
     * 是否子表
     */
    private Boolean isChild;

}
