package com.joyintech.yuntai.module.cfg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/18
 */
@Getter
@AllArgsConstructor
public enum CfgApiTypeEnum {
    CREATE("CREATE", "新增"),
    UPDATE("UPDATE", "修改"),
    DELETE("DELETE", "删除"),
    LIST("LIST", "列表"),
    CHILD_LIST("CHILD_LIST", "子表列表"),
    PAGE_LIST("PAGE_LIST", "分页列表"),
    GET_BY_ID("GET_BY_ID", "获取单条"),
    MAPPING_GET_BY_ID("MAPPING_GET_BY_ID", "获取映射单条数据");

    private String code;
    private String desc;
}
