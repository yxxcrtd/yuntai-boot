package com.joyintech.yuntai.module.cfg.dal.mysql.columncomponentattribute;

import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 字段管理组件 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ColumnComponentAttributeMapper extends BaseMapperX<ColumnComponentAttributeDO> {

    /**
     * 查询组件的属性列表
     * @param ids
     * @return
     */
    List<ComponentAttributeSaveReqVO> selectComponentAttributeList(@Param("ids") Collection<Long> ids);

    List<ColumnComponentAttributeDO> selectColumnComponentAttributeList(@Param("ids") Collection<Long> ids);
}
