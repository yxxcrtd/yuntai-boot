package com.joyintech.yuntai.module.cfg.service.loginfo;

import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.loginfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;

/**
 * 日志记录 Service 接口
 *
 * @author 兆尹云台
 */
public interface LogInfoService {

    /**
     * 创建日志记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLogInfo(@Valid LogInfoSaveReqVO createReqVO);

    /**
     * 更新日志记录
     *
     * @param updateReqVO 更新信息
     */
    void updateLogInfo(@Valid LogInfoSaveReqVO updateReqVO);

    /**
     * 删除日志记录
     *
     * @param id 编号
     */
    void deleteLogInfo(Long id);

    /**
     * 获得日志记录
     *
     * @param id 编号
     * @return 日志记录
     */
    LogInfoDO getLogInfo(Long id);

    /**
     * 获得日志记录分页
     *
     * @param pageReqVO 分页查询
     * @return 日志记录分页
     */
    PageResult<LogInfoDO> getLogInfoPage(LogInfoPageReqVO pageReqVO);

}