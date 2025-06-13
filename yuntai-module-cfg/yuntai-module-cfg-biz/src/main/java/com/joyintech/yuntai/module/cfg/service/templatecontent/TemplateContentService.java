package com.joyintech.yuntai.module.cfg.service.templatecontent;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templatecontent.TemplateContentDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 模版内容 Service 接口
 *
 * @author 兆尹云台
 */
public interface TemplateContentService {

    /**
     * 创建模版内容
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTemplateContent(@Valid TemplateContentSaveReqVO createReqVO);

    /**
     * 更新模版内容
     *
     * @param updateReqVO 更新信息
     */
    void updateTemplateContent(@Valid TemplateContentSaveReqVO updateReqVO);

    /**
     * 删除模版内容
     *
     * @param id 编号
     */
    void deleteTemplateContent(Long id);

    /**
     * 获得模版内容
     *
     * @param id 编号
     * @return 模版内容
     */
    TemplateContentDO getTemplateContent(Long id);

    /**
     * 获得模版内容分页
     *
     * @param pageReqVO 分页查询
     * @return 模版内容分页
     */
    PageResult<TemplateContentDO> getTemplateContentPage(TemplateContentPageReqVO pageReqVO);

}