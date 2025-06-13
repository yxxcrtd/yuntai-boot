package com.joyintech.yuntai.module.system.dal.dataobject.dictnew;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 字典主表 DO
 *
 * @author 兆尹云台
 */
@TableName("sys_dict")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictDO extends BaseDO {

    /**
     * 
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 
     */
    private Integer systemType;
    /**
     * 
     */
    private String dictGroup;
    /**
     * 
     */
    private String dictName;
    /**
     * 
     */
    private String dictCode;
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
    private Integer type;
    /**
     * 
     */
    private String bankType;
    /**
     * 
     */
    private Integer sortIndex;
    /**
     * 
     */
    private Integer editState;
    /**
     * 
     */
    private String description;
    /**
     * 
     */
    private Integer delFlag;
    /**
     * 
     */
    private String dataType;
    /**
     * 
     */
    private String dictClassify;
    /**
     * 
     */
    private Integer dictType;

}