package com.joyintech.yuntai.module.cfg.service.processdesign;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joyintech.yuntai.framework.common.util.io.FileUtils;
import com.joyintech.yuntai.module.cfg.utils.FuncUtil;
import jodd.util.StringUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.Lists;
import com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.mybatis.core.util.DBDynamicSqlExecutorUtils;
import com.joyintech.yuntai.framework.security.core.util.SecurityFrameworkUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleCache;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleRelationFieldCache;
import com.joyintech.yuntai.module.cfg.controller.admin.lowcodeapi.vo.ModuleSqlCache;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.ProcessDesignPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.ProcessDesignSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.condition.Condition;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.condition.FilterRules;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums.*;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdesign.ProcessDesignDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnode.ProcessNodeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnodecondition.ProcessNodeConditionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnodeevent.ProcessNodeEventDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnodeformproperty.ProcessNodeFormPropertyDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnodepermissions.ProcessNodePermissionsDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.processdesign.ProcessDesignMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.processnode.ProcessNodeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.processnodecondition.ProcessNodeConditionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.processnodeevent.ProcessNodeEventMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.processnodeformproperty.ProcessNodeFormPropertyMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.processnodepermissions.ProcessNodePermissionsMapper;
import com.joyintech.yuntai.module.cfg.enums.CfgSystemFieldEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 流程设计 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ProcessDesignServiceImpl implements ProcessDesignService {
    private static final Logger log = LoggerFactory.getLogger(ProcessDesignServiceImpl.class);
    @Resource
    private ProcessDesignMapper processDesignMapper;
    @Resource
    private ProcessNodeMapper processNodeMapper;
    @Resource
    private ProcessNodeEventMapper processNodeEventMapper;
    @Resource
    private ProcessNodeConditionMapper processNodeConditionMapper;
    @Resource
    private ProcessNodeFormPropertyMapper processNodeFormPropertyMapper;
    @Resource
    private ProcessNodePermissionsMapper processNodePermissionsMapper;
    @Resource
    private DBDynamicSqlExecutorUtils executorUtils;

    private static final String ID_FORMAT_ONE = "ID_%s";
    private static final String CHILD_FLAG = "list_";
    private static final String PK_NAME_ONE = "ID";
    private static final String PK_PREFIX_ONE = PK_NAME_ONE + "_";
    private static final String PK_NAME_TWO = "id";
    private static final String PK_PREFIX_TWO = PK_NAME_TWO + "_";
    private static final String FIELD_FORMAT = "%s_%d";
    private static final String ATTACHMENT_LIST = "attachmentList";
    private static final String START_NO = "START_NO_";
    private static final String PRODUCT_BRAND = "PRODUCT_BRAND_";

    @Value("${ibps.isExistsProducts}")
    private String isExistsProducts;

    /**
     * 保存流程数据
     *
     * @param createReqVO
     */
    private void save(ProcessDesignSaveReqVO createReqVO) {
        // 保存流程节点
        List<ProcessNodeDO> nodeList = new ArrayList<>();
        List<ProcessNodeConditionDO> conditionList = new ArrayList<>();
        List<ProcessNodeEventDO> eventList = new ArrayList<>();
        List<ProcessNodeFormPropertyDO> formList = new ArrayList<>();
        List<ProcessNodePermissionsDO> permissionsList = new ArrayList<>();

        this.handleNode(createReqVO.getProcess(), createReqVO.getId(), nodeList, conditionList, eventList, formList, permissionsList);
        //保存数据
        if (CollUtil.isNotEmpty(nodeList)) {
            processNodeMapper.insertBatch(nodeList);
        }
        if (CollUtil.isNotEmpty(conditionList)) {
            processNodeConditionMapper.insertBatch(conditionList);
        }
        if (CollUtil.isNotEmpty(eventList)) {
            processNodeEventMapper.insertBatch(eventList);
        }
        if (CollUtil.isNotEmpty(formList)) {
            processNodeFormPropertyMapper.insertBatch(formList);
        }
        if (CollUtil.isNotEmpty(permissionsList)) {
            processNodePermissionsMapper.insertBatch(permissionsList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProcessDesign(ProcessDesignSaveReqVO createReqVO) {
        ProcessDesignDO processDesign = BeanUtils.toBean(createReqVO, ProcessDesignDO.class);
        processDesign.setId(IdWorker.getId());
        processDesignMapper.insert(processDesign);
        createReqVO.setId(processDesign.getId());
        this.save(createReqVO);
        // 返回
        return processDesign.getId();
    }

    /**
     * 递归处理节点
     *
     * @param node
     * @param processId
     */
    private void handleNode(Node node, Long processId, List<ProcessNodeDO> nodeList,
                            List<ProcessNodeConditionDO> conditionList, List<ProcessNodeEventDO> eventList,
                            List<ProcessNodeFormPropertyDO> formList, List<ProcessNodePermissionsDO> permissionsList) {
        ProcessNodeDO nodeDO = BeanUtils.toBean(node, ProcessNodeDO.class);
        nodeDO.setProcessId(processId);
        nodeList.add(nodeDO);
        //根据节点类型构建不同的数据
        if (NodeTypeEnum.START.getCode().equals(node.getType())) {
            StartNode startNode = (StartNode) node;
            this.setFormProperties(formList, startNode.getFormProperties(), startNode.getId());
            this.setExecutionListeners(eventList, startNode.getExecutionListeners(), startNode.getId());
        }
        if (NodeTypeEnum.CC.getCode().equals(node.getType())) {
            CcNode ccNode = (CcNode) node;
            this.setFormProperties(formList, ccNode.getFormProperties(), ccNode.getId());
            this.setOperations(permissionsList, ccNode.getOperations(), ccNode.getId());
            this.setExecutionListeners(eventList, ccNode.getExecutionListeners(), ccNode.getId());
            //处理集合转字符串
            nodeDO.setUsers(CollUtil.join(ccNode.getUsers(), ","));
            nodeDO.setRoles(CollUtil.join(ccNode.getRoles(), ","));
            nodeDO.setAssigneeType(ccNode.getAssigneeType().getType());
        }
        if (NodeTypeEnum.APPROVAL.getCode().equals(node.getType())) {
            ApprovalNode approvalNode = (ApprovalNode) node;
            this.setFormProperties(formList, approvalNode.getFormProperties(), approvalNode.getId());
            this.setOperations(permissionsList, approvalNode.getOperations(), approvalNode.getId());
            this.setExecutionListeners(eventList, approvalNode.getTaskListeners(), approvalNode.getId());
            //处理集合转字符串
            nodeDO.setUsers(CollUtil.join(approvalNode.getUsers(), ","));
            nodeDO.setRoles(CollUtil.join(approvalNode.getRoles(), ","));
            nodeDO.setNobodyUsers(CollUtil.join(approvalNode.getNobodyUsers(), ","));
            nodeDO.setNobody(approvalNode.getNobody().getNobody());
            nodeDO.setMulti(approvalNode.getMulti().getMulti());
            nodeDO.setAssigneeType(approvalNode.getAssigneeType().getType());
        }
        if (NodeTypeEnum.CONDITION.getCode().equals(node.getType())) {
            ConditionNode conditionNode = (ConditionNode) node;
            this.setCondition(conditionList, eventList, conditionNode);
        }
        if (NodeTypeEnum.EXCLUSIVE.getCode().equals(node.getType())) {
            ExclusiveNode exclusiveNode = (ExclusiveNode) node;
            this.setExecutionListeners(eventList, exclusiveNode.getExecutionListeners(), exclusiveNode.getId());
            if (CollUtil.isNotEmpty(exclusiveNode.getChildren())) {
                exclusiveNode.getChildren().forEach(child -> {
                    ProcessNodeDO nd = BeanUtils.toBean(child, ProcessNodeDO.class);
                    nd.setProcessId(processId);
                    nodeList.add(nd);
                    this.setCondition(conditionList, eventList, child);
                });
            }
        }
        if (NodeTypeEnum.NOTIFY.getCode().equals(node.getType())) {
            NotifyNode notifyNode = (NotifyNode) node;
            this.setExecutionListeners(eventList, notifyNode.getExecutionListeners(), notifyNode.getId());
            //处理集合转字符串
            nodeDO.setUsers(CollUtil.join(notifyNode.getUsers(), ","));
            nodeDO.setRoles(CollUtil.join(notifyNode.getRoles(), ","));
            nodeDO.setTypes(CollUtil.join(NotifyTypeEnum.getByType(notifyNode.getTypes()), ","));
            nodeDO.setAssigneeType(notifyNode.getAssigneeType().getType());
        }
        if (NodeTypeEnum.TIMER.getCode().equals(node.getType())) {
            TimerNode timerNode = (TimerNode) node;
            this.setExecutionListeners(eventList, timerNode.getExecutionListeners(), timerNode.getId());
            nodeDO.setWaitType(timerNode.getWaitType().getType());
        }
        if (NodeTypeEnum.END.getCode().equals(node.getType())) {
            EndNode endNode = (EndNode) node;
            this.setExecutionListeners(eventList, endNode.getExecutionListeners(), endNode.getId());
        }
        if (Objects.nonNull(node.getChild())) {
            this.handleNode(node.getChild(), processId, nodeList, conditionList, eventList, formList, permissionsList);
        }
    }

    /**
     * 设置条件
     *
     * @param conditionList
     * @param eventList
     * @param conditionNode
     */
    private void setCondition(List<ProcessNodeConditionDO> conditionList, List<ProcessNodeEventDO> eventList, ConditionNode conditionNode) {
        this.setExecutionListeners(eventList, conditionNode.getExecutionListeners(), conditionNode.getId());
        if (Objects.nonNull(conditionNode.getConditions())) {
            ProcessNodeConditionDO conditionDO = new ProcessNodeConditionDO();
            conditionDO.setNodeId(conditionNode.getId());
            conditionDO.setOperator(conditionNode.getConditions().getOperator());
            conditionDO.setType("0");//0-条件,1-分组
            if (CollUtil.isNotEmpty(conditionNode.getConditions().getConditions())) {
                conditionNode.getConditions().getConditions().forEach(condition -> {
                    ProcessNodeConditionDO condDO = BeanUtils.toBean(conditionDO, ProcessNodeConditionDO.class);
                    condDO.setField(condition.getField());
                    condDO.setValue(Objects.nonNull(condition.getValue()) ? String.valueOf(condition.getValue()) : null);
                    conditionList.add(condDO);
                });
            } else {
                conditionList.add(conditionDO);
            }
            if (CollUtil.isNotEmpty(conditionNode.getConditions().getGroups())) {
                conditionNode.getConditions().getGroups().forEach(condition -> {
                    if (CollUtil.isNotEmpty(condition.getConditions())) {
                        ProcessNodeConditionDO groupCond = new ProcessNodeConditionDO();
                        groupCond.setGroupId(IdWorker.getId());
                        groupCond.setNodeId(conditionNode.getId());
                        groupCond.setOperator(condition.getOperator());
                        groupCond.setType("1");//0-条件,1-分组
                        if (CollUtil.isNotEmpty(condition.getConditions())) {
                            condition.getConditions().forEach(condition1 -> {
                                ProcessNodeConditionDO condDO = BeanUtils.toBean(groupCond, ProcessNodeConditionDO.class);
                                condDO.setField(condition1.getField());
                                condDO.setValue(Objects.nonNull(condition1.getValue()) ? String.valueOf(condition1.getValue()) : null);
                                conditionList.add(condDO);
                            });
                        } else {
                            conditionList.add(groupCond);
                        }
                        //不递归了，这里最多让其支持三层分组
                        condition.getGroups().forEach(condition2 -> {
                            ProcessNodeConditionDO groupCond2 = new ProcessNodeConditionDO();
                            groupCond2.setGroupId(IdWorker.getId());
                            groupCond2.setParentGroupId(groupCond.getGroupId());
                            groupCond2.setNodeId(conditionNode.getId());
                            groupCond2.setOperator(condition2.getOperator());
                            groupCond2.setType("1");//0-条件,1-分组
                            if (CollUtil.isNotEmpty(condition2.getConditions())) {
                                condition2.getConditions().forEach(condition3 -> {
                                    ProcessNodeConditionDO condDO = BeanUtils.toBean(groupCond2, ProcessNodeConditionDO.class);
                                    condDO.setField(condition3.getField());
                                    condDO.setValue(Objects.nonNull(condition3.getValue()) ? String.valueOf(condition3.getValue()) : null);
                                    conditionList.add(condDO);
                                });
                            } else {
                                conditionList.add(groupCond2);
                            }
                        });
                    }
                });
            }
        }
    }

    /**
     * 设置表单属性
     *
     * @param formList
     * @param formProperties
     * @param nodeId
     */
    private void setFormProperties(List<ProcessNodeFormPropertyDO> formList, List<FormProperty> formProperties, String nodeId) {
        if (CollUtil.isEmpty(formProperties)) return;
        formProperties.forEach(formProperty -> {
            ProcessNodeFormPropertyDO propertyDO = BeanUtils.toBean(formProperty, ProcessNodeFormPropertyDO.class);
            propertyDO.setNodeId(nodeId);
            formList.add(propertyDO);
        });
    }

    /**
     * 设置表单属性
     *
     * @param eventList
     * @param executionListeners
     * @param nodeId
     */
    private void setExecutionListeners(List<ProcessNodeEventDO> eventList, List<NodeListener> executionListeners, String nodeId) {
        if (CollUtil.isEmpty(executionListeners)) return;
        executionListeners.forEach(listener -> {
            ProcessNodeEventDO eventDO = BeanUtils.toBean(listener, ProcessNodeEventDO.class);
            eventDO.setNodeId(nodeId);
            eventList.add(eventDO);
        });
    }

    /**
     * 设置表单属性
     *
     * @param permissionsList
     * @param operations
     * @param nodeId
     */
    private void setOperations(List<ProcessNodePermissionsDO> permissionsList, Map<String, Boolean> operations, String nodeId) {
        if (CollUtil.isEmpty(operations)) return;
        operations.forEach((k, v) -> {
            ProcessNodePermissionsDO permissionsDO = new ProcessNodePermissionsDO();
            permissionsDO.setNodeId(nodeId);
            permissionsDO.setOperation(k);
            permissionsDO.setOperationValue(v);
            permissionsList.add(permissionsDO);
        });
    }


    @Override
    public void updateProcessDesign(ProcessDesignSaveReqVO updateReqVO) {
        // 校验存在
        validateProcessDesignExists(updateReqVO.getId(),updateReqVO);
        // 更新
        ProcessDesignDO updateObj = BeanUtils.toBean(updateReqVO, ProcessDesignDO.class);
        //物理删除,通过processId删除
        processNodeConditionMapper.deletePhysical(updateObj.getId());
        processNodeEventMapper.deletePhysical(updateObj.getId());
        processNodeFormPropertyMapper.deletePhysical(updateObj.getId());
        processNodePermissionsMapper.deletePhysical(updateObj.getId());
        processNodeMapper.deletePhysical(updateObj.getId());
        processDesignMapper.updateById(updateObj);
        this.save(updateReqVO);
    }


    @Override
    public void deleteProcessDesign(Long id) {
        //物理删除,通过processId删除
        processNodeConditionMapper.deletePhysical(id);
        processNodeEventMapper.deletePhysical(id);
        processNodeFormPropertyMapper.deletePhysical(id);
        processNodePermissionsMapper.deletePhysical(id);
        processNodeMapper.deletePhysical(id);
        processDesignMapper.deleteById(id);

    }

    private void validateProcessDesignExists(Long id,ProcessDesignSaveReqVO updateReqVO) {
        ProcessDesignDO designDO = processDesignMapper.selectById(id);
        if (designDO == null) {
            throw exception(PROCESS_DESIGN_NOT_EXISTS);
        }
        if(!FuncUtil.compareDate(designDO.getUpdateTime(),updateReqVO.getUpdateTimeStamp())) {
            throw exception(PROCESS_CHANGE);
        }
    }

    @Override
    public ProcessDesignSaveReqVO getProcessDesign(Long id) {
        //这里开始查询节点
        ProcessDesignDO processDesign = processDesignMapper.selectById(id);
        ProcessDesignSaveReqVO result = BeanUtils.toBean(processDesign, ProcessDesignSaveReqVO.class);
        List<ProcessNodeDO> processNodeList = processNodeMapper.selectList(ProcessNodeDO::getProcessId, id);
        if (CollUtil.isEmpty(processNodeList)) {
            return result;
        }
        Set<String> nodeIds = processNodeList.stream().map(ProcessNodeDO::getId).collect(Collectors.toSet());
        QueryWrapper queryWrapper = new QueryWrapper<>().in("node_id", nodeIds);
        List<ProcessNodeEventDO> eventList = processNodeEventMapper.selectList(queryWrapper);
        List<ProcessNodeConditionDO> conditionList = processNodeConditionMapper.selectList(queryWrapper);
        List<ProcessNodeFormPropertyDO> formList = processNodeFormPropertyMapper.selectList(queryWrapper);
        List<ProcessNodePermissionsDO> permissionsList = processNodePermissionsMapper.selectList(queryWrapper);
        Map<String, List<ProcessNodeEventDO>> eventMap = eventList.stream().collect(Collectors.groupingBy(ProcessNodeEventDO::getNodeId));
        Map<String, List<ProcessNodeConditionDO>> conditionMap = conditionList.stream().collect(Collectors.groupingBy(ProcessNodeConditionDO::getNodeId));
        Map<String, List<ProcessNodeFormPropertyDO>> formMap = formList.stream().collect(Collectors.groupingBy(ProcessNodeFormPropertyDO::getNodeId));
        Map<String, List<ProcessNodePermissionsDO>> permissionsMap = permissionsList.stream().collect(Collectors.groupingBy(ProcessNodePermissionsDO::getNodeId));

        Node root = new StartNode();
        List<Node> nodes = new ArrayList<>();
        for (ProcessNodeDO node : processNodeList) {//根据节点类型将节点进行赋值
            if (NodeTypeEnum.START.getCode().equals(node.getType())) {
                StartNode startNode = BeanUtils.toBean(node, StartNode.class);
                startNode.setFormProperties(formMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(formMap.get(node.getId()), FormProperty.class));
                startNode.setExecutionListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));
                nodes.add(startNode);
            }
            if (NodeTypeEnum.CC.getCode().equals(node.getType())) {
                CcNode ccNode = BeanUtils.toBean(node, CcNode.class);
                ccNode.setFormProperties(formMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(formMap.get(node.getId()), FormProperty.class));
                ccNode.setExecutionListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));

                Map<String, Boolean> operations = new HashMap<>();
                if (CollUtil.isNotEmpty(permissionsMap.get(node.getId()))) {
                    permissionsMap.get(node.getId()).forEach(permissionsDO -> {
                        operations.put(permissionsDO.getOperation(), permissionsDO.getOperationValue());
                    });
                }
                ccNode.setOperations(operations);
                //处理集合转字符串
                ccNode.setUsers(StrUtil.isNotEmpty(node.getUsers()) ? StrUtil.split(node.getUsers(), ",") : new ArrayList<>());
                ccNode.setRoles(StrUtil.isNotEmpty(node.getRoles()) ? StrUtil.split(node.getRoles(), ",") : new ArrayList<>());
                ccNode.setAssigneeType(AssigneeTypeEnum.getByType(node.getAssigneeType()));
                nodes.add(ccNode);
            }
            if (NodeTypeEnum.APPROVAL.getCode().equals(node.getType())) {
                ApprovalNode approvalNode = BeanUtils.toBean(node, ApprovalNode.class);
                approvalNode.setFormProperties(formMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(formMap.get(node.getId()), FormProperty.class));
                approvalNode.setExecutionListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));
                approvalNode.setTaskListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));
                Map<String, Boolean> operations = new HashMap<>();
                if (CollUtil.isNotEmpty(permissionsMap.get(node.getId()))) {
                    permissionsMap.get(node.getId()).forEach(permissionsDO -> {
                        operations.put(permissionsDO.getOperation(), permissionsDO.getOperationValue());
                    });
                }
                approvalNode.setOperations(operations);
                //处理集合转字符串
                approvalNode.setUsers(StrUtil.isNotEmpty(node.getUsers()) ? StrUtil.split(node.getUsers(), ",") : new ArrayList<>());
                approvalNode.setRoles(StrUtil.isNotEmpty(node.getRoles()) ? StrUtil.split(node.getRoles(), ",") : new ArrayList<>());
                approvalNode.setAssigneeType(AssigneeTypeEnum.getByType(node.getAssigneeType()));
                approvalNode.setNobodyUsers(StrUtil.isNotEmpty(node.getNobodyUsers()) ? StrUtil.split(node.getNobodyUsers(), ",") : new ArrayList<>());
                approvalNode.setNobody(ApprovalNobodyEnum.getByNobody(node.getNobody()));
                approvalNode.setMulti(ApprovalMultiEnum.getByMulti(node.getMulti()));
                nodes.add(approvalNode);
            }
            if (NodeTypeEnum.CONDITION.getCode().equals(node.getType())) {
                ConditionNode conditionNode = BeanUtils.toBean(node, ConditionNode.class);
                conditionNode.setExecutionListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));
                List<ProcessNodeConditionDO> conList = conditionMap.get(node.getId());
                if (CollUtil.isNotEmpty(conList)) {
                    List<ProcessNodeConditionDO> con = conList.stream().filter(item -> item.getType() != null && "0".equals(item.getType())).collect(Collectors.toList());
                    FilterRules conditions = new FilterRules();
                    conditions.setConditions(BeanUtils.toBean(con, Condition.class));
                    if(con.size() == 1){
                        conditions.setConditions(new ArrayList<>());
                    }
                    List<ProcessNodeConditionDO> groupList = conList.stream().filter(item -> item.getType() != null && Objects.isNull(item.getParentGroupId())
                            && "1".equals(item.getType())).collect(Collectors.toList());
                    List<FilterRules> groups = new ArrayList<>();
                    conditions.setGroups(groups);
                    groupList.stream().collect(Collectors.groupingBy(ProcessNodeConditionDO::getGroupId)).forEach((key, value) -> {
                        FilterRules group = new FilterRules();
                        group.setConditions(BeanUtils.toBean(value, Condition.class));
                        if (CollUtil.isNotEmpty(value)){
                            group.setOperator(value.get(0).getOperator());
                            if (value.size() == 1){
                                group.setConditions(new ArrayList<>());
                            }
                        }
                        //TODO 这里需要递归处理子节点
                        groups.add(group);
                    });
                    conditions.setOperator(con.get(0).getOperator());
                    conditionNode.setConditions(conditions);
                }
                nodes.add(conditionNode);
            }
            if (NodeTypeEnum.EXCLUSIVE.getCode().equals(node.getType())) {
                ExclusiveNode exclusiveNode = BeanUtils.toBean(node, ExclusiveNode.class);
                exclusiveNode.setExecutionListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));
                nodes.add(exclusiveNode);
            }
            if (NodeTypeEnum.NOTIFY.getCode().equals(node.getType())) {
                NotifyNode notifyNode = BeanUtils.toBean(node, NotifyNode.class);
                notifyNode.setExecutionListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));
                //处理集合转字符串
                notifyNode.setUsers(StrUtil.isNotEmpty(node.getUsers()) ? StrUtil.split(node.getUsers(), ",") : new ArrayList<>());
                notifyNode.setRoles(StrUtil.isNotEmpty(node.getRoles()) ? StrUtil.split(node.getRoles(), ",") : new ArrayList<>());
                notifyNode.setAssigneeType(AssigneeTypeEnum.getByType(node.getAssigneeType()));
                List<String> typesStr = StrUtil.split(node.getTypes(), ",");
                List<NotifyTypeEnum> types = new ArrayList<>();
                if (CollUtil.isNotEmpty(typesStr)) {
                    typesStr.forEach(type -> {
                        types.add(NotifyTypeEnum.getByType(type));
                    });
                }
                notifyNode.setTypes(types);
                nodes.add(notifyNode);
            }
            if (NodeTypeEnum.TIMER.getCode().equals(node.getType())) {
                TimerNode timerNode = BeanUtils.toBean(node, TimerNode.class);
                timerNode.setExecutionListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));
                timerNode.setWaitType(TimerWaitType.getByType(node.getWaitType()));
                nodes.add(timerNode);
            }
            if (NodeTypeEnum.END.getCode().equals(node.getType())) {
                EndNode endNode = BeanUtils.toBean(node, EndNode.class);
                endNode.setExecutionListeners(eventMap.get(node.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(node.getId()), NodeListener.class));
                nodes.add(endNode);
            }
        }
        for (Node node : nodes) {
            for (Node node1 : nodes) {
                if (node.getId().equals(node1.getPid())) {
                    if (!node1.getType().equals(NodeTypeEnum.CONDITION.getCode())) {
                        node.setChild(node1);
                    } else {
                        List<ConditionNode> conditionNodes = ((ExclusiveNode) node).getChildren();
                        if (CollUtil.isEmpty(conditionNodes)) {
                            ((ExclusiveNode) node).setChildren(Lists.newArrayList((ConditionNode) node1));
                        } else {
                            conditionNodes.add((ConditionNode) node1);
                        }
                    }
                }
            }
            if (NodeTypeEnum.START.getCode().equals(node.getType())) {
                root = node;
            }
        }
        result.setProcess(root);
        return result;
    }

    @Override
    public PageResult<ProcessDesignDO> getProcessDesignPage(ProcessDesignPageReqVO pageReqVO) {
        return processDesignMapper.selectPage(pageReqVO);
    }

    @Override
    public Node getNode(String nodeId, Long pageId, String flowId) {

        // 1、nodeId不为空值时，返回流程信息，以及对应节点信息
        if(StringUtils.isNotBlank(nodeId)){
            // 获取流程数据
            QueryWrapper<ProcessDesignDO> wrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(flowId)){
                wrapper.eq("flow_id", flowId);
            }
            if(pageId!=null){
                wrapper.eq("page_id", pageId);
            }
            List<ProcessDesignDO> processDesignDOS = processDesignMapper.selectList(wrapper);
            if (CollUtil.isEmpty(processDesignDOS)) {
                throw exception(PROCESS_DESIGN_NOT_EXISTS);
            }

            ProcessNodeDO processNodeDO = processNodeMapper.selectOne(new QueryWrapper<ProcessNodeDO>()
                    .eq("out_process_node_id", nodeId)
                    .in("process_id", processDesignDOS.stream().map(ProcessDesignDO::getId).collect(Collectors.toList()))
            );
            if (processNodeDO == null) {
                throw exception(PROCESS_NODE_NOT_EXISTS);
            }
            // 流程名称
            for(ProcessDesignDO design : processDesignDOS){
                if(design.getId()!=null && design.getId().equals(processNodeDO.getProcessId())){
                    processNodeDO.setProcessName(design.getProcessName());
                    processNodeDO.setMenuId(design.getMenuId());
                    processNodeDO.setPageId(design.getPageId());
                    processNodeDO.setProcessType(design.getProcessType());
                    processNodeDO.setRemark(design.getRemark());
                    processNodeDO.setFlowId(design.getFlowId());
                    break;
                }
            }

            QueryWrapper queryWrapper = new QueryWrapper<>().eq("node_id", processNodeDO.getId());
            List<ProcessNodeEventDO> eventList = processNodeEventMapper.selectList(queryWrapper);
            List<ProcessNodeConditionDO> conditionList = processNodeConditionMapper.selectList(queryWrapper);
            List<ProcessNodeFormPropertyDO> formList = processNodeFormPropertyMapper.selectList(queryWrapper);
            List<ProcessNodePermissionsDO> permissionsList = processNodePermissionsMapper.selectList(queryWrapper);
            Map<String, List<ProcessNodeEventDO>> eventMap = eventList.stream().collect(Collectors.groupingBy(ProcessNodeEventDO::getNodeId));
            Map<String, List<ProcessNodeConditionDO>> conditionMap = conditionList.stream().collect(Collectors.groupingBy(ProcessNodeConditionDO::getNodeId));
            Map<String, List<ProcessNodeFormPropertyDO>> formMap = formList.stream().collect(Collectors.groupingBy(ProcessNodeFormPropertyDO::getNodeId));
            Map<String, List<ProcessNodePermissionsDO>> permissionsMap = permissionsList.stream().collect(Collectors.groupingBy(ProcessNodePermissionsDO::getNodeId));

            if (NodeTypeEnum.START.getCode().equals(processNodeDO.getType())) {
                StartNode startNode = BeanUtils.toBean(processNodeDO, StartNode.class);
                startNode.setFormProperties(formMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(formMap.get(processNodeDO.getId()), FormProperty.class));
                startNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
                return startNode;
            }
            if (NodeTypeEnum.CC.getCode().equals(processNodeDO.getType())) {
                CcNode ccNode = BeanUtils.toBean(processNodeDO, CcNode.class);
                ccNode.setFormProperties(formMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(formMap.get(processNodeDO.getId()), FormProperty.class));
                ccNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));

                Map<String, Boolean> operations = new HashMap<>();
                if (CollUtil.isNotEmpty(permissionsMap.get(processNodeDO.getId()))) {
                    permissionsMap.get(processNodeDO.getId()).forEach(permissionsDO -> {
                        operations.put(permissionsDO.getOperation(), permissionsDO.getOperationValue());
                    });
                }
                ccNode.setOperations(operations);
                //处理集合转字符串
                ccNode.setUsers(StrUtil.isNotEmpty(processNodeDO.getUsers()) ? StrUtil.split(processNodeDO.getUsers(), ",") : new ArrayList<>());
                ccNode.setRoles(StrUtil.isNotEmpty(processNodeDO.getRoles()) ? StrUtil.split(processNodeDO.getRoles(), ",") : new ArrayList<>());
                ccNode.setAssigneeType(AssigneeTypeEnum.getByType(processNodeDO.getAssigneeType()));
                return ccNode;
            }
            if (NodeTypeEnum.APPROVAL.getCode().equals(processNodeDO.getType())) {
                ApprovalNode approvalNode = BeanUtils.toBean(processNodeDO, ApprovalNode.class);
                approvalNode.setFormProperties(formMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(formMap.get(processNodeDO.getId()), FormProperty.class));
                approvalNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
                approvalNode.setTaskListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
                Map<String, Boolean> operations = new HashMap<>();
                if (CollUtil.isNotEmpty(permissionsMap.get(processNodeDO.getId()))) {
                    permissionsMap.get(processNodeDO.getId()).forEach(permissionsDO -> {
                        operations.put(permissionsDO.getOperation(), permissionsDO.getOperationValue());
                    });
                }
                approvalNode.setOperations(operations);
                //处理集合转字符串
                approvalNode.setUsers(StrUtil.isNotEmpty(processNodeDO.getUsers()) ? StrUtil.split(processNodeDO.getUsers(), ",") : new ArrayList<>());
                approvalNode.setRoles(StrUtil.isNotEmpty(processNodeDO.getRoles()) ? StrUtil.split(processNodeDO.getRoles(), ",") : new ArrayList<>());
                approvalNode.setAssigneeType(AssigneeTypeEnum.getByType(processNodeDO.getAssigneeType()));
                approvalNode.setNobodyUsers(StrUtil.isNotEmpty(processNodeDO.getNobodyUsers()) ? StrUtil.split(processNodeDO.getNobodyUsers(), ",") : new ArrayList<>());
                approvalNode.setNobody(ApprovalNobodyEnum.getByNobody(processNodeDO.getNobody()));
                approvalNode.setMulti(ApprovalMultiEnum.getByMulti(processNodeDO.getMulti()));
                return approvalNode;
            }
            if (NodeTypeEnum.CONDITION.getCode().equals(processNodeDO.getType())) {
                ConditionNode conditionNode = BeanUtils.toBean(processNodeDO, ConditionNode.class);
                conditionNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
                List<ProcessNodeConditionDO> conList = conditionMap.get(processNodeDO.getId());
                if (CollUtil.isNotEmpty(conList)) {
                    List<ProcessNodeConditionDO> con = conList.stream().filter(item -> item.getType() != null && "0".equals(item.getType())).collect(Collectors.toList());
                    FilterRules conditions = new FilterRules();
                    conditions.setConditions(BeanUtils.toBean(con, Condition.class));
                    if (con.size() == 1) {
                        conditions.setConditions(new ArrayList<>());
                    }
                    List<ProcessNodeConditionDO> groupList = conList.stream().filter(item -> item.getType() != null && Objects.isNull(item.getParentGroupId())
                            && "1".equals(item.getType())).collect(Collectors.toList());
                    List<FilterRules> groups = new ArrayList<>();
                    conditions.setGroups(groups);
                    groupList.stream().collect(Collectors.groupingBy(ProcessNodeConditionDO::getGroupId)).forEach((key, value) -> {
                        FilterRules group = new FilterRules();
                        group.setConditions(BeanUtils.toBean(value, Condition.class));
                        if (CollUtil.isNotEmpty(value)) {
                            group.setOperator(value.get(0).getOperator());
                            if (value.size() == 1) {
                                group.setConditions(new ArrayList<>());
                            }
                        }
                        //TODO 这里需要递归处理子节点
                        groups.add(group);
                    });
                    conditions.setOperator(con.get(0).getOperator());
                    conditionNode.setConditions(conditions);
                }
                return conditionNode;
            }
            if (NodeTypeEnum.EXCLUSIVE.getCode().equals(processNodeDO.getType())) {
                ExclusiveNode exclusiveNode = BeanUtils.toBean(processNodeDO, ExclusiveNode.class);
                exclusiveNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
                return exclusiveNode;
            }
            if (NodeTypeEnum.NOTIFY.getCode().equals(processNodeDO.getType())) {
                NotifyNode notifyNode = BeanUtils.toBean(processNodeDO, NotifyNode.class);
                notifyNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
                //处理集合转字符串
                notifyNode.setUsers(StrUtil.isNotEmpty(processNodeDO.getUsers()) ? StrUtil.split(processNodeDO.getUsers(), ",") : new ArrayList<>());
                notifyNode.setRoles(StrUtil.isNotEmpty(processNodeDO.getRoles()) ? StrUtil.split(processNodeDO.getRoles(), ",") : new ArrayList<>());
                notifyNode.setAssigneeType(AssigneeTypeEnum.getByType(processNodeDO.getAssigneeType()));
                List<String> typesStr = StrUtil.split(processNodeDO.getTypes(), ",");
                List<NotifyTypeEnum> types = new ArrayList<>();
                if (CollUtil.isNotEmpty(typesStr)) {
                    typesStr.forEach(type -> {
                        types.add(NotifyTypeEnum.getByType(type));
                    });
                }
                notifyNode.setTypes(types);
                return notifyNode;
            }
            if (NodeTypeEnum.TIMER.getCode().equals(processNodeDO.getType())) {
                TimerNode timerNode = BeanUtils.toBean(processNodeDO, TimerNode.class);
                timerNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
                timerNode.setWaitType(TimerWaitType.getByType(processNodeDO.getWaitType()));
                return timerNode;
            }
            if (NodeTypeEnum.END.getCode().equals(processNodeDO.getType())) {
                EndNode endNode = BeanUtils.toBean(processNodeDO, EndNode.class);
                endNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
                return endNode;
            } else {
                return null;
            }
        }

        // 2、nodeId为空值时，返回流程信息以及第一个节点信息
        else if(StringUtils.isBlank(nodeId) && (pageId!=null || StringUtils.isNotBlank(flowId))){
            // 获取流程数据
            QueryWrapper<ProcessDesignDO> wrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(flowId)){
                wrapper.eq("flow_id", flowId);
            }
            if(pageId!=null){
                wrapper.eq("page_id", pageId);
            }
            List<ProcessDesignDO> processDesignDOS = processDesignMapper.selectList(wrapper);
            if (CollUtil.isEmpty(processDesignDOS)) {
                throw exception(PROCESS_DESIGN_NOT_EXISTS);
            }

            if (CollUtil.isEmpty(processDesignDOS)) {
                throw exception(PROCESS_DESIGN_NOT_EXISTS);
            }

            List<ProcessNodeDO> processNodeDOList = processNodeMapper.selectList(new QueryWrapper<ProcessNodeDO>()
                    .eq("type", "start")
                    .in("process_id", processDesignDOS.stream().map(ProcessDesignDO::getId).collect(Collectors.toList()))
            );
            if (processNodeDOList == null || processNodeDOList.isEmpty()) {
                throw exception(PROCESS_NODE_NOT_EXISTS);
            }

            ProcessNodeDO processNodeDO = processNodeDOList.get(0);
            // 流程名称
            for(ProcessDesignDO design : processDesignDOS){
                if(design.getId()!=null && design.getId().equals(processNodeDO.getProcessId())){
                    processNodeDO.setProcessName(design.getProcessName());
                    processNodeDO.setMenuId(design.getMenuId());
                    processNodeDO.setPageId(design.getPageId());
                    processNodeDO.setProcessType(design.getProcessType());
                    processNodeDO.setRemark(design.getRemark());
                    processNodeDO.setFlowId(design.getFlowId());
                    break;
                }
            }

            QueryWrapper queryWrapper = new QueryWrapper<>().eq("node_id", processNodeDO.getId());
            List<ProcessNodeEventDO> eventList = processNodeEventMapper.selectList(queryWrapper);
            List<ProcessNodeFormPropertyDO> formList = processNodeFormPropertyMapper.selectList(queryWrapper);
            Map<String, List<ProcessNodeEventDO>> eventMap = eventList.stream().collect(Collectors.groupingBy(ProcessNodeEventDO::getNodeId));
            Map<String, List<ProcessNodeFormPropertyDO>> formMap = formList.stream().collect(Collectors.groupingBy(ProcessNodeFormPropertyDO::getNodeId));

            StartNode startNode = BeanUtils.toBean(processNodeDO, StartNode.class);
            startNode.setFormProperties(formMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(formMap.get(processNodeDO.getId()), FormProperty.class));
            startNode.setExecutionListeners(eventMap.get(processNodeDO.getId()) == null ? new ArrayList<>() : BeanUtils.toBean(eventMap.get(processNodeDO.getId()), NodeListener.class));
            return startNode;
        }
        return null;
    }

    /**
     * 外部流程节点对应的java类
     *
     * @param flowId 外部流程Id
     * @param nodeId 外部流程节点ID
     * @return
     */
    @Override
    public List<String> findProcessMethods(String flowId, String nodeId) {
        return processDesignMapper.findProcessMethods(flowId, nodeId);
    }

    @Override
    public Map<String, List<Map<String, String>>> importExcel(Long id, List<String> listSort, Map<String, List<Map<String, String>>> maps) {
        // 获取节点
        List<ProcessNodeDO> list = processNodeMapper.selectList(ProcessNodeDO::getProcessId, id);
        Map<String, List<ProcessNodeDO>> map = list.stream().collect(Collectors.groupingBy(ProcessNodeDO::getName));
        AtomicInteger index = new AtomicInteger(1);
        listSort.forEach(s -> {
            int num = index.getAndIncrement();
            int maxIndex = num * 3;
            if (CollUtil.isNotEmpty(map.get(s))) {
                map.get(s).forEach(p -> {
                    List<ProcessNodeFormPropertyDO> processNodeFormPropertyDOS = processNodeFormPropertyMapper.selectList(ProcessNodeFormPropertyDO::getNodeId, p.getId());
                    processNodeFormPropertyDOS.forEach(i -> {
                        if (CollUtil.isNotEmpty(maps.get(i.getName()))) {
                            i.setHidden(maps.get(i.getName()).get(maxIndex - 3).values().toString().equals("是"));
                            i.setReadonly(maps.get(i.getName()).get(maxIndex - 2).values().toString().equals("是"));
                            i.setRequired(maps.get(i.getName()).get(maxIndex - 1).values().toString().equals("是"));
                        }
                    });
                    processNodeFormPropertyMapper.insertOrUpdate(processNodeFormPropertyDOS);
                });
            }
        });
        return null;
    }

    public void saveApprovalDate(ModuleCache api,LinkedHashMap<String,Object> params,String flowId,String nodeId){
        List<ModuleSqlCache> list = api.getSqlList();
        Map<String, Object> parameterMap = new HashMap<>();

        Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        List<ModuleRelationFieldCache> relationFields = api.getRelationList();
        List<Map<String, Map<String, Object>>> childList = new ArrayList<>();
        Map<String, Map<String, Object>> rootMap = new HashMap<>();
        List<Long> child = Lists.newArrayList();
        this.handleData(params, childList, rootMap, relationFields, child, OperateTypeEnum.UPDATE);
        Map<String, Object> mainMap = rootMap.get(String.valueOf(api.getModuleTableId()));

        if (CollUtil.isNotEmpty(list)) {
            rootMap.forEach((key, value) -> {
                if(!ATTACHMENT_LIST.equals(key)){
                    List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(sqlList)) {
                        this.setSaveParams(value, api);
                        if (CollUtil.isNotEmpty(value)) {
                            String str = value.keySet().iterator().next();
                            int lastIndex = str.lastIndexOf('_');
                            String substring = str.substring(lastIndex + 1);
                            Object o = value.get(String.format(ID_FORMAT_ONE, substring));
                            for (ModuleSqlCache m : sqlList) {
                                if(o != null){
                                    if(StringUtils.isNotEmpty(m.getActionSql()) && m.getActionSql().contains("APPROVAL_DATE")){
                                        parameterMap.put("APPROVAL_DATE",new Date());
                                        parameterMap.put("ID",String.valueOf(o));
                                        String tableName = m.getTableName();
                                        String sql = "update " + tableName + " set APPROVAL_DATE = #{APPROVAL_DATE} where ID = #{ID}";
                                        executorUtils.update(sql, parameterMap);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public void saveLatestApprovePassDate(ModuleCache api,LinkedHashMap<String,Object> params,String flowId,String nodeId){
        List<ModuleSqlCache> list = api.getSqlList();
        Map<String, Object> parameterMap = new HashMap<>();

        Map<Long, List<ModuleSqlCache>> map = list.stream().collect(Collectors.groupingBy(ModuleSqlCache::getModuleTableId));
        List<ModuleRelationFieldCache> relationFields = api.getRelationList();
        List<Map<String, Map<String, Object>>> childList = new ArrayList<>();
        Map<String, Map<String, Object>> rootMap = new HashMap<>();
        List<Long> child = Lists.newArrayList();
        this.handleData(params, childList, rootMap, relationFields, child, OperateTypeEnum.UPDATE);

        if (CollUtil.isNotEmpty(list)) {
            rootMap.forEach((key, value) -> {
                if(!ATTACHMENT_LIST.equals(key)){
                    List<ModuleSqlCache> sqlList = map.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(sqlList)) {
                        this.setSaveParams(value, api);
                        if (CollUtil.isNotEmpty(value)) {
                            String str = value.keySet().iterator().next();
                            int lastIndex = str.lastIndexOf('_');
                            String substring = str.substring(lastIndex + 1);
                            Object o = value.get(String.format(ID_FORMAT_ONE, substring));
                            for (ModuleSqlCache m : sqlList) {
                                if(o != null){
                                    if(StringUtils.isNotEmpty(m.getActionSql()) && m.getActionSql().contains("LATEST_APPROVE_PASS_DATE")){
                                        parameterMap.put("LATEST_APPROVE_PASS_DATE",new Date());
                                        parameterMap.put("ID",String.valueOf(o));
                                        String tableName = m.getTableName();
                                        String sql = "update " + tableName + " set LATEST_APPROVE_PASS_DATE = #{LATEST_APPROVE_PASS_DATE} where ID = #{ID}";
                                        executorUtils.update(sql, parameterMap);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 给初始字段赋值
     *
     * @param body
     * @param childList
     * @param rootMap
     * @param relationFields
     */
    private void handleData(Map<String, Object> body, List<Map<String, Map<String, Object>>> childList, Map<String, Map<String, Object>> rootMap,
            List<ModuleRelationFieldCache> relationFields, List<Long> child, OperateTypeEnum operateType) {
        body.forEach((key, value) -> {
            if (key.startsWith(CHILD_FLAG)) {
                String childKey = key.replace(CHILD_FLAG, "");
                child.add(Long.valueOf(childKey));
                List vlist = (List) value;
                if (CollUtil.isNotEmpty(vlist)) {//重新组织
                    vlist.forEach(v -> {
                        Map<String, Map<String, Object>> vmap = new HashMap<>();
                        Map<String, Object> v1 = this.setChildMap(BeanUtil.beanToMap(v));
                        v1.forEach((k, v2) -> {
                            Map<String, Object> endMap = BeanUtil.beanToMap(v2);
                            endMap.put(String.format(ID_FORMAT_ONE, k), IdWorker.getId());
                            // TODO: 暂时先不考虑兼容性
                            //                            endMap.put(String.format(ID_FORMAT_TWO, k), IdWorker.getId());
                            vmap.put(k, endMap);
                        });
                        childList.add(vmap);
                    });
                }
            } else {
                Map<String, Object> v = BeanUtil.beanToMap(value);
                if (operateType == OperateTypeEnum.CREATE) {
                    v.put(String.format(ID_FORMAT_ONE, key), IdWorker.getId());
                    // TODO: 暂时先不考虑兼容性
                    //                    v.put(String.format(ID_FORMAT_TWO, key), IdWorker.getId());
                }
                rootMap.put(key, v);
            }
        });
        Map<String, Map<String, Object>> allMap = new HashMap<>(rootMap);
        if (rootMap.size() > 1) {
            this.setRelationFieldValue(rootMap, allMap, relationFields);
        }
        if (CollUtil.isNotEmpty(child)) {
            childList.forEach(childMap -> {
                Map<String, Map<String, Object>> temp = new HashMap<>(rootMap);
                temp.putAll(childMap);
                this.setRelationFieldValue(childMap, temp, relationFields);
            });
        }
    }

    /**
     * 设置childMap
     *
     * @param v1
     * @return
     */
    private Map<String, Object> setChildMap(Map<String, Object> v1) {
        Set<String> keys = new HashSet<>();
        v1.keySet().forEach(k -> {
            String key = k.substring(k.lastIndexOf('_') + 1);
            if (StrUtil.isNumeric(key)) {
                keys.add(key);
            }
        });
        return this.setTableKey(keys, v1);
    }

    /**
     * 封装返回的报文
     *
     * @param tableSet
     * @param obj
     * @return
     */
    private Map<String, Object> setTableKey(Set<String> tableSet, Map<String, Object> obj) {
        Map<String, Object> result = new HashMap<>();
        for (String t : tableSet) {
            obj.forEach((k, v) -> {
                Map<String, Object> tempMap = new HashMap<>();
                if (result.get(t) != null) {
                    tempMap = (Map<String, Object>) result.get(t);
                } else {
                    result.put(t, tempMap);
                }
                if (k.endsWith(t)) {
                    if (k.startsWith(PK_PREFIX_ONE) || k.startsWith(PK_PREFIX_TWO)) {
                        tempMap.put(k, v.toString());
                    } else {
                        tempMap.put(k, (v != null && StringUtil.isNotBlank(v.toString())) ? v.toString() : v);
                    }
                }
            });
        }
        return result;
    }

    /**
     * 设置关联字段
     *
     * @param map
     * @param allMap
     */
    private void setRelationFieldValue(Map<String, Map<String, Object>> map, Map<String, Map<String, Object>> allMap, List<ModuleRelationFieldCache> rList) {
        if (CollUtil.isEmpty(rList)) {//没有关联关系说明是单表操作
            return;
        }
        Map<String, Object> tmp = new HashMap<>();
        if (CollUtil.isNotEmpty(rList)) {
            rList.forEach(r -> {
                Map<String, Object> rmap = allMap.get(String.valueOf(r.getRelationMoudleTableId()));
                Map<String, Object> mmap = allMap.get(String.valueOf(r.getModuleTableId()));
                if (CollUtil.isNotEmpty(mmap) ) {
                    String key = String.format(FIELD_FORMAT, r.getFieldName(), r.getModuleTableId());
                    Object val = mmap.get(key);
                    if (val != null) {
                        tmp.put(String.format(FIELD_FORMAT, r.getRelationFieldName(), r.getRelationMoudleTableId()), val);
                    } else if (tmp.get(key) != null) {
                        tmp.put(String.format(FIELD_FORMAT, r.getRelationFieldName(), r.getRelationMoudleTableId()), tmp.get(key));
                    }
                }
                if (CollUtil.isNotEmpty(rmap)) {
                    String key = String.format(FIELD_FORMAT, r.getRelationFieldName(), r.getRelationMoudleTableId());
                    Object val = rmap.get(key);
                    if (val != null) {
                        tmp.put(String.format(FIELD_FORMAT, r.getFieldName(), r.getModuleTableId()), val);
                    } else if (tmp.get(key) != null) {
                        tmp.put(String.format(FIELD_FORMAT, r.getFieldName(), r.getModuleTableId()), tmp.get(key));
                    }
                }
            });
            //处理没有覆盖到的关联字段
            rList.forEach(r -> {
                String key = String.format(FIELD_FORMAT, r.getFieldName(), r.getModuleTableId());
                String key1 = String.format(FIELD_FORMAT, r.getRelationFieldName(), r.getRelationMoudleTableId());
                if (tmp.get(key) == null) {
                    tmp.put(key, tmp.get(key1));
                }
                if (tmp.get(key1) == null) {
                    tmp.put(key1, tmp.get(key));
                }
            });
        }
        Map<Long, List<ModuleRelationFieldCache>> moduleMap = rList.stream().collect(Collectors.groupingBy(ModuleRelationFieldCache::getModuleTableId));
        Map<Long, List<ModuleRelationFieldCache>> relationModuleMap = rList.stream().collect(Collectors.groupingBy(ModuleRelationFieldCache::getModuleTableId));
        map.forEach((key, value) -> {
            if (CollUtil.isNotEmpty(rList)) {
                if (canConvertToLongUsingRegex(key)) {
                    List<ModuleRelationFieldCache> mList = moduleMap.get(Long.valueOf(key));
                    if (CollUtil.isNotEmpty(mList)) {
                        mList.forEach(m -> {
                            value.put(String.format(FIELD_FORMAT, m.getFieldName(), m.getModuleTableId()), tmp.get(String.format(FIELD_FORMAT, m.getRelationFieldName(), m.getRelationMoudleTableId())));
                        });
                    } else {
                        List<ModuleRelationFieldCache> m1List = relationModuleMap.get(Long.valueOf(key));
                        if (CollUtil.isNotEmpty(m1List)) {
                            m1List.forEach(m -> {
                                value.put(String.format(FIELD_FORMAT, m.getRelationFieldName(), m.getRelationMoudleTableId()), tmp.get(String.format(FIELD_FORMAT, m.getFieldName(), m.getModuleTableId())));
                            });
                        }
                    }
                }
            }
        });
    }

    public boolean canConvertToLongUsingRegex(String str) {
        String regex = "^[+-]?\\d+$";
        return str != null && str.matches(regex) && str.length() <= 19;
    }

    /**
     * 设置新建修改参数
     *
     * @param reqVo
     * @param api
     */
    private void setSaveParams(Map<String, Object> reqVo, ModuleCache api) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        api.getSystemFieldList().forEach(field -> {
            if (CfgSystemFieldEnum.USER_ID.getCode().equals(field.getDefaultValue())) {
                reqVo.put(field.getColumnName(), loginUserId);
            }
            if (CfgSystemFieldEnum.DATE.getCode().equals(field.getDefaultValue())) {
                reqVo.put(field.getColumnName(), new Date());
            }
            if (CfgSystemFieldEnum.DELETE_FLAG.getCode().equals(field.getCategory())) {
                reqVo.put(api.getDeleteField(), api.getNotDeleteVaule());
            }
        });
    }

    public String checkStartNo(ModuleCache api,LinkedHashMap<String,Object> params,String flowId,String nodeId){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(params != null){
            params.forEach((key, value) -> {
                if (key.startsWith(CHILD_FLAG)) {
                    List vlist = (List) value;
                    if (CollUtil.isNotEmpty(vlist)) {//重新组织
                        vlist.forEach(v -> {
                            Map<String, Object> v1 = BeanUtil.beanToMap(v);
                            v1.forEach((k, v2) -> {
                                if(k.startsWith(START_NO)){
                                    Map map = new HashMap();
                                    map.put("startNo",v2);
                                    jsonArray.add(map);
                                }
                            });
                        });
                    }
                    jsonObject.put("jsonArray",jsonArray);
                }else{
                    Map<String, Object> v = BeanUtil.beanToMap(value);
                    v.forEach((k, v2) -> {
                        if(k.startsWith(PRODUCT_BRAND)){
                            jsonObject.put("productBrand",v2);
                        }
                    });
                }
            });

            //调用业务接口获取结果
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(isExistsProducts)
                    .post(okhttp3.RequestBody.create(String.valueOf(jsonObject), MediaType.parse("application/json; charset=utf-8")))
                    .build();

            try {
                // 执行请求
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    // 处理响应
                    String responseData = response.body().string();
                    System.out.println(responseData);
                    return responseData;
                } else {
                    System.out.println("请求失败，响应码: " + response.code());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
