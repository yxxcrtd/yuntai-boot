package com.joyintech.yuntai.module.cfg.service.dataformat;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataformat.DataFormatDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.dataformat.DataFormatMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面数据格式化 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DataFormatServiceImpl implements DataFormatService {

    @Resource
    private DataFormatMapper dataFormatMapper;

    @Override
    public Long createDataFormat(DataFormatSaveReqVO createReqVO) {
        // 插入
        DataFormatDO dataFormat = BeanUtils.toBean(createReqVO, DataFormatDO.class);
        dataFormatMapper.insert(dataFormat);
        // 返回
        return dataFormat.getId();
    }

    @Override
    public void updateDataFormat(DataFormatSaveReqVO updateReqVO) {
        // 校验存在
        validateDataFormatExists(updateReqVO.getId());
        // 更新
        DataFormatDO updateObj = BeanUtils.toBean(updateReqVO, DataFormatDO.class);
        dataFormatMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataFormat(Long id) {
        // 校验存在
        validateDataFormatExists(id);
        // 删除
        dataFormatMapper.deleteById(id);
    }

    private void validateDataFormatExists(Long id) {
        if (dataFormatMapper.selectById(id) == null) {
            throw exception(DATA_FORMAT_NOT_EXISTS);
        }
    }

    @Override
    public DataFormatDO getDataFormat(Long id) {
        return dataFormatMapper.selectById(id);
    }

    @Override
    public PageResult<DataFormatDO> getDataFormatPage(DataFormatPageReqVO pageReqVO) {
        return dataFormatMapper.selectPage(pageReqVO);
    }

}