package com.joyintech.yuntai.module.cfg.dal.dataobject.functioninfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 开发平台功能管理 DO
 *
 * @author 兆尹云台
 */
@TableName("cfg_function_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 功能菜单名称
     */
    private String functionName;
    /**
     * 功能菜单编码
     */
    private String functionCode;
    /**
     * 功能菜单图标
     */
    private String functionIcon;
    /**
     * 功能菜单类型
     */
    private String functionType;
    /**
     * 功能菜单排序
     */
    private String sort;
    /**
     * 功能菜单状态
     */
    private String status;
    /**
     * 功能菜单描述
     */
    private String remark;

}