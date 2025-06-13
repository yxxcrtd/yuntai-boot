package com.joyintech.yuntai.server;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 项目的启动类
 *
 *
 * @author 兆尹云台
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${yuntai.info.base-package}
@SpringBootApplication(scanBasePackages = {"${yuntai.info.base-package}.server", "${yuntai.info.base-package}.module",
        "${yuntai.info.base-package}.framework.mybatis.core.util"})
public class YuntaiServerApplication {

    public static void main(String[] args) throws UnknownHostException {

        SpringApplication.run(YuntaiServerApplication.class, args);

//        ConfigurableApplicationContext application = SpringApplication.run(YuntaiServerApplication.class, args);
//        Environment env = application.getEnvironment();
//        String ip = InetAddress.getLocalHost().getHostAddress();
//        String port = env.getProperty("server.port");
//        String path =env.getProperty("server.servlet.context-path");
//        System.out.println("\n----------------------------------------------------------\n\t" +
//                "Application Jeecg-Boot is running! Access URLs:\n\t" +
//                "【本地地址】: \t\thttp://localhost:" + port + path + "/\n\t" +
//                "【外部地址】: \thttp://" + ip + ":" + port + path + "/\n\t" +
//                "【Swagger文档】: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
//                "----------------------------------------------------------");

//        new SpringApplicationBuilder(YuntaiServerApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(20480))
//                .run(args);

    }

}
