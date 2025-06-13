package com.joyintech.yuntai.module.cfg.sqlgen.enums;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/27
 */
public class ColTypeConstant {
    //0-不需要长度,1-需要长度,2-需要长度和精度，3-需要长度，精度可选
    public static final int NOT_LEGNTH = 0;
    public static final int LEGNTH = 1;
    public static final int LEGNTH_SCALE = 2;
    public static final int LEGNTH_OR_SCALE = 3;

}
