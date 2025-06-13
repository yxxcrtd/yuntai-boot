package com.joyintech.yuntai.module.cfg.sqlgen.enums;

import lombok.AllArgsConstructor;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/27
 */
@AllArgsConstructor
public enum MySqlColType implements DbColType {
    INSTANCE(-1),
    //0-不需要长度,1-需要长度,2-需要长度和精度，3-需要长度，精度可选
    VARCHAR(1),
    INT(0),
    LONGTEXT(0),
    BIGINT(0),
    JSON(0),
    TEXT(0),
    MEDIUMTEXT(0),
    DATETIME(0),
    TIMESTAMP(0),
    CHAR(1),
    BINARY(0),
    FLOAT(2),
    VARBINARY(0),
    TINYINT(0),
    DECIMAL(2),
    BLOB(0),
    DOUBLE(2),
    BIT(0),
    MEDIUMBLOB(0),
    DATE(0);

    private final int code;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public DbColType get(String name) {
        for (MySqlColType colType : MySqlColType.values()) {
            if (colType.name().equalsIgnoreCase(name)) {
                return colType;
            }
        }
        return null;
    }
}
