package com.joyintech.yuntai.module.cfg.dal.mysql.processdata;

import java.util.List;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.processdata.vo.ProcessDataPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdata.ProcessDataDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程Log日志 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ProcessDataMapper extends BaseMapperX<ProcessDataDO> {

    default PageResult<ProcessDataDO> selectPage(ProcessDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProcessDataDO>()
                .eqIfPresent(ProcessDataDO::getPageId, reqVO.getPageId())
                .eqIfPresent(ProcessDataDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(ProcessDataDO::getFlowRequestId, reqVO.getFlowRequestId())
                .eqIfPresent(ProcessDataDO::getNodeId, reqVO.getNodeId())
                .eqIfPresent(ProcessDataDO::getFormId, reqVO.getFormId())
                .eqIfPresent(ProcessDataDO::getFormData, reqVO.getFormData())
                .betweenIfPresent(ProcessDataDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProcessDataDO::getId));
    }

    /**
     * 流程日志数据
     *
     * @param reqVO 页面数据
     * @return
     */
    default List<ProcessDataDO> findProcessDataList(ProcessDataPageReqVO reqVO){
        return selectList(new LambdaQueryWrapperX<ProcessDataDO>()
                .eqIfPresent(ProcessDataDO::getPageId, reqVO.getPageId())
                .eqIfPresent(ProcessDataDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(ProcessDataDO::getFlowRequestId, reqVO.getFlowRequestId())
                .eqIfPresent(ProcessDataDO::getNodeId, reqVO.getNodeId())
                .eqIfPresent(ProcessDataDO::getFormId, reqVO.getFormId())
                .orderByDesc(ProcessDataDO::getUpdateTime));
    };
}