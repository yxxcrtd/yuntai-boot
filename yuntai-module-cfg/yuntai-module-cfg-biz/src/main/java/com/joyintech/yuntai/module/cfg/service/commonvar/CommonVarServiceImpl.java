package com.joyintech.yuntai.module.cfg.service.commonvar;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.commonvar.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.commonvar.CommonVarDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.commonvar.CommonVarMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;
import static com.joyintech.yuntai.module.infra.enums.ErrorCodeConstants.COMMON_VAR_NOT_EXISTS;

/**
 * 公共变量 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class CommonVarServiceImpl implements CommonVarService {

    @Resource
    private CommonVarMapper commonVarMapper;

    @Override
    public Long createCommonVar(CommonVarSaveReqVO createReqVO) {
        // 插入
        CommonVarDO commonVar = BeanUtils.toBean(createReqVO, CommonVarDO.class);
        commonVarMapper.insert(commonVar);
        // 返回
        return commonVar.getId();
    }

    @Override
    public void updateCommonVar(CommonVarSaveReqVO updateReqVO) {
        // 校验存在
        validateCommonVarExists(updateReqVO.getId());
        // 更新
        CommonVarDO updateObj = BeanUtils.toBean(updateReqVO, CommonVarDO.class);
        commonVarMapper.updateById(updateObj);
    }

    @Override
    public void deleteCommonVar(Long id) {
        // 校验存在
        validateCommonVarExists(id);
        // 删除
        commonVarMapper.deleteById(id);
    }

    private void validateCommonVarExists(Long id) {
        if (commonVarMapper.selectById(id) == null) {
            throw exception(COMMON_VAR_NOT_EXISTS);
        }
    }

    @Override
    public CommonVarDO getCommonVar(Long id) {
        return commonVarMapper.selectById(id);
    }

    @Override
    public PageResult<CommonVarDO> getCommonVarPage(CommonVarPageReqVO pageReqVO) {
        return commonVarMapper.selectPage(pageReqVO);
    }

}