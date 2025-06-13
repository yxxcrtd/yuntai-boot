package com.joyintech.yuntai.module.cfg.service.commonmodel;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.commonmodel.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.commonmodel.CommonModelDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 公共模型 Service 接口
 *
 * @author 兆尹云台
 */
public interface CommonModelService {

    /**
     * 创建公共模型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonModel(@Valid CommonModelSaveReqVO createReqVO);

    /**
     * 更新公共模型
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonModel(@Valid CommonModelSaveReqVO updateReqVO);

    /**
     * 删除公共模型
     *
     * @param id 编号
     */
    void deleteCommonModel(Long id);

    /**
     * 获得公共模型
     *
     * @param id 编号
     * @return 公共模型
     */
    CommonModelDO getCommonModel(Long id);

    /**
     * 获得公共模型分页
     *
     * @param pageReqVO 分页查询
     * @return 公共模型分页
     */
    PageResult<CommonModelDO> getCommonModelPage(CommonModelPageReqVO pageReqVO);

}