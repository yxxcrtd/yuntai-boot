package com.joyintech.yuntai.module.cfg.dal.mysql.processdesign;

import java.util.List;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.mapper.BaseMapperX;
import com.joyintech.yuntai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.ProcessDesignPageReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdesign.ProcessDesignDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程设计 Mapper
 *
 * @author 兆尹云台
 */
@Mapper
public interface ProcessDesignMapper extends BaseMapperX<ProcessDesignDO> {

    default PageResult<ProcessDesignDO> selectPage(ProcessDesignPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProcessDesignDO>()
                .eqIfPresent(ProcessDesignDO::getMenuId, reqVO.getMenuId())
                .likeIfPresent(ProcessDesignDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(ProcessDesignDO::getPageId, reqVO.getPageId())
                .eqIfPresent(ProcessDesignDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(ProcessDesignDO::getProcessType, reqVO.getProcessType())
                .eqIfPresent(ProcessDesignDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ProcessDesignDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProcessDesignDO::getId));
    }

    /**
     * 物理删除记录
     * @param processId
     * @return
     */
    int deletePhysical(@Param("processId") Long processId);

    /**
     * 外部流程节点对应的java类
     *
     * @param flowId 外部流程Id
     * @param nodeId 外部流程节点ID
     * @return
     */
    List<String> findProcessMethods(@Param("flowId") String flowId, @Param("nodeId") String nodeId);
}
