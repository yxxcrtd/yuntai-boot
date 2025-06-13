package com.joyintech.yuntai.module.cfg.dal.dataobject.validaterules;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页面校验规则 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_validate_rules")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateRulesDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 页面ID
     */
    private Long pageConfigId;
    /**
     * 校验名称
     */
    private String checkName;
    /**
     * 是否必填
     */
    private String isSelect;
    /**
     * 校验类型
     */
    private String validateType;
    /**
     * 提示信息
     */
    private String toolTips;
    /**
     * 规则定义
     */
    private String rulesName;

    /**
     * 区间配置类型
     */
    private String rangeType;

    /**
     * 复制数据的id
     */
    private Long oldId;
}