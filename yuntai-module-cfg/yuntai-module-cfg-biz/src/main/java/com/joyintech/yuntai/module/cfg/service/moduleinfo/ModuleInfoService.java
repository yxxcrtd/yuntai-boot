package com.joyintech.yuntai.module.cfg.service.moduleinfo;

import java.util.*;
import javax.validation.*;

import com.joyintech.yuntai.framework.common.util.collection.CollectionUtils;
import com.joyintech.yuntai.framework.common.util.tree.TreeNode;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;

/**
 * 模型信息 Service 接口
 *
 * @author 兆尹云台
 */
public interface ModuleInfoService {

    /**
     * 创建模型信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createModuleInfo(@Valid ModuleInfoSaveReqVO createReqVO);

    /**
     * 更新模型信息
     *
     * @param updateReqVO 更新信息
     */
    void updateModuleInfo(@Valid ModuleInfoSaveReqVO updateReqVO);

    /**
     * 删除模型信息
     *
     * @param id 编号
     */
    void deleteModuleInfo(Long id);

    /**
     * 获得模型信息
     *
     * @param id 编号
     * @return 模型信息
     */
    ModuleInfoRespVO getModuleInfo(Long id);

    /**
     * 模型列表
     * @return
     */
    List<TreeNode> listModuleInfo();

    /**
     * 获得部门信息数组
     *
     * @param ids 部门编号数组
     * @return 部门信息数组
     */
    List<ModuleInfoDO> getModuleInfoList(Collection<Long> ids);

    /**
     * 获得模型信息分页
     *
     * @param pageReqVO 分页查询
     * @return 模型信息分页
     */
    PageResult<ModuleInfoDO> getModuleInfoPage(ModuleInfoPageReqVO pageReqVO);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<Long, ModuleInfoDO> getModuleInfoMap(Collection<Long> ids) {
        List<ModuleInfoDO> list = getModuleInfoList(ids);
        return CollectionUtils.convertMap(list, ModuleInfoDO::getId);
    }

    /**
     * 刷新模型
     *
     * @param id
     */
    void refreshModuleInfo(Long id);

    /**
     * 复制模型信息
     *
     * @param id
     */
    void copyPageInfo(Long id);

    /**
     * 刷新模型数据
     */
    void refreshCache();
}
