package com.joyintech.yuntai.module.cfg.service.processdata;

import javax.validation.*;
import com.joyintech.yuntai.framework.common.pojo.LowCodeParam;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdata.ProcessDataDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveAgainReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.DataProcessSaveReqVO;

/**
 * 流程Log日志 Service 接口
 *
 * @author 兆尹云台
 */
public interface ProcessDataService {

    /**
     * 创建流程Log日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProcessData(@Valid ProcessDataSaveReqVO createReqVO);

    /**
     * 更新流程Log日志
     *
     * @param updateReqVO 更新信息
     */
    void updateProcessData(@Valid ProcessDataSaveReqVO updateReqVO);

    /**
     * 删除流程Log日志
     *
     * @param id 编号
     */
    void deleteProcessData(Long id);

    /**
     * 获得流程Log日志
     *
     * @param id 编号
     * @return 流程Log日志
     */
    ProcessDataDO getProcessData(Long id);

    /**
     * 获得流程Log日志分页
     *
     * @param pageReqVO 分页查询
     * @return 流程Log日志分页
     */
    PageResult<ProcessDataDO> getProcessDataPage(ProcessDataPageReqVO pageReqVO);

    /**
     * 流程日志数据
     *
     * @param reqVO 页面数据
     * @return
     */
    String findProcessDataList(ProcessDataPageReqVO reqVO);

    String projectAppear(DataProcessSaveReqVO dataProcessSaveReqVO, OutSystemTableDO outSystemTableDO, LogInfoDO logInfoDO,LowCodeParam param)
            throws IllegalAccessException;

    String projectAppearAgain(DataProcessSaveAgainReqVO dataProcessSaveReqVO, OutSystemTableDO outSystemTableDO, LogInfoDO logInfoDO) throws IllegalAccessException;
}