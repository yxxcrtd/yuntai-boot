package com.joyintech.yuntai.module.cfg.service.templateinfo;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templateinfo.TemplateInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 模版 Service 接口
 *
 * @author 兆尹云台
 */
public interface TemplateInfoService {

    /**
     * 创建模版
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTemplateInfo(@Valid TemplateInfoSaveReqVO createReqVO);

    /**
     * 更新模版
     *
     * @param updateReqVO 更新信息
     */
    void updateTemplateInfo(@Valid TemplateInfoSaveReqVO updateReqVO);

    /**
     * 删除模版
     *
     * @param id 编号
     */
    void deleteTemplateInfo(Long id);

    /**
     * 获得模版
     *
     * @param id 编号
     * @return 模版
     */
    TemplateInfoRespVO getTemplateInfo(Long id);

    /**
     * 获得模版分页
     *
     * @param pageReqVO 分页查询
     * @return 模版分页
     */
    PageResult<TemplateInfoDO> getTemplateInfoPage(TemplateInfoPageReqVO pageReqVO);

}
