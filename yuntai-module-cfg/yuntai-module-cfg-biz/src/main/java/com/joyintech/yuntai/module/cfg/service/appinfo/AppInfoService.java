package com.joyintech.yuntai.module.cfg.service.appinfo;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.appinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.appinfo.AppInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 多应用 Service 接口
 *
 * @author 兆尹云台
 */
public interface AppInfoService {

    /**
     * 创建多应用
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAppInfo(@Valid AppInfoSaveReqVO createReqVO);

    /**
     * 更新多应用
     *
     * @param updateReqVO 更新信息
     */
    void updateAppInfo(@Valid AppInfoSaveReqVO updateReqVO);

    /**
     * 删除多应用
     *
     * @param id 编号
     */
    void deleteAppInfo(Long id);

    /**
     * 获得多应用
     *
     * @param id 编号
     * @return 多应用
     */
    AppInfoDO getAppInfo(Long id);

    /**
     * 获得多应用分页
     *
     * @param pageReqVO 分页查询
     * @return 多应用分页
     */
    PageResult<AppInfoDO> getAppInfoPage(AppInfoPageReqVO pageReqVO);

}