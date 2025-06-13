package com.joyintech.yuntai.module.cfg.tabledefinition;

import com.joyintech.yuntai.module.cfg.tabledefinition.dto.TableDefinitionDTO;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/31
 */
public interface TableDefinitionApi {

    /**
     * 获取数据
     * @param dataSourceId
     * @param tableName
     * @return
     */
    TableDefinitionDTO selectTable(Long dataSourceId,String tableName);

    /**
     * 保存数据
     * @param dto
     * @return
     */
    void insertTable(TableDefinitionDTO dto);

}
