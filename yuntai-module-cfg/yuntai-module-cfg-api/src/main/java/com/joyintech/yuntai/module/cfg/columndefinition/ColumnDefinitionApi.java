package com.joyintech.yuntai.module.cfg.columndefinition;

import com.joyintech.yuntai.module.cfg.columndefinition.dto.ColumnDefinitionDTO;

import java.util.List;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/31
 */
public interface ColumnDefinitionApi {
    /**
     * 批量保存
     * @param dataSourceId
     * @param list
     */
    void insertColumn(Long dataSourceId,List<ColumnDefinitionDTO> list);
}
