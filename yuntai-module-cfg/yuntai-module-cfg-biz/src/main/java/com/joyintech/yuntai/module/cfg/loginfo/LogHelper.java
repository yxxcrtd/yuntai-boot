package com.joyintech.yuntai.module.cfg.loginfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志助手
 *
 * @author hzz
 * @since 2024-12-19
 */
@Slf4j
public class LogHelper {

    public static void outPutLog(String format, Object... args) {
        try {
            // 使用String.format来格式化日志信息，并添加分隔符
            String formattedMessage = String.format(format, args);
            log.info("-----------------------------{}------------------------------", formattedMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
