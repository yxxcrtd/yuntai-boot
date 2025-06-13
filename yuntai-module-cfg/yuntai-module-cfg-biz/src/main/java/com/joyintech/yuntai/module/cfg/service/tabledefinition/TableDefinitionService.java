package com.joyintech.yuntai.module.cfg.service.tabledefinition;

import javax.validation.Valid;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;

import java.util.List;

/**
 * 表定义 Service 接口
 *
 * @author 兆尹云台
 */
public interface TableDefinitionService {

    /**
     * 创建表定义
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTableDefinition(@Valid TableDefinitionSaveReqVO createReqVO);

    /**
     * 更新表定义
     *
     * @param updateReqVO 更新信息
     */
    void updateTableDefinition(@Valid TableDefinitionSaveReqVO updateReqVO);

    /**
     * 删除表定义
     *
     * @param id 编号
     */
    void deleteTableDefinition(Long id);

    /**
     * 获得表定义
     *
     * @param id 编号
     * @return 表定义
     */
    TableDefinitionDO getTableDefinition(Long id);

    /**
     * 获得表定义分页
     *
     * @param pageReqVO 分页查询
     * @return 表定义分页
     */
    PageResult<TableDefinitionDO> getTableDefinitionPage(TableDefinitionPageReqVO pageReqVO);

    /**
     * 获得表定义分页
     *
     * @param pageReqVO 分页查询
     * @return 表定义分页
     */
    List<TableDefinitionDO> getTableDefinitionList(TableDefinitionPageReqVO pageReqVO);

}
