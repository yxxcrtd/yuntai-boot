package com.joyintech.yuntai.module.cfg.service.datasetconfig;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.DATA_SET_CONFIG_NOT_EXISTS;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.DATA_SET_CONFIG_EXISTS;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.DATA_SET_CONFIG_NAME_EXISTS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo.DataSetConfigPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.datasetconfig.vo.DataSetConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfig.DataSetConfigDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasetconfighttp.DataSetConfigHttpDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.datasetconfig.DataSetConfigMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.datasetconfighttp.DataSetConfigHttpMapper;

import cn.hutool.core.collection.CollectionUtil;
import jodd.util.StringUtil;

/**
 * 数据集管理 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DataSetConfigServiceImpl implements DataSetConfigService {

    @Resource
    private DataSetConfigMapper dataSetConfigMapper;

    @Resource
    private DataSetConfigHttpMapper dataSetConfigHttpMapper;

    @Override
    public Long createDataSetConfig(DataSetConfigSaveReqVO createReqVO) {
        // 插入
        DataSetConfigDO dataSetConfig = BeanUtils.toBean(createReqVO, DataSetConfigDO.class);
        // 校验数据集名称、数据集编码的唯一性
        validateDataSetConfig(createReqVO.getId(), createReqVO.getName(), createReqVO.getCode());
        dataSetConfigMapper.insert(dataSetConfig);

        if(createReqVO.getHeaderList()!=null && !createReqVO.getHeaderList().isEmpty()){
            List<DataSetConfigHttpDO> headerList = BeanUtils.toBean(createReqVO.getHeaderList(), DataSetConfigHttpDO.class);
            headerList.forEach(e -> e.setDataId(dataSetConfig.getId()));
            dataSetConfigHttpMapper.insertBatch(headerList);
        }

        if(createReqVO.getConfigList()!=null && !createReqVO.getConfigList().isEmpty()){
            List<DataSetConfigHttpDO> configList = BeanUtils.toBean(createReqVO.getConfigList(), DataSetConfigHttpDO.class);
            configList.forEach(e -> e.setDataId(dataSetConfig.getId()));
            dataSetConfigHttpMapper.insertBatch(configList);
        }

        // 返回
        return dataSetConfig.getId();
    }

    /**
     * 校验数据集名称、数据集编码的唯一性
     *
     * @param id
     * @param name
     * @param code
     */
    private void validateDataSetConfig(Long id, String name, String code) {
        QueryWrapper<DataSetConfigDO> dataSetConfigDOQuery1Wrapper = new QueryWrapper<>();
        dataSetConfigDOQuery1Wrapper.eq(!StringUtil.isEmpty(name), "name", name);
        List<DataSetConfigDO> nameList = dataSetConfigMapper.selectList(dataSetConfigDOQuery1Wrapper);
        if((nameList!=null && !nameList.isEmpty() && nameList.size()>1) || (nameList!=null && !nameList.isEmpty() && !nameList.get(0).getId().equals(id))){
            throw exception(DATA_SET_CONFIG_NAME_EXISTS);
        }

        QueryWrapper<DataSetConfigDO> dataSetConfigDOQuery2Wrapper = new QueryWrapper<>();
        dataSetConfigDOQuery2Wrapper.eq(!StringUtil.isEmpty(name), "code", code);
        List<DataSetConfigDO> codeList = dataSetConfigMapper.selectList(dataSetConfigDOQuery2Wrapper);
        if((codeList!=null && !codeList.isEmpty() && codeList.size()>1) || (codeList!=null && !codeList.isEmpty() && !codeList.get(0).getId().equals(id))){
            throw exception(DATA_SET_CONFIG_EXISTS);
        }
    }

    @Override
    public void updateDataSetConfig(DataSetConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateDataSetConfigExists(updateReqVO.getId());
        // 校验数据集名称、数据集编码的唯一性
        validateDataSetConfig(updateReqVO.getId(), updateReqVO.getName(), updateReqVO.getCode());
        // 更新
        DataSetConfigDO updateObj = BeanUtils.toBean(updateReqVO, DataSetConfigDO.class);
        dataSetConfigMapper.updateById(updateObj);

        List<DataSetConfigHttpDO> list = new ArrayList<>();
        if (updateReqVO.getHeaderList()!=null && CollectionUtil.isNotEmpty(updateReqVO.getHeaderList())){
            List<DataSetConfigHttpDO> headerList = BeanUtils.toBean(updateReqVO.getHeaderList(), DataSetConfigHttpDO.class);
            if(headerList!=null && !headerList.isEmpty()){
                list.addAll(headerList);
            }
        }
        if (updateReqVO.getConfigList()!=null && CollectionUtil.isNotEmpty(updateReqVO.getConfigList())){
            List<DataSetConfigHttpDO> configList = BeanUtils.toBean(updateReqVO.getConfigList(), DataSetConfigHttpDO.class);
            if(configList!=null && !configList.isEmpty()){
                list.addAll(configList);
            }
        }
        if(!list.isEmpty()){
            list.forEach(http -> {
                http.setDataId(updateReqVO.getId());
            });
            dataSetConfigHttpMapper.insertOrUpdateBatch(list);
            dataSetConfigHttpMapper.delete(new QueryWrapper<DataSetConfigHttpDO>()
                    .notIn("id", list.stream().map(DataSetConfigHttpDO::getId).collect(Collectors.toList())));
        }
        else{
            dataSetConfigHttpMapper.delete(DataSetConfigHttpDO::getDataId, updateReqVO.getId());
        }

    }

    @Override
    public void deleteDataSetConfig(Long id) {
        // 校验存在
        validateDataSetConfigExists(id);
        // 删除
        dataSetConfigMapper.deleteById(id);

        dataSetConfigHttpMapper.delete(DataSetConfigHttpDO::getDataId, id);
    }

    private void validateDataSetConfigExists(Long id) {
        if (dataSetConfigMapper.selectById(id) == null) {
            throw exception(DATA_SET_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public DataSetConfigDO getDataSetConfig(Long id) {
        DataSetConfigDO dataSetConfigDO = dataSetConfigMapper.selectById(id);
        if("http".equals(dataSetConfigDO.getType())){
            List<DataSetConfigHttpDO> headerList = this.findHeaderList(dataSetConfigDO.getId(), "header");
            if(headerList!=null && !headerList.isEmpty()){
                dataSetConfigDO.setHeaderList(headerList);
            }
            List<DataSetConfigHttpDO> configList = this.findHeaderList(dataSetConfigDO.getId(), "config");
            if(configList!=null && !configList.isEmpty()){
                dataSetConfigDO.setConfigList(configList);
            }
        }
        return dataSetConfigDO;
    }

    @Override
    public PageResult<DataSetConfigDO> getDataSetConfigPage(DataSetConfigPageReqVO pageReqVO) {
        PageResult<DataSetConfigDO> list = dataSetConfigMapper.selectPage(pageReqVO);

        if(list!=null && list.getList()!=null && !list.getList().isEmpty()) {
            list.getList().forEach(data -> {
                if ("http".equals(data.getType())) {
                    List<DataSetConfigHttpDO> headerList = this.findHeaderList(data.getId(), "header");
                    if (headerList != null && !headerList.isEmpty()) {
                        data.setHeaderList(headerList);
                    }
                    List<DataSetConfigHttpDO> configList = this.findHeaderList(data.getId(), "config");
                    if (configList != null && !configList.isEmpty()) {
                        data.setConfigList(configList);
                    }
                }
            });
        }
        return list;
    }

    @Override
    public List<DataSetConfigDO> getDataSourceConfigList(String name) {
        QueryWrapper<DataSetConfigDO> dataSetConfigDOQueryWrapper = new QueryWrapper<>();
        dataSetConfigDOQueryWrapper.like(!StringUtil.isEmpty(name), "name", name);
        dataSetConfigDOQueryWrapper.orderByDesc("create_time");
        List<DataSetConfigDO> result = dataSetConfigMapper.selectList(dataSetConfigDOQueryWrapper);
        if(result!=null && !result.isEmpty()){
            result.forEach(data -> {
                if ("http".equals(data.getType())) {
                    List<DataSetConfigHttpDO> headerList = this.findHeaderList(data.getId(), "header");
                    if (headerList != null && !headerList.isEmpty()) {
                        data.setHeaderList(headerList);
                    }
                    List<DataSetConfigHttpDO> configList = this.findHeaderList(data.getId(), "config");
                    if (configList != null && !configList.isEmpty()) {
                        data.setConfigList(configList);
                    }
                }
            });
        }
        return result;
    }

    /**
     * 获得数据集管理
     *
     * @param code 编号
     * @return 数据集管理
     */
    @Override
    public DataSetConfigDO getDataSetConfigByCode(String code) {
        DataSetConfigDO dataSetConfig = dataSetConfigMapper.selectOne("code", code);
        if(dataSetConfig!=null && "json".equals(dataSetConfig.getType())){
            List<Map> mapList = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            map.put("jsonData", dataSetConfig.getJsonData());
            mapList.add(map);
            dataSetConfig.setData(mapList);
        }
        else if(dataSetConfig!=null && "sql".equals(dataSetConfig.getType())){
            String sql = dataSetConfig.getSqlData();
            if(StringUtils.isNotBlank(sql)){
                List<Map> mapList = dataSetConfigMapper.findMapList(sql);
                dataSetConfig.setData(mapList);
            }
        }

        if("http".equals(dataSetConfig.getType())){
            List<DataSetConfigHttpDO> headerList = this.findHeaderList(dataSetConfig.getId(), "header");
            if(headerList!=null && !headerList.isEmpty()){
                dataSetConfig.setHeaderList(headerList);
            }
            List<DataSetConfigHttpDO> configList = this.findHeaderList(dataSetConfig.getId(), "config");
            if(configList!=null && !configList.isEmpty()){
                dataSetConfig.setConfigList(configList);
            }

            // TODO 判断http是否是后端调用，是否需要返回调用结果，放在data中返回

        }

        return dataSetConfig;
    }

    /**
     * 数据集-http请求内容
     *
     * @param id
     * @param type
     * @return
     */
    private List<DataSetConfigHttpDO> findHeaderList(Long id, String type) {
        QueryWrapper<DataSetConfigHttpDO> qw = new QueryWrapper<>();
        qw.eq(id!=null,"data_id", id);
        qw.eq("type", type);
        return dataSetConfigHttpMapper.selectList(qw);
    }

}