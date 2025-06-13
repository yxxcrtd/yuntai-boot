package com.joyintech.yuntai.module.cfg.service.loginfo;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.module.cfg.controller.admin.loginfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.loginfo.LogInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.loginfo.LogInfoMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 日志记录 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class LogInfoServiceImpl implements LogInfoService {

    @Resource
    private LogInfoMapper logInfoMapper;

    @Override
    public Long createLogInfo(LogInfoSaveReqVO createReqVO) {
        // 插入
        LogInfoDO logInfo = BeanUtils.toBean(createReqVO, LogInfoDO.class);
        logInfoMapper.insert(logInfo);
        // 返回
        return logInfo.getId();
    }

    @Override
    public void updateLogInfo(LogInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateLogInfoExists(updateReqVO.getId());
        // 更新
        LogInfoDO updateObj = BeanUtils.toBean(updateReqVO, LogInfoDO.class);
        logInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteLogInfo(Long id) {
        // 校验存在
        validateLogInfoExists(id);
        // 删除
        logInfoMapper.deleteById(id);
    }

    private void validateLogInfoExists(Long id) {
        if (logInfoMapper.selectById(id) == null) {
            throw exception(LOG_INFO_NOT_EXISTS);
        }
    }

    @Override
    public LogInfoDO getLogInfo(Long id) {
        return logInfoMapper.selectById(id);
    }

    @Override
    public PageResult<LogInfoDO> getLogInfoPage(LogInfoPageReqVO pageReqVO) {
        return logInfoMapper.selectPage(pageReqVO);
    }

}