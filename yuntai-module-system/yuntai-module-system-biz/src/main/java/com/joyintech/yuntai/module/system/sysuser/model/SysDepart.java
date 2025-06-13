package com.joyintech.yuntai.module.system.sysuser.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 部门表
 */
@Data
@TableName(value = "SYS_DEPART")
public class SysDepart implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    /**父机构ID*/
    private String parentId;

    /**机构/部门名称*/
    private String departName;

    /**英文名*/
    private String departNameEn;

    /**缩写*/
    private String departNameAbbr;

    /**排序*/
    private Integer departOrder;

    /**描述*/
    private String description;

    /**机构性质 1机构 2部门*/
    private String orgType;

    /**机构编码*/
    private String orgCode;

    /**手机号*/
    private String mobile;

    /**传真*/
    private String fax;

    /**地址*/
    private String address;

    /**备注*/
    private String memo;

    /**状态（1启用，0不启用）*/
    private String status;

    /**状态（1启用，0不启用）*/
    private String delFlag;
    /**机构层级 1总行 2分行 3支行*/
    private Integer branchType;

    /**租户编号*/
    private String tenantCode;

    @TableField(exist = false)
    private Boolean isLeaf;

    /**
     * 陕国投增加字段 dengyufeng 20230919
     */
    /**部门标签*/
    private String departLabel;

    private String taOrgCode;

}
