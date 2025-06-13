package com.joyintech.yuntai.module.system.dal.dataobject.dictitem;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 字典子表 DO
 *
 * @author 兆尹云台
 */
@TableName("sys_dict_item")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictItemDO extends BaseDO {

    /**
     * 
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 
     */
    private String dictId;
    /**
     * 
     */
    private String dictCode;
    /**
     * 
     */
    private String itemText;
    /**
     * 
     */
    private String itemValue;
    /**
     * 
     */
    private String filterType;
    /**
     * 
     */
    private String description;
    /**
     * 
     */
    private Integer sortOrder;
    /**
     * 
     */
    private Integer status;
    /**
     * 
     */
    private String createBy;
    /**
     * 
     */
    private String updateBy;
    /**
     * 
     */
    private Integer delFlag;
    /**
     * 
     */
    private Integer tenantCode;
    /**
     * 
     */
    private String extText1;

}