package com.joyintech.yuntai.module.cfg.utils;



import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;


/**
 * description :
 * author ：JHY
 * date : 2020/6/3
 * version : 1.0
 */
@Slf4j
public class IdentityVerifyUtil {
    public static final String APPID = "EECC5436-7968-4BE0-8C6C-89E9D88805EC";
//    public static final String HOST = "http://10.0.23.88";

//    @Value("${external.APPID:''}")
//    private static String APPID;

//    @Value("${external.HOST:''}")
//    private static String HOST;

//    @Value("${external.SECRET:''}")
//    private static String SECRET;
//
//    @Value("${external.secret:''}")
//    private static String secret;

    //系统公钥信息
    private String SPK = null;
    //秘钥信息
    private static final String SECRET = "0e011d0b-b6d1-4c23-a848-f4e7d0cae4b3";

    //秘钥信息
    private static final String secret = "il2mW2Nv0XhxiZrzdELBCfUu7ZOFhUGvbWWWf716omiYNgkGKNcjgEzICSLG4KaZg/BtvDCXzcQQN9u1oEXkqPsZVBw6f+6MDldSB+uXbcYzCh+Q4uWA9f92WSg/xniviwk+B8OnZXZh6tOw+b3UVNwbk0P7s9LHO+D2DutOyobFODwFEJIc4icnzXiTSD2ax+AQ4SbJkMqth7gN9UaAaU38CoS4VeTQBkVaJdRjWY8eBvY1vfudMQqFg9+cQUDoomTiay5NRgHSsUTcgr7oCPbg6CwuTiriDtLrgKAhxwc7SXtO+OrgD94A+7m1lnv2jRbTccTK8wbkqy5xbWzBUg==";

    private static IdentityVerifyUtil instance;

    public static synchronized IdentityVerifyUtil getInstance() {
        if (instance == null) {
            instance = new IdentityVerifyUtil();
//            instance.regist();
        }
        return instance;
    }

    private IdentityVerifyUtil() {
    }


//    private void regist() {
//        //httpclient的使用请自己封装，可参考ECOLOGY中HttpManager类
//        HttpManager http = new HttpManager();
//        //请求头信息封装集合
//        Map<String, String> heads = new HashMap<String, String>();
//        //获取当前异构系统RSA加密的公钥
//        String cpk = new RSA().getRSA_PUB();
//        //当前异构系统用于向ECOLOGY注册时使用的账号密码通过DES加密后密文进行传输
//        //kb1906及以上版本 已废弃账号密码校验
//        //封装参数到请求头
//        heads.put("appid", APPID);
//        heads.put("cpk", cpk);
//        //调用ECOLOGY系统接口进行注册
//        try {
//            String data = http.postDataSSL(HOST + "/api/ec/dev/auth/regist", new HashMap<>(), heads);
//            //返回的数据格式为json，具体格式参数格式请参考文末API介绍。
//            //注意此时如果注册成功会返回秘钥信息,请根据业务需要进行保存。
//            if (data != null) {
//                JSONObject result = JSON.parseObject(data);
//                if ("true".equals(result.getString("status"))) {
//                    this.SPK = result.getString("spk");
//                    this.SECRET = result.getString("secrit");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static String getToken() {
        //httpclient的使用请自己封装，可参考ECOLOGY中HttpManager类
        HttpManager http = new HttpManager();
        //请求头信息封装集合
        Map<String, String> heads = new HashMap<String, String>();
//        RSA rsa = new RSA(null, spk);
        //对秘钥进行加密传输，防止篡改数据
//        String secret = rsa.encryptBase64("", CharsetUtil.CHARSET_UTF_8, KeyType.PublicKey);
        //封装参数到请求头
        heads.put("appid", APPID);
        heads.put("secret", secret);
        //调用ECOLOGY系统接口进行申请
        try {
            String data = http.postDataSSL(ConfigUtil.HOST + "/api/ec/dev/auth/applytoken", new HashMap<>(), heads);
            if (data != null) {
                JSONObject res = JSON.parseObject(data);
                if ("true".equals(res.getString("status"))) {
                    return res.getString("token");
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    /**
     * 获取请求头信息
     * @param token
     * @param userid
     * @param spk
     * @return
     */
    public static Map<String, String> getHttpHeads(String token,String userid,String spk){
        Map<String, String> heads = new HashMap<>();
        heads.put("token", token);
        heads.put("appid", IdentityVerifyUtil.APPID);
        RSA rsa = new RSA(null, spk);
        //对秘钥进行加密传输，防止篡改数据
        String secretUserid = rsa.encryptBase64(userid, CharsetUtil.CHARSET_UTF_8, KeyType.PublicKey);
//        RSA rsa = new RSA();
//        String secretUserid = rsa.encrypt(null, userid, null, "utf-8", spk, false);
        heads.put("userid", secretUserid);
        return heads;
    }

    public String getSPK() {
        return SPK;
    }

    public String getSECRET() {
        return SECRET;
    }

    public static void main(String[] args) {

        getToken();
        //对秘钥进行加密传输，防止篡改数据
//        RSA rsa = new RSA();
//        String secret = rsa.encrypt(null, "326",
//                null, "utf-8", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs0/mmbRvuW96Nz+BRSauQB+dAPC8BRC7ATeMrirprLqSrjpwvUWPZeKj7y+1loYVuz5Hd1XEcd0QwljKZvd7V7onXAbSjglTmN5/0ong/s1cSleQ0Ql4YINj6iDeTxEYj+r2jd1KOZO7Q892FwGsz5kjV+rQCSdmRBqsNQRchA/KhoV/6EEXPaD0geo+cTh9s+BCL6086yT2Y97hE0cGqSJ7zX8U+x7O6HrWXM0jWP06iAXZw7N5KFX9E+DFtne9/8SS6ty5oZftOlOix+hpweyvobQkYsFSgXWdCUydDBcmyMIk5tVEeJKkxrSzs5mlzJg9IjfUXvvW/7pThviKpQIDAQAB", false);
//        System.out.println("输出结果为：   " + secret);
    }
}
