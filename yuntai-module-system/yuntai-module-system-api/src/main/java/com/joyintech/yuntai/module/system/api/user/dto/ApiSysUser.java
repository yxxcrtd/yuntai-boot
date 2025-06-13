package com.joyintech.yuntai.module.system.api.user.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class ApiSysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    /** id **/
    private Long id;
    /** 登录账号 **/
    private String username;
    /** 真实姓名 **/
    private String realname;
    /** 密码 **/
    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /** 密码摘要加密方式 **/
    private String digestType;
    /** md5密码盐 **/

    private String salt;
    /** 头像 **/
    private String avatar;
    /** 生日 **/

    private Date birthday;
    /** 性别（1：男 2：女） **/
    private Integer sex;
    /** 电子邮件 **/
    private String email;
    /** 电话 **/
    private String phone;
    /** 用户所属部门id */
    private String departId;
    /**部门名称*/
    private transient String orgCodeTxt;
    /** 状态(1：正常  2：冻结 ） **/
    private Integer status;
    /** 删除状态（0，正常，1已删除） **/
    private Integer delFlag;
    /** 工号，唯一键 **/
    private String workNo;
    /** 职务，关联职务表 **/

    private String post;
    /** 座机号 **/
    private String telephone;
    /** 创建人 **/
    private String createBy;
    /** 创建时间 **/
    private Date createTime;
    /** 更新人 **/
    private String updateBy;
    /** 更新时间 **/
    private Date updateTime;
    /** 同步工作流引擎1同步0不同步 **/
    private Integer activitiSync;
    /** 身份（0 普通成员 1 上级） **/
    private Integer userIdentity;
    /** 多租户id配置，编辑用户的时候设置 **/
    private String relTenantIds;
    /**设备id uniapp推送用*/
    private String clientId;
    /**租户编号*/
    private String tenantCode;
    /**默认子系统*/
    private String defaultSystemType;
    /**用户类别*/
    private String userClass;
    /**用户所属组*/
    private String userGroup;

    /**
     * 陕国投新增 dengyufeng 20230919
     */
    /**用户所属部门名称*/
    private String departmentName;
    /**微信二维码*/
    private String weChatQrCode;
    /**微信*/
    private String weChat;
    /**在编性质*/
    private String positionName;
    /**人员级别*/
    private String userLevel;
    /**证件类别*/
    private String credentialType;
    /**证件编号*/
    private String credentialNo;
    /**qq*/
    private String qq;
    /**联系地址*/
    private String address;
    /**家庭地址*/
    private String familyAddress;
    /**毕业院校*/
    private String graduateUnity;
    /**学历*/
    private String education;
    /**学位*/
    private String degree;
    /**特长*/
    private String speciality;
    /**民族*/
    private String nation;
    /**个人简介*/
    private String personProfile;
}
