package com.joyintech.yuntai.framework.common.core;

/**
 * 可生成 Int 数组的接口
 *
 * @author 兆尹云台
 */
public interface IntArrayValuable {

    /**
     * @return int 数组
     */
    int[] array();

    /**
     * @return string 数组
     */
    default String[] nameList() {
        return new String[] {};
    }

}
