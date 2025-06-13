package com.joyintech.yuntai.module.cfg.service.appinfo;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.appinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.appinfo.AppInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.appinfo.AppInfoMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 多应用 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class AppInfoServiceImpl implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;

    @Override
    public Long createAppInfo(AppInfoSaveReqVO createReqVO) {
        // 插入
        AppInfoDO appInfo = BeanUtils.toBean(createReqVO, AppInfoDO.class);
        appInfoMapper.insert(appInfo);
        // 返回
        return appInfo.getId();
    }

    @Override
    public void updateAppInfo(AppInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateAppInfoExists(updateReqVO.getId());
        // 更新
        AppInfoDO updateObj = BeanUtils.toBean(updateReqVO, AppInfoDO.class);
        appInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteAppInfo(Long id) {
        // 校验存在
        validateAppInfoExists(id);
        // 删除
        appInfoMapper.deleteById(id);
    }

    private void validateAppInfoExists(Long id) {
        if (appInfoMapper.selectById(id) == null) {
            throw exception(APP_INFO_NOT_EXISTS);
        }
    }

    @Override
    public AppInfoDO getAppInfo(Long id) {
        AppInfoDO appInfoDO = appInfoMapper.selectById(id);
        if (appInfoDO == null) {
            throw exception(APP_INFO_NOT_EXISTS);
        }
        return appInfoDO;
    }

    @Override
    public PageResult<AppInfoDO> getAppInfoPage(AppInfoPageReqVO pageReqVO) {
        return appInfoMapper.selectPage(pageReqVO);
    }

}