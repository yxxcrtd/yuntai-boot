package com.joyintech.yuntai.module.cfg.service.functioninfo;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.functioninfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.functioninfo.FunctionInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 开发平台功能管理 Service 接口
 *
 * @author 兆尹云台
 */
public interface FunctionInfoService {

    /**
     * 创建开发平台功能管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFunctionInfo(@Valid FunctionInfoSaveReqVO createReqVO);

    /**
     * 更新开发平台功能管理
     *
     * @param updateReqVO 更新信息
     */
    void updateFunctionInfo(@Valid FunctionInfoSaveReqVO updateReqVO);

    /**
     * 删除开发平台功能管理
     *
     * @param id 编号
     */
    void deleteFunctionInfo(Long id);

    /**
     * 获得开发平台功能管理
     *
     * @param id 编号
     * @return 开发平台功能管理
     */
    FunctionInfoDO getFunctionInfo(Long id);

    /**
     * 获得开发平台功能管理分页
     *
     * @param pageReqVO 分页查询
     * @return 开发平台功能管理分页
     */
    PageResult<FunctionInfoDO> getFunctionInfoPage(FunctionInfoPageReqVO pageReqVO);

    List<FunctionInfoDO> getFunctionInfoList(FunctionInfoPageReqVO pageReqVO);
}