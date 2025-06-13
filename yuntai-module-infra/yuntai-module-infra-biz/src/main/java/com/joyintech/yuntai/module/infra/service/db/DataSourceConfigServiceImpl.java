package com.joyintech.yuntai.module.infra.service.db;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.mybatis.core.query.QueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.util.JdbcUtils;
import com.joyintech.yuntai.module.infra.controller.admin.db.vo.DataSourceConfigSaveReqVO;
import com.joyintech.yuntai.module.infra.dal.dataobject.db.DataSourceConfigDO;
import com.joyintech.yuntai.module.infra.dal.mysql.db.DataSourceConfigMapper;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;

import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.infra.enums.ErrorCodeConstants.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源配置 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
@Slf4j
public class DataSourceConfigServiceImpl implements DataSourceConfigService {

    /**
     * 动态数据源
     */
    @Resource
    private DynamicRoutingDataSource dynamicDataSource;

    /**
     * 默认数据源创建器
     */
    @Resource
    private DefaultDataSourceCreator dataSourceCreator;

    /**
     * 动态数据源配置-application中配置参数-获取primary数据源
     */
    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Resource
    private DataSourceConfigMapper dataSourceConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDataSourceConfig(DataSourceConfigSaveReqVO createReqVO) {
        DataSourceConfigDO config = BeanUtils.toBean(createReqVO, DataSourceConfigDO.class);
        validateConnectionOK(config);
        // 插入
        dataSourceConfigMapper.insert(config);
        // 数据源添加到 Dynamic
        DataSourceProperty dataSourceProperty = BeanUtils.toBean(createReqVO, DataSourceProperty.class);
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        dynamicDataSource.addDataSource(String.valueOf(config.getId()), dataSource);
        // 返回
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDataSourceConfig(DataSourceConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateDataSourceConfigExists(updateReqVO.getId(),"update");
        DataSourceConfigDO updateObj = BeanUtils.toBean(updateReqVO, DataSourceConfigDO.class);
        validateConnectionOK(updateObj);

        // 更新
        dataSourceConfigMapper.updateById(updateObj);
        // 数据源更新到 Dynamic
        DataSourceProperty dataSourceProperty = BeanUtils.toBean(updateReqVO, DataSourceProperty.class);
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        dynamicDataSource.addDataSource(String.valueOf(updateObj.getId()), dataSource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataSourceConfig(Long id) {
        // 校验存在
        validateDataSourceConfigExists(id,"delete");
        // 删除
        dataSourceConfigMapper.deleteById(id);
        // 数据源删除 Dynamic
        dynamicDataSource.removeDataSource(String.valueOf(id));
    }

    private void validateDataSourceConfigExists(Long id,String op) {
        DataSourceConfigDO config = dataSourceConfigMapper.selectById(id);
        if (config == null) {
            throw exception(DATA_SOURCE_CONFIG_NOT_EXISTS);
        }
        if ("delete".equals(op) && Objects.equals(config.getCode(), "master")) {
            throw exception(DATA_SOURCE_CONFIG_NOT_DELETE);
        }
    }

    @Override
    public DataSourceConfigDO getDataSourceConfig(Long id) {
        // 如果 id 为 0，默认为 master 的数据源
        if (Objects.equals(id, DataSourceConfigDO.ID_MASTER)) {
            return buildMasterDataSourceConfig();
        }
        // 从 DB 中读取
        return dataSourceConfigMapper.selectById(id);
    }

    @Override
    public List<DataSourceConfigDO> getDataSourceConfigList(String name) {
        QueryWrapper<DataSourceConfigDO> dataSourceConfigDOQueryWrapper = new QueryWrapper<>();
        dataSourceConfigDOQueryWrapper.like(!StringUtil.isEmpty(name), "name", name);
        List<DataSourceConfigDO> result = dataSourceConfigMapper.selectList(dataSourceConfigDOQueryWrapper);
        // 补充 master 数据源,不采用取系统默认数据源的方式，采用从数据库获取的方式,然后控制数据源的code是master的数据不允许删除
        //result.add(0, buildMasterDataSourceConfig());
        return result;
    }

    @Override
    public List<DataSourceConfigDO> getDataSourceList(Set<Long> dataSourceIds) {
        return dataSourceConfigMapper.selectBatchIds(dataSourceIds);
    }

    @Override
    public DataSourceConfigDO getDataSource(String code) {
        return dataSourceConfigMapper.selectOne(DataSourceConfigDO::getCode,code);
    }

    @Override
    public void loadDataSourceFromDb() {
        QueryWrapper<DataSourceConfigDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("code", "master");
        List<DataSourceConfigDO> dataSourceConfigDOS = dataSourceConfigMapper.selectList(queryWrapper);
        dataSourceConfigDOS.forEach(config -> {
            // 异常 -- 程序启动失败
            // validateConnectionOK(config);
            boolean success = JdbcUtils.isConnectionOK(config.getUrl(), config.getUsername(), config.getPassword());
            if (!success) {
                log.error("[loadDataSourceFromDb][配置(id={}, url={}, username={}) 测试连接异常]",
                          config.getId(), config.getUrl(), config.getUsername());
                return;
            }
            DataSourceProperty dataSourceProperty = BeanUtils.toBean(config, DataSourceProperty.class);
            DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
            dynamicDataSource.addDataSource(String.valueOf(config.getId()), dataSource);
        });
    }

    private void validateConnectionOK(DataSourceConfigDO config) {
        boolean success = JdbcUtils.isConnectionOK(config.getUrl(), config.getUsername(), config.getPassword());
        if (!success) {
            throw exception(DATA_SOURCE_CONFIG_NOT_OK);
        }
    }

    private DataSourceConfigDO buildMasterDataSourceConfig() {
        String primary = dynamicDataSourceProperties.getPrimary();
        DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource().get(primary);
        return new DataSourceConfigDO().setId(DataSourceConfigDO.ID_MASTER).setName(primary)
                .setUrl(dataSourceProperty.getUrl()).setUsername(dataSourceProperty.getUsername())
                .setPassword(dataSourceProperty.getPassword());
    }

}
