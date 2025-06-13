package com.joyintech.yuntai.module.cfg.service.templategroup;

import java.util.*;
import javax.validation.*;

import com.joyintech.yuntai.module.cfg.controller.admin.componentgroup.vo.ComponentGroupRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.templategroup.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templategroup.TemplateGroupDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 模版分组 Service 接口
 *
 * @author 兆尹云台
 */
public interface TemplateGroupService {

    /**
     * 创建模版分组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTemplateGroup(@Valid TemplateGroupSaveReqVO createReqVO);

    /**
     * 更新模版分组
     *
     * @param updateReqVO 更新信息
     */
    void updateTemplateGroup(@Valid TemplateGroupSaveReqVO updateReqVO);

    /**
     * 删除模版分组
     *
     * @param id 编号
     */
    void deleteTemplateGroup(Long id);

    /**
     * 获得模版分组
     *
     * @param id 编号
     * @return 模版分组
     */
    TemplateGroupDO getTemplateGroup(Long id);

    /**
     * 树形结构
     *
     * @return list
     */
    List<TemplateGroupRespVO> getTree();

    /**
     * 获得模版分组分页
     *
     * @param pageReqVO 分页查询
     * @return 模版分组分页
     */
    PageResult<TemplateGroupDO> getTemplateGroupPage(TemplateGroupPageReqVO pageReqVO);

}
