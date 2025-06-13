package com.joyintech.yuntai.module.cfg.service.pagelistcondition;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistcondition.PageListConditionDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 表单页查询条件（待定） Service 接口
 *
 * @author 兆尹云台
 */
public interface PageListConditionService {

    /**
     * 创建表单页查询条件（待定）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageListCondition(@Valid PageListConditionSaveReqVO createReqVO);

    /**
     * 更新表单页查询条件（待定）
     *
     * @param updateReqVO 更新信息
     */
    void updatePageListCondition(@Valid PageListConditionSaveReqVO updateReqVO);

    /**
     * 删除表单页查询条件（待定）
     *
     * @param id 编号
     */
    void deletePageListCondition(Long id);

    /**
     * 获得表单页查询条件（待定）
     *
     * @param id 编号
     * @return 表单页查询条件（待定）
     */
    PageListConditionDO getPageListCondition(Long id);

    /**
     * 获得表单页查询条件（待定）分页
     *
     * @param pageReqVO 分页查询
     * @return 表单页查询条件（待定）分页
     */
    PageResult<PageListConditionDO> getPageListConditionPage(PageListConditionPageReqVO pageReqVO);

}