package com.joyintech.yuntai.module.cfg.service.processdesign;

import java.util.List;
import java.util.Map;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.*;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node.Node;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdesign.ProcessDesignDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;

/**
 * 流程设计 Service 接口
 *
 * @author 兆尹云台
 */
public interface ProcessDesignService {

    /**
     * 创建流程设计
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProcessDesign(@Valid ProcessDesignSaveReqVO createReqVO);

    /**
     * 更新流程设计
     *
     * @param updateReqVO 更新信息
     */
    void updateProcessDesign(@Valid ProcessDesignSaveReqVO updateReqVO);

    /**
     * 删除流程设计
     *
     * @param id 编号
     */
    void deleteProcessDesign(Long id);

    /**
     * 获得流程设计
     *
     * @param id 编号
     * @return 流程设计
     */
    ProcessDesignSaveReqVO getProcessDesign(Long id);

    /**
     * 获得流程设计分页
     *
     * @param pageReqVO 分页查询
     * @return 流程设计分页
     */
    PageResult<ProcessDesignDO> getProcessDesignPage(ProcessDesignPageReqVO pageReqVO);

    Node getNode(String id, Long pageId, String flowId);

    /**
     * 外部流程节点对应的java类
     *
     * @param flowId 外部流程Id
     * @param nodeId 外部流程节点ID
     * @return
     */
    List<String> findProcessMethods(String flowId, String nodeId);

    Map<String, List<Map<String, String>>> importExcel(Long id, List<String> listSort, Map<String, List<Map<String, String>>> maps);
}
