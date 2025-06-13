package com.joyintech.yuntai.module.system.dal.mysql.treedata;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import com.joyintech.yuntai.module.system.controller.admin.treedata.vo.TreeDataPageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.treedata.TreeDataDO;

/**
 * 字典树子 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface TreeDataMapper extends BaseMapperX<TreeDataDO> {

    default PageResult<TreeDataDO> selectPage(TreeDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TreeDataDO>()
                .eqIfPresent(TreeDataDO::getTreeType, reqVO.getTreeType())
                .eqIfPresent(TreeDataDO::getNodeCode, reqVO.getNodeCode())
                .eqIfPresent(TreeDataDO::getNodeText, reqVO.getNodeText())
                .eqIfPresent(TreeDataDO::getShortText, reqVO.getShortText())
                .eqIfPresent(TreeDataDO::getNodeLevel, reqVO.getNodeLevel())
                .eqIfPresent(TreeDataDO::getParentCode, reqVO.getParentCode())
                .eqIfPresent(TreeDataDO::getSortIndex, reqVO.getSortIndex())
                .eqIfPresent(TreeDataDO::getCreateBy, reqVO.getCreateBy())
                .eqIfPresent(TreeDataDO::getUpdateBy, reqVO.getUpdateBy())
                .betweenIfPresent(TreeDataDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TreeDataDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TreeDataDO::getTreeGroup, reqVO.getTreeGroup())
                .likeIfPresent(TreeDataDO::getTreeName, reqVO.getTreeName())
                .orderByDesc(TreeDataDO::getId));
    }

}