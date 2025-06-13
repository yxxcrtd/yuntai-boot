package com.joyintech.yuntai.module.cfg.api.tabledefinition;

import cn.hutool.core.bean.BeanUtil;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.tabledefinition.TableDefinitionMapper;
import com.joyintech.yuntai.module.cfg.tabledefinition.TableDefinitionApi;
import com.joyintech.yuntai.module.cfg.tabledefinition.dto.TableDefinitionDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/31
 */
@Service
public class TableDefinitionApiImpl implements TableDefinitionApi {
    @Resource
    private TableDefinitionMapper tableDefinitionMapper;

    @Override
    public TableDefinitionDTO selectTable(Long dataSourceId, String tableName) {
        TableDefinitionDO tableDefinitionDO =  tableDefinitionMapper.selectOne(TableDefinitionDO::getDatasourceId,dataSourceId,
                TableDefinitionDO::getTableName,tableName);
        return BeanUtil.toBean(tableDefinitionDO, TableDefinitionDTO.class);
    }

    @Override
    public void insertTable(TableDefinitionDTO dto) {
        tableDefinitionMapper.insert(BeanUtil.toBean(dto, TableDefinitionDO.class));
    }
}
