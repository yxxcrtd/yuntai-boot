package com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;
import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/11
 */
@Data
public class ModuleCache {

    /**
     * 模型编码
     */
    private Long moduleId;

    /**
     * 服务id
     */
    private Long serviceId;

    /**
     * 服务编码
     */
    private String serviceCode;

    /**
     * 数据源id
     */
    private Long dataSourceId;

    /**
     * 数据源编码
     */
    private String dataSourceCode;

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
     * 主表的id
     */
    private Long moduleTableId;

    /**
     * sql列表
     */
    private List<ModuleSqlCache> sqlList;

    /**
     * 页面扩展事件列表
     */
    private List<PageExtendEventCache> extendEventList;

    /**
     * 绑定的关系列表
     */
    private List<ModuleRelationFieldCache> relationList;

    /**
     * 系统字段列表
     */
    @JsonIgnore
    private List<DbSystemColumnDO> systemFieldList;

    /**
     * 删除字段名
     */
    @JsonIgnore
    private String deleteField;

    /**
     * 删除字段值
     */
    @JsonIgnore
    private Object deleteValue;

    /**
     * 未删除字段值
     */
    @JsonIgnore
    private Object notDeleteVaule;

}
