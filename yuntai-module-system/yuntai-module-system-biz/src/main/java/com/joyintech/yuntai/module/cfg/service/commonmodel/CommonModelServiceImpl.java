package com.joyintech.yuntai.module.cfg.service.commonmodel;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.commonmodel.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.commonmodel.CommonModelDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.commonmodel.CommonModelMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;
import static com.joyintech.yuntai.module.infra.enums.ErrorCodeConstants.COMMON_MODEL_NOT_EXISTS;

/**
 * 公共模型 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class CommonModelServiceImpl implements CommonModelService {

    @Resource
    private CommonModelMapper commonModelMapper;

    @Override
    public Long createCommonModel(CommonModelSaveReqVO createReqVO) {
        // 插入
        CommonModelDO commonModel = BeanUtils.toBean(createReqVO, CommonModelDO.class);
        commonModelMapper.insert(commonModel);
        // 返回
        return commonModel.getId();
    }

    @Override
    public void updateCommonModel(CommonModelSaveReqVO updateReqVO) {
        // 校验存在
        validateCommonModelExists(updateReqVO.getId());
        // 更新
        CommonModelDO updateObj = BeanUtils.toBean(updateReqVO, CommonModelDO.class);
        commonModelMapper.updateById(updateObj);
    }

    @Override
    public void deleteCommonModel(Long id) {
        // 校验存在
        validateCommonModelExists(id);
        // 删除
        commonModelMapper.deleteById(id);
    }

    private void validateCommonModelExists(Long id) {
        if (commonModelMapper.selectById(id) == null) {
            throw exception(COMMON_MODEL_NOT_EXISTS);
        }
    }

    @Override
    public CommonModelDO getCommonModel(Long id) {
        return commonModelMapper.selectById(id);
    }

    @Override
    public PageResult<CommonModelDO> getCommonModelPage(CommonModelPageReqVO pageReqVO) {
        return commonModelMapper.selectPage(pageReqVO);
    }

}