package com.joyintech.yuntai.module.cfg.service.dataconversion;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataconversion.DataConversionDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.dataconversion.DataConversionMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 数据转换 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DataConversionServiceImpl implements DataConversionService {

    @Resource
    private DataConversionMapper dataConversionMapper;

    @Override
    public Long createDataConversion(DataConversionSaveReqVO createReqVO) {
        // 插入
        DataConversionDO dataConversion = BeanUtils.toBean(createReqVO, DataConversionDO.class);
        dataConversionMapper.insert(dataConversion);
        // 返回
        return dataConversion.getId();
    }

    @Override
    public void updateDataConversion(DataConversionSaveReqVO updateReqVO) {
        // 校验存在
        validateDataConversionExists(updateReqVO.getId());
        // 更新
        DataConversionDO updateObj = BeanUtils.toBean(updateReqVO, DataConversionDO.class);
        dataConversionMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataConversion(Long id) {
        // 校验存在
        validateDataConversionExists(id);
        // 删除
        dataConversionMapper.deleteById(id);
    }

    private void validateDataConversionExists(Long id) {
        if (dataConversionMapper.selectById(id) == null) {
            throw exception(DATA_CONVERSION_NOT_EXISTS);
        }
    }

    @Override
    public DataConversionDO getDataConversion(Long id) {
        return dataConversionMapper.selectById(id);
    }

    @Override
    public PageResult<DataConversionDO> getDataConversionPage(DataConversionPageReqVO pageReqVO) {
        return dataConversionMapper.selectPage(pageReqVO);
    }

}