package com.joyintech.yuntai.module.cfg.service.columndefinition;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;

/**
 * 字段定义 Service 接口
 *
 * @author 兆尹云台
 */
public interface ColumnDefinitionService {

    /**
     * 保存字段定义
     *
     * @param reqVOList 字段信息列表
     * @param isTemp 是否是暂存
     * @param tableSql 虚拟表的sql
     */
    void saveColumnDefinition(@Valid List<ColumnDefinitionSaveReqVO> reqVOList,Boolean isTemp, String tableSql);

    /**
     * 保存字段定义
     *
     * @param dataSourceId 数据源id
     */
    void reloadTable(Long dataSourceId);

    /**
     * 创建字段定义
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createColumnDefinition(@Valid ColumnDefinitionSaveReqVO createReqVO);


    /**
     * 更新字段定义
     *
     * @param updateReqVO 更新信息
     */
    void updateColumnDefinition(@Valid ColumnDefinitionSaveReqVO updateReqVO);

    /**
     * 删除字段定义
     *
     * @param id 编号
     */
    void deleteColumnDefinition(Long id);

    /**
     * 获得字段定义
     *
     * @param id 编号
     * @return 字段定义
     */
    ColumnDefinitionDO getColumnDefinition(Long id);

    /**
     * 获得字段定义分页
     *
     * @param pageReqVO 分页查询
     * @return 字段定义分页
     */
    PageResult<ColumnDefinitionDO> getColumnDefinitionPage(ColumnDefinitionPageReqVO pageReqVO);

    /**
     * 根据列的id获取组件信息
     * @param ids
     * @return
     */
    List<ComponentAttributeSaveReqVO>  getAttrList(Set<Long> ids);

}
