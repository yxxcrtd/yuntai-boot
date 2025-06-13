package com.joyintech.yuntai.module.cfg.service.paramterlist;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.paramterlist.ParamterListDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面路由参数 Service 接口
 *
 * @author 兆尹云台
 */
public interface ParamterListService {

    /**
     * 创建页面路由参数
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createParamterList(@Valid ParamterListSaveReqVO createReqVO);

    /**
     * 更新页面路由参数
     *
     * @param updateReqVO 更新信息
     */
    void updateParamterList(@Valid ParamterListSaveReqVO updateReqVO);

    /**
     * 删除页面路由参数
     *
     * @param id 编号
     */
    void deleteParamterList(Long id);

    /**
     * 获得页面路由参数
     *
     * @param id 编号
     * @return 页面路由参数
     */
    ParamterListDO getParamterList(Long id);

    /**
     * 获得页面路由参数分页
     *
     * @param pageReqVO 分页查询
     * @return 页面路由参数分页
     */
    PageResult<ParamterListDO> getParamterListPage(ParamterListPageReqVO pageReqVO);

}