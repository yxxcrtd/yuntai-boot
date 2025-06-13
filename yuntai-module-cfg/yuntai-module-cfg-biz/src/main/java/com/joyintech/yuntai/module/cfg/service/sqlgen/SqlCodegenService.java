package com.joyintech.yuntai.module.cfg.service.sqlgen;

import java.util.List;

import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.sqlgen.model.Table;

/**
 * sql代码生成 Service 接口
 *
 * @author 兆尹云台
 */
public interface SqlCodegenService {

    /**
     * 获得表列表，基于表名称 + 表描述进行模糊匹配
     *
     * @param dataSourceConfigId 数据源配置的编号
     * @param nameLike 表名称，模糊匹配
     * @param commentLike 表描述，模糊匹配
     * @return 表列表
     */
    List<Table> getTableList(Long dataSourceConfigId, String nameLike, String commentLike);

    void initTableFromDatabase(List<TableDefinitionSaveReqVO> tables);
}
