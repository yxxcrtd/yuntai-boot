package com.joyintech.yuntai.module.cfg.service.componentgroup;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.componentgroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentgroup.ComponentGroupDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 组件分组 Service 接口
 *
 * @author 兆尹云台
 */
public interface ComponentGroupService {

    /**
     * 创建组件分组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createComponentGroup(@Valid ComponentGroupSaveReqVO createReqVO);

    /**
     * 更新组件分组
     *
     * @param updateReqVO 更新信息
     */
    void updateComponentGroup(@Valid ComponentGroupSaveReqVO updateReqVO);

    /**
     * 删除组件分组
     *
     * @param id 编号
     */
    void deleteComponentGroup(Long id);

    /**
     * 获得组件分组
     *
     * @param id 编号
     * @return 组件分组
     */
    ComponentGroupDO getComponentGroup(Long id);

    /**
     * 获得组件分组分页
     *
     * @param pageReqVO 分页查询
     * @return 组件分组分页
     */
    PageResult<ComponentGroupDO> getComponentGroupPage(ComponentGroupPageReqVO pageReqVO);

    /**
     * 树形结构
     *
     * @return list
     */
    List<ComponentGroupRespVO> getTree();
}