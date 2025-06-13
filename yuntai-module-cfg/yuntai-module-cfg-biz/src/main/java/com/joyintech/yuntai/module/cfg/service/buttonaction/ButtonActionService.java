package com.joyintech.yuntai.module.cfg.service.buttonaction;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.buttonaction.ButtonActionDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面按钮动作 Service 接口
 *
 * @author 兆尹云台
 */
public interface ButtonActionService {

    /**
     * 创建页面按钮动作
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createButtonAction(@Valid ButtonActionSaveReqVO createReqVO);

    /**
     * 更新页面按钮动作
     *
     * @param updateReqVO 更新信息
     */
    void updateButtonAction(@Valid ButtonActionSaveReqVO updateReqVO);

    /**
     * 删除页面按钮动作
     *
     * @param id 编号
     */
    void deleteButtonAction(Long id);

    /**
     * 获得页面按钮动作
     *
     * @param id 编号
     * @return 页面按钮动作
     */
    ButtonActionDO getButtonAction(Long id);

    /**
     * 获得页面按钮动作分页
     *
     * @param pageReqVO 分页查询
     * @return 页面按钮动作分页
     */
    PageResult<ButtonActionDO> getButtonActionPage(ButtonActionPageReqVO pageReqVO);

}