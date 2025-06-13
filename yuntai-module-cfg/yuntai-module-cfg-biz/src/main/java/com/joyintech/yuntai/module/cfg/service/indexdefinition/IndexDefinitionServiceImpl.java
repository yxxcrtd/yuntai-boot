package com.joyintech.yuntai.module.cfg.service.indexdefinition;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.INDEX_DEFINITION_NOT_EXISTS;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo.IndexDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo.IndexDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.indexdefinition.IndexDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.indexdefinition.IndexDefinitionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.tabledefinition.TableDefinitionMapper;
import com.joyintech.yuntai.module.cfg.service.sqlgen.CommonDdlService;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.TableStatementGenerator;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.TableStatementGeneratorFactory;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Index;
import com.joyintech.yuntai.module.cfg.sqlgen.trans.DynamicDataSourceExecuteHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 索引定义 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
@Slf4j
public class IndexDefinitionServiceImpl implements IndexDefinitionService {

    @Resource
    private IndexDefinitionMapper indexDefinitionMapper;

    @Resource
    private CommonDdlService commonDdlService;

    @Resource
    private TableDefinitionMapper tableDefinitionMapper;

    @Override
    public Long createIndexDefinition(IndexDefinitionSaveReqVO createReqVO) {
        // 校验存在
        TableDefinitionDO tableDefinitionDO =
                tableDefinitionMapper.getAndCheck(createReqVO.getTableId());
        // 转换
        Index index = BeanUtils.toBean(createReqVO, Index.class);
        // 补充索引表名
        index.setTableName(tableDefinitionDO.getTableName());

        // 生成建索引sql
        TableStatementGenerator generator =
                TableStatementGeneratorFactory.getGenerator(tableDefinitionDO.getDatasourceId());
        String sql = generator.generateCreateIndexStatement(index);
        // 根据数据源执行 ddl
        DynamicDataSourceExecuteHelper.executeWithDataSource(tableDefinitionDO.getDatasourceId(), () -> {
            List<String> sqlList = new ArrayList<>();
            sqlList.add(sql);
            commonDdlService.executeSql(sqlList);
            return null;
        });
        log.info("创建索引成功：" + sql);
        IndexDefinitionDO indexDefinition = BeanUtils.toBean(createReqVO, IndexDefinitionDO.class);
        // 补充数据源id--单个数据源下 索引名唯一
        indexDefinition.setDatasourceId(tableDefinitionDO.getDatasourceId());
        indexDefinitionMapper.insert(indexDefinition);
        // 返回
        return indexDefinition.getId();
    }

    @Override
    public void updateIndexDefinition(IndexDefinitionSaveReqVO updateReqVO) {
        // 校验存在
        validateIndexDefinitionExists(updateReqVO.getId());
        // 更新
        IndexDefinitionDO updateObj = BeanUtils.toBean(updateReqVO, IndexDefinitionDO.class);
        indexDefinitionMapper.updateById(updateObj);
    }

    @Override
    public void deleteIndexDefinition(Long id) {
        // 校验存在
        IndexDefinitionDO indexDefinitionDO = getAndCheck(id);
        TableDefinitionDO tableDefinitionDO =
                tableDefinitionMapper.getAndCheck(indexDefinitionDO.getTableId());
        // 转换
        Index index = BeanUtils.toBean(indexDefinitionDO, Index.class);
        // 补充索引表名
        index.setTableName(tableDefinitionDO.getTableName());
        // 生成drop索引sql
        TableStatementGenerator generator =
                TableStatementGeneratorFactory.getGenerator(tableDefinitionDO.getDatasourceId());
        String sql = generator.generateDropIndexStatement(index);
        // 根据数据源执行 ddl
        DynamicDataSourceExecuteHelper.executeWithDataSource(tableDefinitionDO.getDatasourceId(), () -> {
            List<String> sqlList = new ArrayList<>();
            sqlList.add(sql);
            commonDdlService.executeSql(sqlList);
            return null;
        });
        log.info("删除索引成功：" + sql);
        // 删除
        indexDefinitionMapper.deleteById(id);
    }

    private void validateIndexDefinitionExists(Long id) {
        if (indexDefinitionMapper.selectById(id) == null) {
            throw exception(INDEX_DEFINITION_NOT_EXISTS);
        }
    }

    private IndexDefinitionDO getAndCheck(Long id) {
        IndexDefinitionDO indexDefinitionDO = indexDefinitionMapper.selectById(id);
        if (indexDefinitionDO == null) {
            throw exception(INDEX_DEFINITION_NOT_EXISTS);
        }
        return indexDefinitionDO;
    }

    @Override
    public IndexDefinitionDO getIndexDefinition(Long id) {
        return indexDefinitionMapper.selectById(id);
    }

    @Override
    public PageResult<IndexDefinitionDO> getIndexDefinitionPage(IndexDefinitionPageReqVO pageReqVO) {
        return indexDefinitionMapper.selectPage(pageReqVO);
    }

}