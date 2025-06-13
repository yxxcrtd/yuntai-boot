package com.joyintech.yuntai.module.cfg.service.lowcodeapi;

import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.joyintech.yuntai.framework.common.pojo.LowCodeParam;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleCache;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/31
 */
public interface LowCodeService {
    // TODO：暂时因时间问题，只做oracle的修改，不考虑兼容性
    String PK_NAME_ONE = "ID";
    String PK_PREFIX_ONE = PK_NAME_ONE + "_";

    String PK_NAME_TWO = "id";
    String PK_PREFIX_TWO = PK_NAME_TWO + "_";

    /**
     * 处理低代码请求
     * @param param
     * @return
     */
    Object handle(LowCodeParam param, ModuleCache api);

    /**
     * 外部流程节点对应的java类
     *
     * @param params 数据
     * @param flowInfo 流程
     */
    Object processMethods(Map<String, Object> params, Map<String, Object> flowInfo, ModuleCache api);

    /**
     * 发起流程
     *
     * @param param 页面数据
     * @param handle1 主表id
     */
    OutSystemTableDO initiateDataProcess(LowCodeParam param, Object handle1);


    /**
     * 获取模型数据
     * @param param
     * @param api
     * @return
     */
    JSONObject getModelData(LowCodeParam param, ModuleCache api,ModuleCache updateApi);
}
