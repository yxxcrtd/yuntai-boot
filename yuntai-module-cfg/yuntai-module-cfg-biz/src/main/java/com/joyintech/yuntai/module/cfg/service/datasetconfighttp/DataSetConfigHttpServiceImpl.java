package com.joyintech.yuntai.module.cfg.service.datasetconfighttp;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfighttp.vo.DataSetConfigHttpSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfighttp.DataSetConfigHttpDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.datasetconfighttp.DataSetConfigHttpMapper;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.DATA_SET_CONFIG_HTTP_NOT_EXISTS;

/**
 * 数据集-http请求内容 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DataSetConfigHttpServiceImpl implements DataSetConfigHttpService {

    @Resource
    private DataSetConfigHttpMapper dataSetConfigHttpMapper;

    @Override
    public Long createDataSetConfigHttp(DataSetConfigHttpSaveReqVO createReqVO) {
        // 插入
        DataSetConfigHttpDO dataSetConfigHttp = BeanUtils.toBean(createReqVO, DataSetConfigHttpDO.class);
        dataSetConfigHttpMapper.insert(dataSetConfigHttp);
        // 返回
        return dataSetConfigHttp.getId();
    }

    @Override
    public void updateDataSetConfigHttp(DataSetConfigHttpSaveReqVO updateReqVO) {
        // 校验存在
        validateDataSetConfigHttpExists(updateReqVO.getId());
        // 更新
        DataSetConfigHttpDO updateObj = BeanUtils.toBean(updateReqVO, DataSetConfigHttpDO.class);
        dataSetConfigHttpMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataSetConfigHttp(Long id) {
        // 校验存在
        validateDataSetConfigHttpExists(id);
        // 删除
        dataSetConfigHttpMapper.deleteById(id);
    }

    private void validateDataSetConfigHttpExists(Long id) {
        if (dataSetConfigHttpMapper.selectById(id) == null) {
            throw exception(DATA_SET_CONFIG_HTTP_NOT_EXISTS);
        }
    }

    @Override
    public DataSetConfigHttpDO getDataSetConfigHttp(Long id) {
        return dataSetConfigHttpMapper.selectById(id);
    }

    @Override
    public PageResult<DataSetConfigHttpDO> getDataSetConfigHttpPage(DataSetConfigHttpPageReqVO pageReqVO) {
        return dataSetConfigHttpMapper.selectPage(pageReqVO);
    }

}