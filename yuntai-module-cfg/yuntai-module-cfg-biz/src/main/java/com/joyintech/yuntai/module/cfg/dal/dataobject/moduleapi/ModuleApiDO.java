package com.joyintech.yuntai.module.cfg.dal.dataobject.moduleapi;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 模型API DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_module_api")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleApiDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务编码
     */
    private String serviceCode;
    /**
     * 服务方式
     */
    private String serviceType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 模型id
     */
    private Long moduleId;

    /**
     * 复制数据的id
     */
    private Long oldId;
}
