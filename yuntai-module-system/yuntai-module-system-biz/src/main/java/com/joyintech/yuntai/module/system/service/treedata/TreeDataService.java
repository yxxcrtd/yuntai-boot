package com.joyintech.yuntai.module.system.service.treedata;

import java.util.List;
import javax.validation.*;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.system.controller.admin.treedata.vo.TreeDataPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.treedata.vo.TreeDataSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.treedata.TreeDataDO;

/**
 * 字典树子 Service 接口
 *
 * @author 兆尹云台
 */
public interface TreeDataService {

    /**
     * 创建字典树子
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTreeData(@Valid TreeDataSaveReqVO createReqVO);

    /**
     * 更新字典树子
     *
     * @param updateReqVO 更新信息
     */
    void updateTreeData(@Valid TreeDataSaveReqVO updateReqVO);

    /**
     * 删除字典树子
     *
     * @param id 编号
     */
    void deleteTreeData(String id);

    /**
     * 获得字典树子
     *
     * @param id 编号
     * @return 字典树子
     */
    TreeDataDO getTreeData(String id);

    /**
     * 获得字典树子分页
     *
     * @param pageReqVO 分页查询
     * @return 字典树子分页
     */
    PageResult<TreeDataDO> getTreeDataPage(TreeDataPageReqVO pageReqVO);

    List<TreeDataDO> getTreeDataByTreeType(String treeType);
}