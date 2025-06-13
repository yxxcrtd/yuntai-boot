package com.joyintech.yuntai.module.system.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 *
 * @author liuyanlong
 * @version 1.0
 * @since 2025/4/7
 */
@Component
public class ConfigUtils {
    public static String HOST;

    @Value("${fanwei.callbackUrl}")
    public void setHost(String host) {
        ConfigUtils.HOST = host;
    }
}
