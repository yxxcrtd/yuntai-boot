package com.joyintech.yuntai.module.cfg.sqlgen.enums;

import lombok.AllArgsConstructor;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/27
 */
@AllArgsConstructor
public enum OracleColType implements DbColType {
    INSTANCE(-1),
    //0-不需要长度,1-需要长度,2-需要长度和精度，3-需要长度，精度可选
    NUMBER(3),
    VARCHAR2(1),
    CLOB(0),
    DATE(0),
    CHAR(1),
    BLOB(0),
    TIMESTAMP(0),
    FLOAT(2),
    NVARCHAR2(1);
    private final int code;


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public DbColType get(String name) {
        for (OracleColType colType : OracleColType.values()) {
            if (colType.name().equalsIgnoreCase(name)) {
                return colType;
            }
        }
        return null;
    }
}
