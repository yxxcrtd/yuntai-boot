package com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfighttp;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 数据集-http请求内容 DO
 *
 * @author 兆尹云台
 */
@TableName("infra_data_set_config_http")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSetConfigHttpDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 列表页ID
     */
    private Long dataId;
    /**
     * 类型
     */
    private String type;
    /**
     * 请求头/参数名称
     */
    private String keyCode;

    /**
     * 内容/参数值
     */
    private String value;

}