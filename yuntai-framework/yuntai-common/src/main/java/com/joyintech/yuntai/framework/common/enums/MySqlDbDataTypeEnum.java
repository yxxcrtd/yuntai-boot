package com.joyintech.yuntai.framework.common.enums;

import java.util.Arrays;

import com.joyintech.yuntai.framework.common.core.IntArrayValuable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基于MySQL定义的数据库数据类型枚举
 *
 * @author abator 2024/9/26
 */
@Getter
@AllArgsConstructor
public enum MySqlDbDataTypeEnum implements IntArrayValuable {

    /**
     * 整数类型
     */
    // TINYINT("非常小的整数，范围是 -128 到 127（有符号）或 0 到 255（无符号）"),
    // SMALLINT("整数，范围是 -32,768 到 32,767（有符号）或 0 到 65,535（无符号）"),
    // MEDIUMINT("中等大小的整数，范围是 -8,388,608 到 8,388,607（有符号）或 0 到 16,777,215（无符号）"),
    INT("小标准整数，范围是 -2,147,483,648 到 2,147,483,647（有符号）或 0 到 4,294,967,295（无符号）"),
    BIGINT("大整数，范围是 -9,223,372,036,854,775,808 到 9,223,372,036,854,775,807（有符号）或 0 到 18,446,744,073,709,551,615（无符号）"),
    /**
     * 定点数类型
     */
    DECIMAL("DECIMAL(M,D): 定点数，M是总位数，D是小数位数"),
    /*
     * 浮点数类型
     */
    // FLOAT("单精度浮点数"),
    // DOUBLE("双精度浮点数"),
    /**
     * 字符串类型
     */
    CHAR("CHAR(M): 固定长度字符串，M是最大长度，范围是0到255"),
    VARCHAR("可变长度字符串，M是最大长度，范围是0到65,535"),
    TEXT("用于存储大文本数据，最大长度为65,535字符"),
    MEDIUMTEXT("用于存储更大的文本数据，最大长度为16,777,215字符"),
    LONGTEXT("用于存储非常大的文本数据，最大长度为4,294,967,295字符"),
    /**
     * 日期和时间类型
     */
    DATE("日期，格式为'YYYY-MM-DD'"),
    TIME("时间，格式为'HH:MM:SS'"),
    DATETIME("日期和时间，格式为'YYYY-MM-DD HH:MM:SS'"),
    // TIMESTAMP("时间戳，格式为'YYYY-MM-DD HH:MM:SS'，范围是'1970-01-01 00:00:01' UTC 到 '2038-01-19 03:14:07' UTC"),
    // YEAR("年份，格式为'YYYY'"),
    /**
     * 二进制类型
     */
    BIT("BIT(M): 位类型，M是位长度，范围是1到64"),
    BINARY("BINARY(M): 固定长度的二进制字符串，M是最大长度"),
    VARBINARY("VARBINARY(M): 可变长度的二进制字符串，M是最大长度"),
    BLOB("用于存储二进制大对象，最大长度为65,535字节"),
    MEDIUMBLOB("用于存储更大的二进制数据，最大长度为16,777,215字节"),
    LONGBLOB("用于存储非常大的二进制数据，最大长度为4,294,967,295字节"),
    /*
     * 枚举和集合类型
     */
    // ENUM("枚举类型，允许从预定义的值列表中选择一个值"),
    // SET("集合类型，允许从预定义的值列表中选择多个值"),
    /*
     * JSON类型
     */
    // JSON("用于存储JSON格式的数据。")
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(MySqlDbDataTypeEnum::ordinal).toArray();
    public static final String[] NAME_LIST = Arrays.stream(values()).map(MySqlDbDataTypeEnum::name).toArray(String[]::new);

    /**
     * 名称
     */
    private final String desc;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    @Override
    public String[] nameList() {
        return NAME_LIST;
    }

}
