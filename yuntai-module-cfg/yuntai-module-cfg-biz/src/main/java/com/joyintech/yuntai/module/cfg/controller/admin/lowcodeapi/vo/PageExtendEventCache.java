package com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo;

import lombok.*;


/**
 * 页面事件扩展配置 DO
 *
 * @author 兆尹云台
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageExtendEventCache {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 页面id
     */
    private Long pageId;
    /**
     * 页面apiID
     */
    private Long pageApiId;

    /**
     * 服务id
     */
    private Long serviceId;
    /**
     * api编码
     */
    private String apiCode;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 接口地址
     */
    private String interfaceUrl;
    /**
     * 接口参数
     */
    private String interfaceParam;
    /**
     * 执行时间点
     */
    private String runTime;
    /**
     * 是否拦截主操作
     */
    private Boolean isIntercept;
    /**
     * 事件类型：1-前端,2-后端
     */
    private String eventType;
    /**
     * 调用类型1-调用方法,2-调用接口,3-执行代码
     */
    private String callType;
    /**
     * 接口
     */
    private String interfaceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 代码内容
     */
    private String executableCode;

}
