package com.joyintech.yuntai.framework.common.enums;

import java.util.Arrays;

import com.joyintech.yuntai.framework.common.core.IntArrayValuable;

import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 删除状态枚举
 *
 * @author 兆尹云台
 */
@Getter
@AllArgsConstructor
public enum DelFlagEnum implements IntArrayValuable {

    NORMAL(0, "正常"),
    DELETED(1, "删除");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DelFlagEnum::getCode).toArray();

    /**
     * 状态值
     */
    private final Integer code;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static boolean isNormal(Integer status) {
        return ObjUtil.equal(NORMAL.code, status);
    }

    public static boolean isDeleted(Integer status) {
        return ObjUtil.equal(DELETED.code, status);
    }

}
