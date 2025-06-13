package com.joyintech.yuntai.module.cfg.service.componenttable;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.componenttable.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable.ComponentTableDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 组件 Service 接口
 *
 * @author 兆尹云台
 */
public interface ComponentTableService {

    /**
     * 创建组件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createComponentTable(@Valid ComponentTableSaveReqVO createReqVO);

    /**
     * 更新组件
     *
     * @param updateReqVO 更新信息
     */
    void updateComponentTable(@Valid ComponentTableSaveReqVO updateReqVO);

    /**
     * 删除组件
     *
     * @param id 编号
     */
    void deleteComponentTable(Long id);

    /**
     * 获得组件
     *
     * @param id 编号
     * @return 组件
     */
    ComponentTableDO getComponentTable(Long id);

    /**
     * 获得组件
     *
     * @param code 编号
     * @return 组件
     */
    ComponentTableDO getComponentTableByCode(String code);

    /**
     * 获得组件
     *
     * @param code 编号
     * @return 组件
     */
    List<ComponentTableDO> getComponentTableByCode(Set<String> code);

    /**
     * 获得组件分页
     *
     * @param pageReqVO 分页查询
     * @return 组件分页
     */
    PageResult<ComponentTableDO> getComponentTablePage(ComponentTablePageReqVO pageReqVO);

    /**
     * 获得全部组件
     *
     * @param pageReqVO 分页查询
     * @return 组件分页
     */
    List<ComponentTableDO> getComponentTableAllList();

}
