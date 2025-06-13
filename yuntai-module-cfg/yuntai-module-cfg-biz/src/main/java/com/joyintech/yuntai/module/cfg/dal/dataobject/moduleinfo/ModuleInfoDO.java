package com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 模型信息 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_module_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 映射模型id
     */
    private Long mappingModuleId;
    /**
     * 模型名称
     */
    private String moduleName;
    /**
     * 模型编码
     */
    private String moduleCode;
    /**
     * 模型类型
     */
    private String moduleType;
    /**
     * 模型sql
     */
    private String moduleSql;
    /**
     * 模型JavaBean
     */
    private String moduleBean;
    /**
     * 模型方法
     */
    private String moduleMethod;
    /**
     * 备注
     */
    private String remark;
    /**
     * 菜单Id
     */
    private Long menuId;
    /**
     * 主表id
     */
    private Long mainTableId;
    /**
     * 是否只读
     */
    private Boolean readonly;
    /**
     * 接口地址
     */
    private String apiUrl;
    /**
     * 接口类型：get，post
     */
    private String apiType;

    /**
     * 复制数据的id
     */
    private Long oldId;

    /**
     * 是否回推模型数据
     */
    private Boolean isPushBackModelData;

    /**
     * 指定回推地址
     */
    private String pushBackUrl;

    /**
     * 流程记录回推参数
     */
    private String flowLogParams;
}
