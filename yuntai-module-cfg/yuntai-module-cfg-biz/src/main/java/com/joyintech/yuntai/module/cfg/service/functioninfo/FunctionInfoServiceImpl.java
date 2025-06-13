package com.joyintech.yuntai.module.cfg.service.functioninfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.service.menu.MenuApiServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.functioninfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.functioninfo.FunctionInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.functioninfo.FunctionInfoMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.*;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 开发平台功能管理 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class FunctionInfoServiceImpl implements FunctionInfoService {

    @Resource
    private FunctionInfoMapper functionInfoMapper;
    @Resource
    private MenuApiServiceImpl menuApi;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFunctionInfo(FunctionInfoSaveReqVO createReqVO) {
        FunctionInfoDO functionInfo = BeanUtils.toBean(createReqVO, FunctionInfoDO.class);
        functionInfoMapper.insert(functionInfo);
        //如果是模块则创建菜单
        menuApi.createFunction(functionInfo);
        // 返回
        return functionInfo.getId();
    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFunctionInfo(FunctionInfoSaveReqVO updateReqVO) {
        FunctionInfoDO old = validateFunctionInfoExists(updateReqVO.getId());
        FunctionInfoDO updateObj = BeanUtils.toBean(updateReqVO, FunctionInfoDO.class);
        functionInfoMapper.updateById(updateObj);
        //如果是模块则创建菜单
        menuApi.updateFunction(updateObj, old);
    }

    @Override
    public void deleteFunctionInfo(Long id) {
        // 校验存在
        FunctionInfoDO infoDO = validateFunctionInfoExists(id);
        // 校验是否有子集存在
        List<FunctionInfoDO> parentId = functionInfoMapper.selectList(new QueryWrapper<FunctionInfoDO>()
                .eq("parent_id", id)
        );
        if (parentId != null && !parentId.isEmpty()) {
            throw exception0(1_003_001_015, "该功能下有子集，请先删除子集");
        }
        // 删除
        functionInfoMapper.deleteById(id);
        menuApi.deleteFunction(infoDO);
    }

    private FunctionInfoDO validateFunctionInfoExists(Long id) {
        FunctionInfoDO infoDO = functionInfoMapper.selectById(id);
        if (infoDO == null) {
            throw exception(FUNCTION_INFO_NOT_EXISTS);
        }
        return infoDO;
    }

    @Override
    public FunctionInfoDO getFunctionInfo(Long id) {
        return functionInfoMapper.selectById(id);
    }

    @Override
    public PageResult<FunctionInfoDO> getFunctionInfoPage(FunctionInfoPageReqVO pageReqVO) {
        return functionInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FunctionInfoDO> getFunctionInfoList(FunctionInfoPageReqVO pageReqVO) {
        return functionInfoMapper.selectList();
    }
}
