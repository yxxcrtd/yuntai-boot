package com.joyintech.yuntai.module.cfg.service.componentattribute;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentattribute.ComponentAttributeDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 组件属性 Service 接口
 *
 * @author 兆尹云台
 */
public interface ComponentAttributeService {

    /**
     * 创建组件属性
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createComponentAttribute(@Valid ComponentAttributeSaveReqVO createReqVO);

    /**
     * 更新组件属性
     *
     * @param updateReqVO 更新信息
     */
    void updateComponentAttribute(@Valid ComponentAttributeSaveReqVO updateReqVO);

    /**
     * 删除组件属性
     *
     * @param id 编号
     */
    void deleteComponentAttribute(Long id);

    /**
     * 获得组件属性
     *
     * @param id 编号
     * @return 组件属性
     */
    ComponentAttributeDO getComponentAttribute(Long id);

    /**
     * 获得组件属性分页
     *
     * @param pageReqVO 分页查询
     * @return 组件属性分页
     */
    PageResult<ComponentAttributeDO> getComponentAttributePage(ComponentAttributePageReqVO pageReqVO);

    boolean createComponentAttributeList(@Valid List<ComponentAttributeSaveReqVO> createReqVO);

    List<ComponentAttributeRespVO> selectComponent(Long id);
}