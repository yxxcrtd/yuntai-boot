package com.joyintech.yuntai.module.system.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模块类型枚举类
 *
 * @author 兆尹云台
 */
@Getter
@AllArgsConstructor
public enum MoudleTypeEnum {

    SYS(0), //  云台
    ADMIN(1), // 系统后台
    ;

    /**
     * 类型
     */
    private final Integer type;

}
