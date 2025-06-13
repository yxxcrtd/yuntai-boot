package com.joyintech.yuntai.module.cfg.sqlgen.enums;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/27
 */
public interface DbColType {

    /**
     * 获取类型编码
     * @return
     */
    int getCode();

    /**
     * 获取类型编码
     * @return
     */
    DbColType get(String name);
}
