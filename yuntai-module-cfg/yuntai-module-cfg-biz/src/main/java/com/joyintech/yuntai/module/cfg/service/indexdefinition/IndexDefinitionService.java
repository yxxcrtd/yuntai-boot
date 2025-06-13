package com.joyintech.yuntai.module.cfg.service.indexdefinition;

import javax.validation.Valid;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo.IndexDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.indexdefinition.vo.IndexDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.indexdefinition.IndexDefinitionDO;

/**
 * 索引定义 Service 接口
 *
 * @author 兆尹云台
 */
public interface IndexDefinitionService {

    /**
     * 创建索引定义
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createIndexDefinition(@Valid IndexDefinitionSaveReqVO createReqVO);

    /**
     * 更新索引定义
     *
     * @param updateReqVO 更新信息
     */
    void updateIndexDefinition(@Valid IndexDefinitionSaveReqVO updateReqVO);

    /**
     * 删除索引定义
     *
     * @param id 编号
     */
    void deleteIndexDefinition(Long id);

    /**
     * 获得索引定义
     *
     * @param id 编号
     * @return 索引定义
     */
    IndexDefinitionDO getIndexDefinition(Long id);

    /**
     * 获得索引定义分页
     *
     * @param pageReqVO 分页查询
     * @return 索引定义分页
     */
    PageResult<IndexDefinitionDO> getIndexDefinitionPage(IndexDefinitionPageReqVO pageReqVO);

}