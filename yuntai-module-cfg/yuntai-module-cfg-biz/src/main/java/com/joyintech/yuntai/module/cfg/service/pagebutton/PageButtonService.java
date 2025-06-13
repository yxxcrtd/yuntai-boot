package com.joyintech.yuntai.module.cfg.service.pagebutton;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagebutton.PageButtonDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面操作按钮 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageButtonService {

    /**
     * 创建页面操作按钮
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageButton(@Valid PageButtonSaveReqVO createReqVO);

    /**
     * 更新页面操作按钮
     *
     * @param updateReqVO 更新信息
     */
    void updatePageButton(@Valid PageButtonSaveReqVO updateReqVO);

    /**
     * 删除页面操作按钮
     *
     * @param id 编号
     */
    void deletePageButton(Long id);

    /**
     * 获得页面操作按钮
     *
     * @param id 编号
     * @return 页面操作按钮
     */
    PageButtonDO getPageButton(Long id);

    /**
     * 获得页面操作按钮分页
     *
     * @param pageReqVO 分页查询
     * @return 页面操作按钮分页
     */
    PageResult<PageButtonDO> getPageButtonPage(PageButtonPageReqVO pageReqVO);

}