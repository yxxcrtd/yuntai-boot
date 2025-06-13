package com.joyintech.yuntai.module.cfg.service.conditionaltable;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 条件 Service 接口
 *
 * @author 兆尹云台
 */
public interface ConditionalTableService {

    /**
     * 创建条件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createConditionalTable(@Valid ConditionalTableSaveReqVO createReqVO);

    /**
     * 更新条件
     *
     * @param updateReqVO 更新信息
     */
    void updateConditionalTable(@Valid ConditionalTableSaveReqVO updateReqVO);

    /**
     * 删除条件
     *
     * @param id 编号
     */
    void deleteConditionalTable(Long id);

    /**
     * 获得条件
     *
     * @param id 编号
     * @return 条件
     */
    ConditionalTableDO getConditionalTable(Long id);

    /**
     * 获得条件分页
     *
     * @param pageReqVO 分页查询
     * @return 条件分页
     */
    PageResult<ConditionalTableDO> getConditionalTablePage(ConditionalTablePageReqVO pageReqVO);

}