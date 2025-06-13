package com.joyintech.yuntai.module.system.service.treedata;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.system.controller.admin.treedata.vo.TreeDataPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.treedata.vo.TreeDataSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.treedata.TreeDataDO;
import com.joyintech.yuntai.module.system.dal.mysql.treedata.TreeDataMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.*;

/**
 * 字典树子 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class TreeDataServiceImpl implements TreeDataService {

    @Resource
    private TreeDataMapper treeDataMapper;

    @Override
    public String createTreeData(TreeDataSaveReqVO createReqVO) {
        // 插入
        TreeDataDO treeData = BeanUtils.toBean(createReqVO, TreeDataDO.class);
        treeDataMapper.insert(treeData);
        // 返回
        return treeData.getId();
    }

    @Override
    public void updateTreeData(TreeDataSaveReqVO updateReqVO) {
        // 校验存在
        validateTreeDataExists(updateReqVO.getId());
        // 更新
        TreeDataDO updateObj = BeanUtils.toBean(updateReqVO, TreeDataDO.class);
        treeDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteTreeData(String id) {
        // 校验存在
        validateTreeDataExists(id);
        // 删除
        treeDataMapper.deleteById(id);
    }

    private void validateTreeDataExists(String id) {
        if (treeDataMapper.selectById(id) == null) {
            throw exception(TREE_DATA_NOT_EXISTS);
        }
    }

    @Override
    public TreeDataDO getTreeData(String id) {
        return treeDataMapper.selectById(id);
    }

    @Override
    public PageResult<TreeDataDO> getTreeDataPage(TreeDataPageReqVO pageReqVO) {
        return treeDataMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TreeDataDO> getTreeDataByTreeType(String treeType) {
        return treeDataMapper.selectList("tree_type", treeType);
    }

}