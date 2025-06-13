package com.joyintech.yuntai.module.cfg.utils;

import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;

public class FuncUtil {
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 比较两个日期是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean compareDate(LocalDateTime d1, LocalDateTime d2) {
        if(d1 == null && d2 == null) return true;
        if(d1 == null || d2 == null) return false;
        return DateUtil.format(d1,DATE_FORMAT).equals(DateUtil.format(d2,DATE_FORMAT));
    }



}
