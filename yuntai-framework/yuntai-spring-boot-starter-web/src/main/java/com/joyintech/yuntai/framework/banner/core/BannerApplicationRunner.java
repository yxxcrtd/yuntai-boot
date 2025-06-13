package com.joyintech.yuntai.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author 兆尹云台
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Resource
    private  Environment env;

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execute(() -> {
//            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            // 获取Environment 对象
            try {
                String ip = InetAddress.getLocalHost().getHostAddress();
                String port = env.getProperty("server.port");
                String path = StringUtils.isBlank(env.getProperty("server.servlet.context-path"))? "" : env.getProperty("server.servlet.context-path");
                log.info("\n----------------------------------------------------------\n\t" +
                        "项目启动成功！\n\t" +
                        "【本地地址】: \thttp://localhost:" + port + path + "/\n\t" +
                        "【外部地址】: \thttp://" + ip + ":" + port + path + "/\n\t" +
                        "【Swagger文档】: \thttp://" + ip + ":" + port + path + "/swagger-ui/index.html\n" +
                        "----------------------------------------------------------");
            } catch (Exception e) {
                log.error("系统启动异常！",e);
                throw new RuntimeException(e);
            }
//
//            log.info("\n----------------------------------------------------------\n\t" +
//                            "项目启动成功！\n\t" +
//                            "接口文档: \t{} \n\t" +
//                            "开发文档: \t{} \n\t" +
//                            "视频教程: \t{} \n" +
//                            "----------------------------------------------------------",
//                    "https://doc.joyintech.com/api-doc/",
//                    "https://doc.joyintech.com",
//                    "https://t.zsxq.com/02Yf6M7Qn");

            // 数据报表
            if (isNotPresent("com.joyintech.yuntai.module.report.framework.security.config.SecurityConfiguration")) {
                System.out.println("[报表模块 yuntai-module-report - 已禁用]");
            }
            // 工作流
            if (isNotPresent("com.joyintech.yuntai.module.bpm.framework.flowable.config.BpmFlowableConfiguration")) {
                System.out.println("[工作流模块 yuntai-module-bpm - 已禁用]");
            }
            // 商城系统
            if (isNotPresent("com.joyintech.yuntai.module.trade.framework.web.config.TradeWebConfiguration")) {
                System.out.println("[商城系统 yuntai-module-mall - 已禁用]");
            }
            // ERP 系统
            if (isNotPresent("com.joyintech.yuntai.module.erp.framework.web.config.ErpWebConfiguration")) {
                System.out.println("[ERP 系统 yuntai-module-erp - 已禁用]");
            }
            // CRM 系统
            if (isNotPresent("com.joyintech.yuntai.module.crm.framework.web.config.CrmWebConfiguration")) {
                System.out.println("[CRM 系统 yuntai-module-crm - 已禁用]");
            }
            // 微信公众号
            if (isNotPresent("com.joyintech.yuntai.module.mp.framework.mp.config.MpConfiguration")) {
                System.out.println("[微信公众号 yuntai-module-mp - 已禁用]");
            }
            // 支付平台
            if (isNotPresent("com.joyintech.yuntai.module.pay.framework.pay.config.PayConfiguration")) {
                System.out.println("[支付系统 yuntai-module-pay - 已禁用]");
            }
            // AI 大模型
            if (isNotPresent("com.joyintech.yuntai.module.ai.framework.web.config.AiWebConfiguration")) {
                System.out.println("[AI 大模型 yuntai-module-ai - 已禁用]");
            }
        });
    }

    private static boolean isNotPresent(String className) {
        return !ClassUtils.isPresent(className, ClassUtils.getDefaultClassLoader());
    }

}
