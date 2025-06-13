package com.joyintech.yuntai.module.cfg.service.commonvar;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.commonvar.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.commonvar.CommonVarDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 公共变量 Service 接口
 *
 * @author 兆尹云台
 */
public interface CommonVarService {

    /**
     * 创建公共变量
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonVar(@Valid CommonVarSaveReqVO createReqVO);

    /**
     * 更新公共变量
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonVar(@Valid CommonVarSaveReqVO updateReqVO);

    /**
     * 删除公共变量
     *
     * @param id 编号
     */
    void deleteCommonVar(Long id);

    /**
     * 获得公共变量
     *
     * @param id 编号
     * @return 公共变量
     */
    CommonVarDO getCommonVar(Long id);

    /**
     * 获得公共变量分页
     *
     * @param pageReqVO 分页查询
     * @return 公共变量分页
     */
    PageResult<CommonVarDO> getCommonVarPage(CommonVarPageReqVO pageReqVO);

}