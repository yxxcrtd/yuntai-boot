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
public enum CfgActionTypeEnum {
    INSERT,
    UPDATE,
    DELETE,
    SELECT_LIST,
    GET_BY_ID,
    SELECT_PAGE,
    MAPPING_GET_BY_ID,
    CHILD_SELECT_LIST,
    MAPPING_CHILD_SELECT_LIST
    ;

}
