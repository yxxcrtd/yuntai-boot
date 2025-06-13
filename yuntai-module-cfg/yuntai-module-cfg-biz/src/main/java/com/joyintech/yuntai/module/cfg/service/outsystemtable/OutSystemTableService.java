package com.joyintech.yuntai.module.cfg.service.outsystemtable;

import javax.validation.Valid;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo.OutSystemTablePageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo.OutSystemTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;

/**
 * 外部系统关联 Service 接口
 *
 * @author 兆尹云台
 */
public interface OutSystemTableService {

    /**
     * 创建外部系统关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOutSystemTable(@Valid OutSystemTableSaveReqVO createReqVO);

    /**
     * 更新外部系统关联
     *
     * @param updateReqVO 更新信息
     */
    void updateOutSystemTable(@Valid OutSystemTableSaveReqVO updateReqVO);

    /**
     * 删除外部系统关联
     *
     * @param id 编号
     */
    void deleteOutSystemTable(Long id);

    /**
     * 获得外部系统关联
     *
     * @param id 编号
     * @return 外部系统关联
     */
    OutSystemTableDO getOutSystemTable(Long id);

    /**
     * 获得外部系统关联分页
     *
     * @param pageReqVO 分页查询
     * @return 外部系统关联分页
     */
    PageResult<OutSystemTableDO> getOutSystemTablePage(OutSystemTablePageReqVO pageReqVO);

    /**
     * 根据流程ID，查找页面ID
     *
     * @param flowId
     * @return
     */
    Long getPageId(Long flowId, Long pageDataId);
}