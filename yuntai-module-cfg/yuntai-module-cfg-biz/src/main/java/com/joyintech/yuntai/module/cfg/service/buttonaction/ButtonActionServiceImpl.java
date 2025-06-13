package com.joyintech.yuntai.module.cfg.service.buttonaction;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.buttonaction.ButtonActionDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.buttonaction.ButtonActionMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面按钮动作 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ButtonActionServiceImpl implements ButtonActionService {

    @Resource
    private ButtonActionMapper buttonActionMapper;

    @Override
    public Long createButtonAction(ButtonActionSaveReqVO createReqVO) {
        // 插入
        ButtonActionDO buttonAction = BeanUtils.toBean(createReqVO, ButtonActionDO.class);
        buttonActionMapper.insert(buttonAction);
        // 返回
        return buttonAction.getId();
    }

    @Override
    public void updateButtonAction(ButtonActionSaveReqVO updateReqVO) {
        // 校验存在
        validateButtonActionExists(updateReqVO.getId());
        // 更新
        ButtonActionDO updateObj = BeanUtils.toBean(updateReqVO, ButtonActionDO.class);
        buttonActionMapper.updateById(updateObj);
    }

    @Override
    public void deleteButtonAction(Long id) {
        // 校验存在
        validateButtonActionExists(id);
        // 删除
        buttonActionMapper.deleteById(id);
    }

    private void validateButtonActionExists(Long id) {
        if (buttonActionMapper.selectById(id) == null) {
            throw exception(BUTTON_ACTION_NOT_EXISTS);
        }
    }

    @Override
    public ButtonActionDO getButtonAction(Long id) {
        return buttonActionMapper.selectById(id);
    }

    @Override
    public PageResult<ButtonActionDO> getButtonActionPage(ButtonActionPageReqVO pageReqVO) {
        return buttonActionMapper.selectPage(pageReqVO);
    }

}