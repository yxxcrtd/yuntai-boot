package com.joyintech.yuntai.module.cfg.service.pageinfo;

import java.util.*;
import javax.validation.*;

import com.joyintech.yuntai.framework.common.util.tree.TreeNode;
import com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 页面基本信息 Service 接口
 *
 * @author 兆尹云台
 */
public interface PageInfoService {

    /**
     * 创建页面基本信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageApiInfo(@Valid PageInfoSaveReqVO createReqVO);

    /**
     * 更新页面基本信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePageInfo(@Valid PageInfoSaveReqVO updateReqVO);

    /**
     * 删除页面基本信息
     *
     * @param id 编号
     */
    void deletePageInfo(Long id);

    /**
     * 获得页面基本信息
     *
     * @param id 编号
     * @return 页面基本信息
     */
    PageInfoRespVO getPageInfo(Long id);

    /**
     * 获取树形列表
     * @return
     */
    List<TreeNode> listPage();

    /**
     * 获得页面基本信息分页
     *
     * @param pageReqVO 分页查询
     * @return 页面基本信息分页
     */
    PageResult<PageInfoDO> getPageInfoPage(PageInfoPageReqVO pageReqVO);

    /**
     * 获得页面基本信息根据id
     *
     * @param pageIds 列表查询
     * @return 页面基本信息列表
     */
    List<PageInfoDO> getPageList(Collection<Long> pageIds);

    /**
     * 复制页面基本信息
     *
     * @param id
     * @param type
     */
    void copyPageInfo(Long id, String type);
}
