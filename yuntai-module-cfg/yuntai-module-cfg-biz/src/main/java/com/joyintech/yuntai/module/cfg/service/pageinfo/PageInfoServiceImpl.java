package com.joyintech.yuntai.module.cfg.service.pageinfo;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.joyintech.yuntai.framework.mybatis.core.dataobject.BaseDO;
import com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo.SubTableSettingSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.componentattribute.vo.ComponentAttributeSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.SubTableSetting.SubTableSettingDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componentattribute.ComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.modulefield.ModuleFieldDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.SubTableSetting.SubTableSettingMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.componentattribute.ComponentAttributeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.modulefield.ModuleFieldMapper;
import com.joyintech.yuntai.module.cfg.service.SubTableSetting.SubTableSettingService;
import com.joyintech.yuntai.module.cfg.utils.FuncUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.common.util.tree.TreeNode;
import com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo.ButtonActionSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dataconversion.vo.DataConversionRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dataformat.vo.DataFormatRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.eventconfig.vo.EventConfigRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.moduleinfo.vo.ModuleTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageapi.vo.PageApiSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.PageAttachmentInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.PageAttachmentUploadfileSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo.PageButtonSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageextendevent.vo.PageExtendEventRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagegroup.vo.PageGroupSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo.PageInfoPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo.PageInfoRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageinfo.vo.PageInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelinkage.vo.PageLinkageRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistcondition.vo.PageListConditionSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageparameter.vo.PageParameterSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo.ParamterListSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo.TemplateInfoRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.validaterules.vo.ValidateRulesRespVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.buttonaction.ButtonActionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.componenttable.ComponentTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataconversion.DataConversionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dataformat.DataFormatDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.eventconfig.EventConfigDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.functioninfo.FunctionInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.moduleinfo.ModuleInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentinfo.PageAttachmentInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentuploadfile.PageAttachmentUploadfileDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagebutton.PageButtonDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageextendevent.PageExtendEventDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelinkage.PageLinkageDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistcondition.PageListConditionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagelistconfig.PageListConfigDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageparameter.PageParameterDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.paramterlist.ParamterListDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.validaterules.ValidateRulesDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.buttonaction.ButtonActionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.columncomponentattribute.ColumnComponentAttributeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.conditionaltable.ConditionalTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.dataconversion.DataConversionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.dataformat.DataFormatMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.eventconfig.EventConfigMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.functioninfo.FunctionInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduleinfo.ModuleInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.moduletable.ModuleTableMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageapi.PageApiMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageattachmentinfo.PageAttachmentInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageattachmentuploadfile.PageAttachmentUploadfileMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagebutton.PageButtonMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageextendevent.PageExtendEventMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagegroup.PageGroupMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageinfo.PageInfoMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagelinkage.PageLinkageMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagelistcondition.PageListConditionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pagelistconfig.PageListConfigMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageparameter.PageParameterMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.paramterlist.ParamterListMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.validaterules.ValidateRulesMapper;
import com.joyintech.yuntai.module.cfg.service.componenttable.ComponentTableService;
import com.joyintech.yuntai.module.cfg.service.menu.MenuApiServiceImpl;
import com.joyintech.yuntai.module.cfg.service.pageapi.PageApiServiceImpl;
import com.joyintech.yuntai.module.cfg.service.pagebutton.PageButtonServiceImpl;
import com.joyintech.yuntai.module.cfg.service.pagegroup.PageGroupServiceImpl;
import com.joyintech.yuntai.module.cfg.service.pagelistcondition.PageListConditionServiceImpl;
import com.joyintech.yuntai.module.cfg.service.pagelistconfig.PageListConfigServiceImpl;
import com.joyintech.yuntai.module.cfg.service.pageparameter.PageParameterServiceImpl;
import com.joyintech.yuntai.module.cfg.service.paramterlist.ParamterListServiceImpl;
import com.joyintech.yuntai.module.cfg.service.templateinfo.TemplateInfoService;
import com.joyintech.yuntai.module.system.api.dict.DictTypeApi;
import com.joyintech.yuntai.module.system.api.dict.dto.DictTypeRespDTO;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;

/**
 * 页面基本信息 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageInfoServiceImpl implements PageInfoService {

    private static final Logger log = LoggerFactory.getLogger(PageInfoServiceImpl.class);
    public static final String PAGE_ID = "page_id";
    public static final String PAGE_CONFIG_ID = "page_config_id";
    public static final String LIST_CONFIG_ID = "list_config_id";
    public static final String RELEVANCE_ID = "relevance_id";
    public static final String MOBILE_FORM = "mobile-form";
    public static final String MOBILE_LIST = "mobile-list";
    @Resource
    private PageInfoMapper pageInfoMapper;
    @Resource
    private FunctionInfoMapper functionInfoMapper;

    @Resource
    private PageGroupMapper pageGroupMapper;

    @Resource
    private PageGroupServiceImpl pageGroupService;

    @Resource
    private PageListConditionMapper pageListConditionMapper;

    @Resource
    private PageButtonMapper pageButtonMapper;

    @Resource
    private PageListConfigMapper pageListConfigMapper;

    @Resource
    private ModuleTableMapper moduleTableMapper;

    @Resource
    private ModuleInfoMapper moduleInfoMapper;

    @Resource
    private ValidateRulesMapper validateRulesMapper;

    @Resource
    private PageLinkageMapper pageLinkageMapper;

    @Resource
    private EventConfigMapper eventConfigMapper;

    @Resource
    private PageExtendEventMapper pageExtendEventMapper;

    @Resource
    private DataConversionMapper dataConversionMapper;

    @Resource
    private DataFormatMapper dataFormatMapper;

    @Resource
    private ConditionalTableMapper conditionalTableMapper;

    @Resource
    private ColumnComponentAttributeMapper columnComponentAttributeMapper;

    @Resource
    private PageApiMapper pageApiMapper;

    @Resource
    private PageApiServiceImpl pageApiService;

    @Resource
    private PageParameterServiceImpl pageParameterService;

    @Resource
    private PageListConfigServiceImpl pageListConfigService;

    @Resource
    private PageListConditionServiceImpl pageListConditionService;

    @Resource
    private PageButtonServiceImpl pageButtonService;

    @Resource
    private PageParameterMapper pageParameterMapper;

    @Resource
    private MenuApiServiceImpl menuApi;

    @Resource
    private DictTypeApi dictTypeApi;

    @Resource
    private ParamterListServiceImpl paramterListService;

    @Resource
    private ParamterListMapper paramterListMapper;

    @Resource
    private ComponentTableService componentTableService;

    @Resource
    private ButtonActionMapper buttonActionMapper;

    @Resource
    private TemplateInfoService templateInfoService;

    @Resource
    private PageAttachmentInfoMapper pageAttachmentInfoMapper;

    @Resource
    private PageAttachmentUploadfileMapper pageAttachmentUploadfileMapper;

    @Resource
    private ModuleFieldMapper moduleFieldMapper;

    @Resource
    private SubTableSettingService subTableSettingService;

    @Resource
    private SubTableSettingMapper subTableSettingMapper;

    @Resource
    private ComponentAttributeMapper componentAttributeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPageApiInfo(PageInfoSaveReqVO createReqVO) {
        // 插入
        PageInfoDO pageInfo = BeanUtils.toBean(createReqVO, PageInfoDO.class);
        pageInfoMapper.insert(pageInfo);
        //创建菜单时同时创建菜单
        menuApi.createPage(pageInfo);
        // 新增页面API
        if (createReqVO.getPageApiRespVOS()!=null && !createReqVO.getPageApiRespVOS().isEmpty()) {
            createReqVO.getPageApiRespVOS().forEach(group -> {
                group.setPageId(pageInfo.getId());
            });
            this.addApiList(createReqVO.getPageApiRespVOS());
        }

        // 新增页面参数
        if (createReqVO.getTemplateParamList()!=null && !createReqVO.getTemplateParamList().isEmpty()
                && (!MOBILE_LIST.equals(createReqVO.getPageType()) && !MOBILE_FORM.equals(createReqVO.getPageType()))) {
            createReqVO.getTemplateParamList().forEach(param -> {
                param.setPageId(pageInfo.getId());
            });
            pageParameterService.addPageParameter(createReqVO.getTemplateParamList());
        }

        // 新增操作按钮
        if (createReqVO.getPageButtons()!=null && !createReqVO.getPageButtons().isEmpty()) {
            createReqVO.getPageButtons().forEach(s -> {
                    s.setPageId(pageInfo.getId());
                    if (s.getServerParams()!=null && !s.getServerParams().isEmpty()) {
                        s.setServerParamsCode(String.join(",", s.getServerParams()));
                    }
            });
            pageButtonService.addPageButton(createReqVO.getPageButtons());
            //增加按钮时同时创建菜单按钮
            if(!MOBILE_LIST.equals(createReqVO.getPageType()) && !MOBILE_FORM.equals(createReqVO.getPageType())){
                menuApi.createButton(createReqVO.getPageButtons(), pageInfo.getMenuId());
            }
        }

        // 新增扩展事件
        if (createReqVO.getEventList()!=null && !createReqVO.getEventList().isEmpty()) {
            List<PageExtendEventDO> eeList = BeanUtils.toBean(createReqVO.getEventList(), PageExtendEventDO.class);
            eeList.forEach(e -> e.setPageId(pageInfo.getId()));
            pageExtendEventMapper.insertBatch(eeList);
        }

        // 新增路由参数
        if (createReqVO.getParamterList()!=null && !createReqVO.getParamterList().isEmpty()) {
            createReqVO.getParamterList().forEach(param -> param.setPageId(pageInfo.getId()));
            paramterListService.addParamterList(createReqVO.getParamterList());
        }

        // 附件管理
        if("1".equals(pageInfo.getIsSupportAttachment()) && createReqVO.getAttachmentInfo()!=null){
            PageAttachmentInfoSaveReqVO pageAttachmentInfoSaveReqVO =
                    BeanUtils.toBean(createReqVO.getAttachmentInfo(), PageAttachmentInfoSaveReqVO.class);
            pageAttachmentInfoSaveReqVO.setPageId(pageInfo.getId());
            if (pageAttachmentInfoSaveReqVO.getAllowFileSuffix()!=null && !pageAttachmentInfoSaveReqVO.getAllowFileSuffix().isEmpty()) {
                pageAttachmentInfoSaveReqVO.setAllowFileSuffixCode(String.join(",", pageAttachmentInfoSaveReqVO.getAllowFileSuffix()));
            }
            if (pageAttachmentInfoSaveReqVO.getAllowSize()!=null && !pageAttachmentInfoSaveReqVO.getAllowSize().isEmpty()) {
                pageAttachmentInfoSaveReqVO.setAllowSizeCode(String.join(",", pageAttachmentInfoSaveReqVO.getAllowSize()));
            }
            PageAttachmentInfoDO pageAttachmentInfoDO = BeanUtils.toBean(pageAttachmentInfoSaveReqVO, PageAttachmentInfoDO.class);
            pageAttachmentInfoMapper.insert(pageAttachmentInfoDO);

            // 附件管理-指定上传文件
            List<PageAttachmentUploadfileDO> uploadFileList = BeanUtils.toBean(pageAttachmentInfoSaveReqVO.getUploadFileList(), PageAttachmentUploadfileDO.class);
            if(uploadFileList!=null && !uploadFileList.isEmpty()){
                uploadFileList.forEach(key-> key.setAttachmentId(pageAttachmentInfoDO.getId()));
                pageAttachmentUploadfileMapper.insertOrUpdateBatch(uploadFileList);

                pageAttachmentUploadfileMapper.delete(new QueryWrapper<PageAttachmentUploadfileDO>()
                        .eq("attachment_id", pageAttachmentInfoDO.getId())
                        .notIn("id", uploadFileList.stream().map(PageAttachmentUploadfileDO::getId).collect(Collectors.toList())));
            }else{
                pageAttachmentUploadfileMapper.delete(PageAttachmentUploadfileDO::getAttachmentId, pageAttachmentInfoDO.getId());
            }
        }

        return pageInfo.getId();
    }

    public void addApiList(List<PageApiSaveReqVO> pageApiRespVOS) {
        // 新增页面API
        List<PageApiDO> bean = BeanUtils.toBean(pageApiRespVOS, PageApiDO.class);
        pageApiMapper.insertOrUpdateBatch(bean);
        Map<Long, PageApiDO> map = bean.stream().collect(Collectors.toMap(PageApiDO::getApiId, Function.identity()));
        List<PageGroupSaveReqVO> pageGroupSaveReqVOS = new ArrayList<>();
        List<SubTableSettingSaveReqVO> subTableSettingSaveReqVOS = new ArrayList<>();
        List<PageListConfigSaveReqVO> pageListConfigSaveReqVOS = new ArrayList<>();
        List<PageListConditionSaveReqVO> pageListConditionSaveReqVOS = new ArrayList<>();

        // 获取全部组件
        List<ComponentTableDO> componentTableAllList = componentTableService.getComponentTableAllList();
        Map<String, ComponentTableDO> componentTableDOMap = componentTableAllList.stream().collect(Collectors.toMap(ComponentTableDO::getComponentCode, Function.identity()));


        pageApiRespVOS.forEach(s -> {
            // 处理分组
            if (s.getPageGroups()!=null && !s.getPageGroups().isEmpty()) {
                s.getPageGroups().forEach(p -> {
                    p.setPageId(map.get(s.getApiId()).getPageId());
                    p.setPageApiId(map.get(s.getApiId()).getId());
                    pageGroupSaveReqVOS.add(p);
                });
            }
            // 处理子表
            if (s.getTableLayoutConfig()!=null && !s.getTableLayoutConfig().isEmpty()) {
                s.getTableLayoutConfig().forEach(p -> {
                    p.setPageId(String.valueOf(map.get(s.getApiId()).getPageId()));
                    p.setPageApiId(String.valueOf(map.get(s.getApiId()).getId()));
                    subTableSettingSaveReqVOS.add(p);
                });
            }

            // 处理模型列表字段
            if (s.getPageListConfigs()!=null && !s.getPageListConfigs().isEmpty()) {
                s.getPageListConfigs().forEach(p -> {
                   p.setPageId(map.get(s.getApiId()).getPageId());
                   p.setPageApiId(map.get(s.getApiId()).getId());
                   p.setApiCode(map.get(s.getApiId()).getApiCode());
                   if (p.getColumnTag()!=null && !p.getColumnTag().isEmpty()) {
                       p.setColumnTagCode(String.join(",", p.getColumnTag()));
                   }
                   if (p.getColumnDisplayComponent() != null) {
                       p.setColumnDisplayComponentName(componentTableDOMap.get(p.getColumnDisplayComponent()).getComponentName());
                   } else {
                       p.setColumnDisplayComponentName(null);
                   }
                   pageListConfigSaveReqVOS.add(p);
                });
            }

            // 处理查询条件
            if (s.getPageListConditions()!=null && !s.getPageListConditions().isEmpty()) {
                s.getPageListConditions().forEach(p -> {
                    p.setPageId(map.get(s.getApiId()).getPageId());
                    p.setPageApiId(map.get(s.getApiId()).getId());
                    if (p.getColumnDisplayComponent() != null) {
                        p.setColumnDisplayComponentName(componentTableDOMap.get(p.getColumnDisplayComponent()).getComponentName());
                    } else {
                        p.setColumnDisplayComponentName(null);
                    }
                    if (Objects.isNull(p.getTableId())) {
                        p.setTableId(p.getModuleTableId());
                    }
                    pageListConditionSaveReqVOS.add(p);
                });
            }
        });

        // 删除没有的Api
        if (!bean.isEmpty()) {
            List<Long> list = bean.stream().map(PageApiDO::getId).collect(Collectors.toList());
            pageApiMapper.delete(new QueryWrapper<PageApiDO>()
                    .eq(PAGE_ID, bean.get(0).getPageId())
                    .notIn("id", list)
            );
        }
        if (!pageGroupSaveReqVOS.isEmpty()) {
            pageGroupService.addGroupList(pageGroupSaveReqVOS, componentTableDOMap);
        }

        if (!subTableSettingSaveReqVOS.isEmpty()) {
            subTableSettingService.addSubTableSettingList(subTableSettingSaveReqVOS);
        }

        if (!pageListConfigSaveReqVOS.isEmpty()) {
            pageListConfigService.addPageListConfig(pageListConfigSaveReqVOS);
        }

        if (!pageListConditionSaveReqVOS.isEmpty()) {
            pageListConditionService.addPageListCondition(pageListConditionSaveReqVOS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePageInfo(PageInfoSaveReqVO updateReqVO) {
        // 校验存在
        validatePageInfoExists(updateReqVO.getId(),updateReqVO);

        // 更新
        PageInfoDO updateObj = BeanUtils.toBean(updateReqVO, PageInfoDO.class);
        pageInfoMapper.updateById(updateObj);

        // 更新分组及列表配置
        if (updateReqVO.getPageApiRespVOS()!=null && !updateReqVO.getPageApiRespVOS().isEmpty()) {
            updateReqVO.getPageApiRespVOS().forEach(group -> {
                group.setPageId(updateReqVO.getId());
            });
            this.addApiList(updateReqVO.getPageApiRespVOS());
        }

        // 新增页面参数
        if (updateReqVO.getTemplateParamList()!=null && !updateReqVO.getTemplateParamList().isEmpty()) {
            updateReqVO.getTemplateParamList().forEach(param -> {
                param.setPageId(updateReqVO.getId());
            });
            pageParameterService.addPageParameter(updateReqVO.getTemplateParamList());
        }

        // 更新操作按钮
        if (updateReqVO.getPageButtons()!=null && !updateReqVO.getPageButtons().isEmpty()) {
            updateReqVO.getPageButtons().forEach(button -> {
                button.setPageId(updateReqVO.getId());
                if (button.getServerParams()!=null && !button.getServerParams().isEmpty()) {
                    button.setServerParamsCode(String.join(",", button.getServerParams()));
                }
            });
            pageButtonService.addPageButton(updateReqVO.getPageButtons());
            //增加按钮时同时创建菜单按钮
            if(!MOBILE_LIST.equals(updateReqVO.getPageType()) && !MOBILE_FORM.equals(updateReqVO.getPageType())){
                menuApi.createButton(updateReqVO.getPageButtons(), updateReqVO.getMenuId());
            }
        } else {
            pageButtonMapper.delete(new QueryWrapper<PageButtonDO>()
                    .eq(PAGE_ID, updateReqVO.getId())
            );
        }
        // 插入页面扩展事件
        if (updateReqVO.getEventList()!=null && !updateReqVO.getEventList().isEmpty()) {
            //在插入
            List<PageExtendEventDO> eeList = BeanUtils.toBean(updateReqVO.getEventList(), PageExtendEventDO.class);
            eeList.forEach(e -> e.setPageId(updateObj.getId()));
            pageExtendEventMapper.insertOrUpdateBatch(eeList);

            //先删除掉
            pageExtendEventMapper.delete(new QueryWrapper<PageExtendEventDO>()
                    .notIn("id", eeList.stream().map(PageExtendEventDO::getId).collect(Collectors.toList()))
                    .in("PAGE_ID",eeList.stream().map(PageExtendEventDO::getPageId).collect(Collectors.toList()))
            );
        } else {
            pageExtendEventMapper.delete(PageExtendEventDO::getPageId, updateObj.getId());
        }

        // 路由参数
        if (updateReqVO.getParamterList()!=null && !updateReqVO.getParamterList().isEmpty()
                && (!MOBILE_LIST.equals(updateReqVO.getPageType()) && !MOBILE_FORM.equals(updateReqVO.getPageType()))) {
            updateReqVO.getParamterList().forEach(s -> s.setPageId(updateReqVO.getId()));
            paramterListService.addParamterList(updateReqVO.getParamterList());
        } else {
            paramterListMapper.delete(new QueryWrapper<ParamterListDO>()
                    .eq(PAGE_ID, updateObj.getId())
            );
        }

        // 附件管理
        if("1".equals(updateObj.getIsSupportAttachment()) && updateReqVO.getAttachmentInfo()!=null){
            PageAttachmentInfoSaveReqVO pageAttachmentInfoSaveReqVO =
                    BeanUtils.toBean(updateReqVO.getAttachmentInfo(), PageAttachmentInfoSaveReqVO.class);

            if (pageAttachmentInfoSaveReqVO.getAllowFileSuffix()!=null && !pageAttachmentInfoSaveReqVO.getAllowFileSuffix().isEmpty()) {
                pageAttachmentInfoSaveReqVO.setAllowFileSuffixCode(String.join(",", pageAttachmentInfoSaveReqVO.getAllowFileSuffix()));
            }
            if (pageAttachmentInfoSaveReqVO.getAllowSize()!=null && !pageAttachmentInfoSaveReqVO.getAllowSize().isEmpty()) {
                pageAttachmentInfoSaveReqVO.setAllowSizeCode(String.join(",", pageAttachmentInfoSaveReqVO.getAllowSize()));
            }

            if (pageAttachmentInfoSaveReqVO.getFilterFileType()!=null && !pageAttachmentInfoSaveReqVO.getFilterFileType().isEmpty()) {
                pageAttachmentInfoSaveReqVO.setFilterFileTypeValue(String.join(",", pageAttachmentInfoSaveReqVO.getFilterFileType()));
            }

            PageAttachmentInfoDO pageAttachmentInfoDO = BeanUtils.toBean(pageAttachmentInfoSaveReqVO, PageAttachmentInfoDO.class);
            if(updateReqVO.getAttachmentInfo().getId()==null){
                pageAttachmentInfoDO.setPageId(updateReqVO.getId());
                pageAttachmentInfoMapper.insert(pageAttachmentInfoDO);
            }else{
                pageAttachmentInfoMapper.updateById(pageAttachmentInfoDO);
            }

            // 附件管理-指定上传文件
            List<PageAttachmentUploadfileDO> uploadFileList = BeanUtils.toBean(pageAttachmentInfoSaveReqVO.getUploadFileList(), PageAttachmentUploadfileDO.class);
            if(uploadFileList!=null && !uploadFileList.isEmpty()){
                uploadFileList.forEach(key-> key.setAttachmentId(pageAttachmentInfoDO.getId()));
                pageAttachmentUploadfileMapper.insertOrUpdateBatch(uploadFileList);

                pageAttachmentUploadfileMapper.delete(new QueryWrapper<PageAttachmentUploadfileDO>()
                                .eq("attachment_id", pageAttachmentInfoDO.getId())
                        .notIn("id", uploadFileList.stream().map(PageAttachmentUploadfileDO::getId).collect(Collectors.toList())));

                //保存联动配置
                List<PageLinkageRespVO> pageLinkageRespVOSList =new ArrayList<>();
                Set<Long> set = uploadFileList.stream().map(PageAttachmentUploadfileDO::getId).collect(Collectors.toSet());
                uploadFileList.forEach(uploadFile ->{
                    // 新增联动配置
                    if (CollectionUtil.isNotEmpty(uploadFile.getPageLinkageRespVOS())) {
                        uploadFile.getPageLinkageRespVOS().forEach(v -> {
                            v.setPageConfigId(uploadFile.getId());
                            pageLinkageRespVOSList.add(v);
                        });
                    }
                });

                if (CollUtil.isNotEmpty(pageLinkageRespVOSList)) {
                    List<PageLinkageDO> bean1 = BeanUtils.toBean(pageLinkageRespVOSList, PageLinkageDO.class);
                    pageLinkageMapper.insertOrUpdateBatch(bean1);

                    // 删除没有的联动配置
                    List<Long> longs1 = bean1.stream().map(PageLinkageDO::getId).collect(Collectors.toList());
                    pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>()
                            .in("page_config_id", set)
                            .notIn("id", longs1)
                    );

                    // 新增条件内容
                    Map<String, PageLinkageDO> map = bean1.stream().collect(Collectors.toMap(s-> s.getPageConfigId() + s.getLinkageName(),Function.identity()));
                    List<ConditionalTableDO> conditionalTableDOList =new ArrayList<>();
                    pageLinkageRespVOSList.forEach(v -> {
                        if(v.getSceneList()!=null && !v.getSceneList().isEmpty()){
                            for(ConditionalTableSaveReqVO scene : v.getSceneList()){
                                ConditionalTableDO bean2 = BeanUtils.toBean(scene, ConditionalTableDO.class);
                                bean2.setRelevanceId(map.get(v.getPageConfigId() + v.getLinkageName()).getId());
                                bean2.setContent(scene.getCondition() != null ? JSON.toJSONString(scene.getCondition()) : null);
                                conditionalTableDOList.add(bean2);
                            }
                        }
                    });
                    if (!conditionalTableDOList.isEmpty()) {
                        conditionalTableMapper.insertOrUpdateBatch(conditionalTableDOList);

                        // 删除没有的条件
                        conditionalTableMapper.delete(new QueryWrapper<ConditionalTableDO>()
                                .eq("relevance_id", conditionalTableDOList.get(0).getRelevanceId())
                                .notIn("id", conditionalTableDOList.stream().map(ConditionalTableDO::getId).collect(Collectors.toList()))
                        );
                    }
                }else {
                    if(CollUtil.isNotEmpty(set)){
                        pageLinkageMapper.delete(new QueryWrapper<PageLinkageDO>().in("page_config_id", set));
                    }
                }
            }else{
                pageAttachmentUploadfileMapper.delete(PageAttachmentUploadfileDO::getAttachmentId, pageAttachmentInfoDO.getId());
            }
        }else{
            PageAttachmentInfoDO pageAttachmentInfoDO = pageAttachmentInfoMapper.selectOne(PageAttachmentInfoDO::getPageId, updateObj.getId());
            if(pageAttachmentInfoDO!=null){
                pageAttachmentUploadfileMapper.delete(PageAttachmentUploadfileDO::getAttachmentId,pageAttachmentInfoDO.getId());
                pageAttachmentInfoMapper.delete(PageAttachmentInfoDO::getPageId,updateObj.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePageInfo(Long id) {
        // 校验存在
        PageInfoDO pageInfoDO = validatePageInfoExists(id,null);
        // 删除
        pageInfoMapper.deleteById(id);

        // 删除API信息
        pageApiMapper.delete(PageApiDO::getPageId,id);

        // 删除参数信息
        pageParameterMapper.delete(PageParameterDO::getPageId, id);

        // 删除分组及列表配置
        pageGroupMapper.delete(new QueryWrapper<PageGroupDO>()
                .eq(PAGE_ID, id)
        );
        pageListConfigMapper.delete(new QueryWrapper<PageListConfigDO>()
                .eq(PAGE_ID, id)
        );

        // 删除查询条件
        pageListConditionMapper.delete(new QueryWrapper<PageListConditionDO>()
                .eq(PAGE_ID, id)
        );


        // 删除子表信息
        subTableSettingMapper.delete(new QueryWrapper<SubTableSettingDO>()
                .eq(PAGE_ID, id)
        );

        // 删除操作按钮
        pageButtonMapper.delete(new QueryWrapper<PageButtonDO>()
                .eq(PAGE_ID, id)
        );
        // 删除扩展事件
        pageExtendEventMapper.delete(PageExtendEventDO::getPageId,id);
        // 删除菜单按钮
        menuApi.deletePage(pageInfoDO);

        // 附件管理
        PageAttachmentInfoDO pageAttachmentInfoDO = pageAttachmentInfoMapper.selectOne(PageAttachmentInfoDO::getPageId, id);
        if(pageAttachmentInfoDO!=null){
            pageAttachmentUploadfileMapper.delete(PageAttachmentUploadfileDO::getAttachmentId,pageAttachmentInfoDO.getId());
            pageAttachmentInfoMapper.delete(PageAttachmentInfoDO::getPageId,id);
        }
    }

    private PageInfoDO validatePageInfoExists(Long id,PageInfoSaveReqVO updateReqVO) {
        PageInfoDO pageInfoDO = pageInfoMapper.selectById(id);
        if (pageInfoDO == null) {
            throw exception(PAGE_INFO_NOT_EXISTS);
        }
        if (Objects.nonNull(updateReqVO)) {
            if (!FuncUtil.compareDate(pageInfoDO.getUpdateTime(),updateReqVO.getUpdateTimeStamp())) {
                throw exception(PAGE_INFO_CHANGE);
            }
        }
        return pageInfoDO;
    }

    @Override
    public PageInfoRespVO getPageInfo(Long id) {
        if(id==null){
            throw exception(PAGE_INFO_ID_NOT_EXISTS);
        }
        // 查询基本信息
        PageInfoDO pageInfoDO = pageInfoMapper.selectById(id);
        if (null == pageInfoDO) {
            throw exception(PAGE_INFO_NOT_EXISTS);
        }
        PageInfoRespVO pageInfoRespVO = BeanUtils.toBean(pageInfoDO, PageInfoRespVO.class);
        //查询选择的模板信息
        if (StringUtils.isNotBlank(pageInfoDO.getPageTemplate())) {
            TemplateInfoRespVO templateInfo = templateInfoService.getTemplateInfo(Long.valueOf(pageInfoDO.getPageTemplate()));
            pageInfoRespVO.setTemplateInfo(templateInfo);
        }
        // 查询API信息
        List<PageApiDO> pageApiDOS = pageApiMapper.selectList(PageApiDO::getPageId, id);
        pageInfoRespVO.setPageApiRespVOS(BeanUtils.toBean(pageApiDOS, PageApiSaveReqVO.class));

        // 查询api下全部信息
        if (pageInfoRespVO.getPageApiRespVOS()!=null && !pageInfoRespVO.getPageApiRespVOS().isEmpty()) {
            this.getPageApi(pageInfoRespVO.getPageApiRespVOS(), pageInfoDO.getId());
        }

        // 查询参数信息
        List<PageParameterDO> pageParameterDOS = pageParameterMapper.selectList(PageParameterDO::getPageId, id);
        List<PageParameterSaveReqVO> pageParameterSaveReqVOS = BeanUtils.toBean(pageParameterDOS, PageParameterSaveReqVO.class);
        pageInfoRespVO.setTemplateParamList(pageParameterSaveReqVOS);

        // 查询操作按钮
        List<PageButtonDO> pageButtonDOS = pageButtonMapper.selectList(new QueryWrapper<PageButtonDO>()
                .eq(PAGE_ID, id)
        );
        if (!pageButtonDOS.isEmpty()) {
            List<PageButtonSaveReqVO> bean = BeanUtils.toBean(pageButtonDOS, PageButtonSaveReqVO.class);
            List<Long> longStream = bean.stream().map(PageButtonSaveReqVO::getId).collect(Collectors.toList());
            // 查询按钮条件
            List<ConditionalTableDO> relevanceId = conditionalTableMapper.selectList(new QueryWrapper<ConditionalTableDO>()
                    .in(RELEVANCE_ID, longStream)
            );
            Map<Long, ConditionalTableDO> map = relevanceId.stream().collect(Collectors.toMap(ConditionalTableDO::getRelevanceId, p -> p));

            // 查询按钮动作
            List<ButtonActionDO> buttonActionDOS = buttonActionMapper.selectList(ButtonActionDO::getPageButtonId, longStream);
            Map<Long, ButtonActionDO> mapButton = buttonActionDOS.stream().collect(Collectors.toMap(ButtonActionDO::getPageButtonId, p -> p));

            // 查询字段下的组件属性
            List<ColumnComponentAttributeDO> componentAttributeList = columnComponentAttributeMapper.selectColumnComponentAttributeList(longStream);
            Map<Long, List<ColumnComponentAttributeDO>> mapCa = componentAttributeList.stream().collect(Collectors.groupingBy(ColumnComponentAttributeDO::getColumnId));
            // 获取全部组件
            List<ComponentTableDO> componentTableAllList = componentTableService.getComponentTableAllList();
            Map<String, ComponentTableDO> componentTableDOMap = componentTableAllList.stream().collect(Collectors.toMap(ComponentTableDO::getComponentCode, Function.identity()));


            bean.forEach(s -> {
                if (map.get(s.getId()) != null) {
                    ConditionalTableRespVO conditionalTableRespVO = BeanUtil.copyProperties(map.get(s.getId()), ConditionalTableRespVO.class);
                    if (conditionalTableRespVO.getContent() != null) {
                        conditionalTableRespVO.setCondition(JSON.parse(conditionalTableRespVO.getContent()));
                    }
                    // 显示类型
                    if (StringUtils.isNotBlank(conditionalTableRespVO.getShowPageTypeCode())) {
                        conditionalTableRespVO.setShowPageType(Arrays.asList(conditionalTableRespVO.getShowPageTypeCode().split(",")));
                    }

                    s.setConditionalTableRespVO(conditionalTableRespVO);
                } else {
                    s.setConditionalTableRespVO(null);
                }
                if (s.getServerParamsCode() != null) {
                    s.setServerParams(Arrays.asList(s.getServerParamsCode().split(",")));
                }
                if (mapButton.get(s.getId()) != null) {
                    ButtonActionSaveReqVO bean1 = BeanUtils.toBean(mapButton.get(s.getId()), ButtonActionSaveReqVO.class);
                    if (mapButton.get(s.getId()).getServerParams() != null) {
                        bean1.setServerParams(Arrays.asList(mapButton.get(s.getId()).getServerParams().split(",")));
                    }
                    s.setShowButtonActionCfg(bean1);
                }
                //设置按钮组件属性
                s.setColumnComponentAttributeDO(mapCa.get(s.getId()));
                if(StringUtils.isNotEmpty(s.getColumnDisplayComponent())) {
                    s.setColumnDisplayComponentName(componentTableDOMap.get(s.getColumnDisplayComponent()).getComponentName());
                }
            });
            pageInfoRespVO.setPageButtons(bean);
        }

        // 查询页面扩展事件配置
        List<PageExtendEventDO> eeList = pageExtendEventMapper.selectList(PageExtendEventDO::getPageId,id);
        pageInfoRespVO.setEventList(BeanUtils.toBean(eeList, PageExtendEventRespVO.class));

        // 路由参数
        List<ParamterListDO> paramterListDOS = paramterListMapper.selectList(ParamterListDO::getPageId, id);
        pageInfoRespVO.setParamterList(BeanUtils.toBean(paramterListDOS, ParamterListSaveReqVO.class));

        // 附件管理
        PageAttachmentInfoDO pageAttachmentInfoDO = pageAttachmentInfoMapper.selectOne(PageAttachmentInfoDO::getPageId, id);
        if(pageAttachmentInfoDO!=null){
            PageAttachmentInfoSaveReqVO pageAttachmentInfoSaveReqVO = BeanUtils.toBean(pageAttachmentInfoDO, PageAttachmentInfoSaveReqVO.class);

            if (pageAttachmentInfoSaveReqVO.getAllowFileSuffixCode() != null) {
                pageAttachmentInfoSaveReqVO.setAllowFileSuffix(Arrays.asList(pageAttachmentInfoSaveReqVO.getAllowFileSuffixCode().split(",")));
            }
            if (pageAttachmentInfoSaveReqVO.getAllowSizeCode() != null) {
                pageAttachmentInfoSaveReqVO.setAllowSize(Arrays.asList(pageAttachmentInfoSaveReqVO.getAllowSizeCode().split(",")));
            }

            if (pageAttachmentInfoSaveReqVO.getFilterFileTypeValue() != null) {
                pageAttachmentInfoSaveReqVO.setFilterFileType(Arrays.asList(pageAttachmentInfoSaveReqVO.getFilterFileTypeValue().split(",")));
            }

            // 指定上传文件
            if(pageAttachmentInfoSaveReqVO.getId()!=null){
                List<PageAttachmentUploadfileDO> pageAttachmentUploadfileDOS =
                        pageAttachmentUploadfileMapper.selectList(PageAttachmentUploadfileDO::getAttachmentId, pageAttachmentInfoDO.getId());
                pageAttachmentUploadfileDOS.forEach(uploadfile ->{
                    // 查询字段下的联动配置
                    List<PageLinkageDO> tempPageConfigId1 = pageLinkageMapper.selectList(new QueryWrapper<PageLinkageDO>()
                            .in(PAGE_CONFIG_ID, uploadfile.getId())
                    );
                    if(tempPageConfigId1!=null && !tempPageConfigId1.isEmpty()){
                        uploadfile.setPageLinkageRespVOS(BeanUtils.toBean(tempPageConfigId1, PageLinkageRespVO.class));
                        List<Long> list = tempPageConfigId1.stream().map(PageLinkageDO::getId).collect(Collectors.toList());
                        List<ConditionalTableDO> conditionalTableDOList = conditionalTableMapper.selectList(ConditionalTableDO::getRelevanceId, list);

                        Map<Long, List<ConditionalTableDO>> map = conditionalTableDOList.stream().collect(Collectors.groupingBy(ConditionalTableDO::getRelevanceId));
                        Map<Long, List<PageLinkageDO>> mapLink = tempPageConfigId1.stream().collect(Collectors.groupingBy(PageLinkageDO::getPageConfigId));
                        if (mapLink.get(uploadfile.getId()) != null) {
                            List<PageLinkageRespVO> bean1 = BeanUtils.toBean(mapLink.get(uploadfile.getId()), PageLinkageRespVO.class);
                            bean1.forEach(s -> {
                                if (map.get(s.getId())!=null && !map.get(s.getId()).isEmpty()) {
                                    List<ConditionalTableSaveReqVO> sceneList = new ArrayList<>();
                                    map.get(s.getId()).forEach(v -> {
                                        ConditionalTableSaveReqVO bean2 = BeanUtils.toBean(v, ConditionalTableSaveReqVO.class);
                                        if (bean2.getContent() != null) {
                                            bean2.setCondition(JSON.parse(bean2.getContent()));
                                        }
                                        sceneList.add(bean2);
                                    });
                                    s.setSceneList(sceneList);
                                }
                            });
                            uploadfile.setPageLinkageRespVOS(bean1);
                        } else {
                            uploadfile.setPageLinkageRespVOS(null);
                        }
                    }
                });
                pageAttachmentInfoSaveReqVO.setUploadFileList(BeanUtils.toBean(pageAttachmentUploadfileDOS, PageAttachmentUploadfileSaveReqVO.class));
            }
            pageInfoRespVO.setAttachmentInfo(pageAttachmentInfoSaveReqVO);
        }

        return pageInfoRespVO;
    }

    private void getPageApi(List<PageApiSaveReqVO> pageApiRespVOS, Long pageId) {
        // 查询分组信息
        List<PageGroupDO> groupDOS = pageGroupMapper.selectList(new QueryWrapper<PageGroupDO>()
                .eq(PAGE_ID, pageId)
        );
        List<PageGroupSaveReqVO> pageGroupSaveReqVOS = BeanUtils.toBean(groupDOS, PageGroupSaveReqVO.class);

        // 表单页配置>>表单项配置，分组里面的显隐规则，也改成和联动配置那边一样的方式了，一变多
        pageGroupSaveReqVOS.forEach(pageGroups -> {
            List<ConditionalTableDO> tableList = conditionalTableMapper.selectList(ConditionalTableDO::getRelevanceId, pageGroups.getId());
            if(tableList!=null && !tableList.isEmpty()){
                List<ConditionalTableSaveReqVO> showConfig = BeanUtils.toBean(tableList, ConditionalTableSaveReqVO.class);
                showConfig.forEach(c -> {
                    c.setCondition(JSON.parse(c.getContent()));
                });
                pageGroups.setShowConfig(showConfig);
            }
        });

        Map<Long, List<PageGroupSaveReqVO>> mapGroup = pageGroupSaveReqVOS.stream().collect(Collectors.groupingBy(PageGroupSaveReqVO::getPageApiId));

        // 查询子表信息
        List<SubTableSettingDO> subTableSettingDOS = subTableSettingMapper.selectList(new QueryWrapper<SubTableSettingDO>()
                .eq(PAGE_ID, pageId)
        );
        List<SubTableSettingSaveReqVO> subTableSettingSaveReqVOS = BeanUtils.toBean(subTableSettingDOS, SubTableSettingSaveReqVO.class);

        subTableSettingSaveReqVOS.forEach(subTableSetting ->{
            List<ConditionalTableDO> tableList = conditionalTableMapper.selectList(ConditionalTableDO::getRelevanceId, subTableSetting.getId());
            if(tableList!=null && !tableList.isEmpty()){
                List<ConditionalTableSaveReqVO> showConfig = BeanUtils.toBean(tableList, ConditionalTableSaveReqVO.class);
                showConfig.forEach(c -> {
                    c.setCondition(JSON.parse(c.getContent()));
                });
                subTableSetting.setShowConfig(showConfig);
            }
        });

        Map<String, List<SubTableSettingSaveReqVO>> mapSubTable = subTableSettingSaveReqVOS.stream().collect(Collectors.groupingBy(SubTableSettingSaveReqVO::getPageApiId));


        // 收集列表页配置信息
        List<PageListConfigDO> pageListConfigDOS = pageListConfigMapper.selectList(PageListConfigDO::getPageId, pageId);
        Set<Long> fields = pageListConfigDOS.stream().map(PageListConfigDO::getFieldId).collect(Collectors.toSet());
        Set<Long> moduleTables = pageListConfigDOS.stream().map(PageListConfigDO::getModuleTableId).collect(Collectors.toSet());
        List<ModuleFieldDO> moduleFieldDOS = moduleFieldMapper.selectList(new QueryWrapper<ModuleFieldDO>()
                .in("column_id", fields)
                .in("module_table_id", moduleTables)
        );
        Map<String, ModuleFieldDO> moduleFieldDOMap = moduleFieldDOS.stream().collect(Collectors.toMap(
                moduleFieldDO ->
                        moduleFieldDO.getModuleTableId() + ":" + moduleFieldDO.getColumnId(),
                        Function.identity()
        ));

        // 获取全部组件
        List<ComponentTableDO> componentTableAllList = componentTableService.getComponentTableAllList();
        Map<String, ComponentTableDO> componentTableDOMap = componentTableAllList.stream().collect(Collectors.toMap(ComponentTableDO::getComponentCode, Function.identity()));


        List<PageListConfigSaveReqVO> bean = BeanUtils.toBean(pageListConfigDOS, PageListConfigSaveReqVO.class);
        if (!bean.isEmpty()) {
            List<Long> listConfigIds = bean.stream().map(PageListConfigSaveReqVO::getId).collect(Collectors.toList());
            List<String> dict = bean.stream().map(PageListConfigSaveReqVO::getColumnDictType).filter(Objects::nonNull).collect(Collectors.toList());
            List<DictTypeRespDTO> dictTypeList = dictTypeApi.getDictTypeList(dict);
            Map<String, DictTypeRespDTO> dictTypeRespDTOMap = dictTypeList.stream().collect(Collectors.toMap(DictTypeRespDTO::getType, Function.identity()));
            // 查询字段下的校验规则
            List<ValidateRulesDO> pageConfigId = new ArrayList<>();
            // 查询字段下的联动配置
            List<PageLinkageDO> pageConfigId1 = new ArrayList<>();
            // 查询字段下的事件配置
            List<EventConfigDO> pageConfigId2 = new ArrayList<>();
            // 查询字段下的数据转换规则
            List<DataConversionDO> pageConfigId3 = new ArrayList<>();
            // 查询字段下的数据格式化
            List<DataFormatDO> pageConfigId4 = new ArrayList<>();
            // 查询字段下的组件属性
            List<ColumnComponentAttributeDO> pageConfigId5 = new ArrayList<>();

            if(listConfigIds.size()>=1000){
                List<Long> tempSet = new ArrayList<>();
                for(Long id : listConfigIds){
                    tempSet.add(id);
                    if(tempSet.size()>=999){
                        // 查询字段下的校验规则
                        List<ValidateRulesDO> tempPageConfigId = validateRulesMapper.selectList(new QueryWrapper<ValidateRulesDO>()
                                .in(PAGE_CONFIG_ID, tempSet)
                        );
                        if(tempPageConfigId!=null && !tempPageConfigId.isEmpty()){
                            pageConfigId.addAll(tempPageConfigId);
                        }
                        // 查询字段下的联动配置
                        List<PageLinkageDO> tempPageConfigId1 = pageLinkageMapper.selectList(new QueryWrapper<PageLinkageDO>()
                                .in(PAGE_CONFIG_ID, tempSet)
                        );
                        if(tempPageConfigId1!=null && !tempPageConfigId1.isEmpty()){
                            pageConfigId1.addAll(tempPageConfigId1);
                        }
                        // 查询字段下的事件配置
                        List<EventConfigDO> tempPageConfigId2 = eventConfigMapper.selectList(new QueryWrapper<EventConfigDO>()
                                .in(PAGE_CONFIG_ID, tempSet)
                        );
                        if(tempPageConfigId2!=null && !tempPageConfigId2.isEmpty()){
                            pageConfigId2.addAll(tempPageConfigId2);
                        }
                        // 查询字段下的数据转换规则
                        List<DataConversionDO> tempPageConfigId3 = dataConversionMapper.selectList(new QueryWrapper<DataConversionDO>()
                                .in(LIST_CONFIG_ID, tempSet)
                        );
                        if(tempPageConfigId3!=null && !tempPageConfigId3.isEmpty()){
                            pageConfigId3.addAll(tempPageConfigId3);
                        }
                        // 查询字段下的数据格式化
                        List<DataFormatDO> tempPageConfigId4 = dataFormatMapper.selectList(new QueryWrapper<DataFormatDO>()
                                .in(LIST_CONFIG_ID, tempSet)
                        );
                        if(tempPageConfigId4!=null && !tempPageConfigId4.isEmpty()){
                            pageConfigId4.addAll(tempPageConfigId4);
                        }
                        // 查询字段下的组件属性
                        List<ColumnComponentAttributeDO> tempPageConfigId5 = columnComponentAttributeMapper.selectList(new QueryWrapper<ColumnComponentAttributeDO>()
                                .in("column_id", tempSet)
                        );
                        if(tempPageConfigId5!=null && !tempPageConfigId5.isEmpty()){
                            pageConfigId5.addAll(tempPageConfigId5);
                        }

                        tempSet.clear();
                    }
                }
                if(!tempSet.isEmpty()){
                    // 查询字段下的校验规则
                    List<ValidateRulesDO> tempPageConfigId = validateRulesMapper.selectList(new QueryWrapper<ValidateRulesDO>()
                            .in(PAGE_CONFIG_ID, tempSet)
                    );
                    if(tempPageConfigId!=null && !tempPageConfigId.isEmpty()){
                        pageConfigId.addAll(tempPageConfigId);
                    }
                    // 查询字段下的联动配置
                    List<PageLinkageDO> tempPageConfigId1 = pageLinkageMapper.selectList(new QueryWrapper<PageLinkageDO>()
                            .in(PAGE_CONFIG_ID, tempSet)
                    );
                    if(tempPageConfigId1!=null && !tempPageConfigId1.isEmpty()){
                        pageConfigId1.addAll(tempPageConfigId1);
                    }
                    // 查询字段下的事件配置
                    List<EventConfigDO> tempPageConfigId2 = eventConfigMapper.selectList(new QueryWrapper<EventConfigDO>()
                            .in(PAGE_CONFIG_ID, tempSet)
                    );
                    if(tempPageConfigId2!=null && !tempPageConfigId2.isEmpty()){
                        pageConfigId2.addAll(tempPageConfigId2);
                    }
                    // 查询字段下的数据转换规则
                    List<DataConversionDO> tempPageConfigId3 = dataConversionMapper.selectList(new QueryWrapper<DataConversionDO>()
                            .in(LIST_CONFIG_ID, tempSet)
                    );
                    if(tempPageConfigId3!=null && !tempPageConfigId3.isEmpty()){
                        pageConfigId3.addAll(tempPageConfigId3);
                    }
                    // 查询字段下的数据格式化
                    List<DataFormatDO> tempPageConfigId4 = dataFormatMapper.selectList(new QueryWrapper<DataFormatDO>()
                            .in(LIST_CONFIG_ID, tempSet)
                    );
                    if(tempPageConfigId4!=null && !tempPageConfigId4.isEmpty()){
                        pageConfigId4.addAll(tempPageConfigId4);
                    }
                    // 查询字段下的组件属性
                    List<ColumnComponentAttributeDO> tempPageConfigId5 = columnComponentAttributeMapper.selectList(new QueryWrapper<ColumnComponentAttributeDO>()
                            .in("column_id", tempSet)
                    );
                    if(tempPageConfigId5!=null && !tempPageConfigId5.isEmpty()){
                        pageConfigId5.addAll(tempPageConfigId5);
                    }

                    tempSet.clear();
                }
            }else{
                // 查询字段下的校验规则
                List<ValidateRulesDO> tempPageConfigId = validateRulesMapper.selectList(new QueryWrapper<ValidateRulesDO>()
                        .in(PAGE_CONFIG_ID, listConfigIds)
                );
                if(tempPageConfigId!=null && !tempPageConfigId.isEmpty()){
                    pageConfigId.addAll(tempPageConfigId);
                }

                // 查询字段下的联动配置
                List<PageLinkageDO> tempPageConfigId1 = pageLinkageMapper.selectList(new QueryWrapper<PageLinkageDO>()
                        .in(PAGE_CONFIG_ID, listConfigIds)
                );
                if(tempPageConfigId1!=null && !tempPageConfigId1.isEmpty()){
                    pageConfigId1.addAll(tempPageConfigId1);
                }

                // 查询字段下的事件配置
                List<EventConfigDO> tempPageConfigId2 = eventConfigMapper.selectList(new QueryWrapper<EventConfigDO>()
                        .in(PAGE_CONFIG_ID, listConfigIds)
                );
                if(tempPageConfigId2!=null && !tempPageConfigId2.isEmpty()){
                    pageConfigId2.addAll(tempPageConfigId2);
                }

                // 查询字段下的数据转换规则
                List<DataConversionDO> tempPageConfigId3 = dataConversionMapper.selectList(new QueryWrapper<DataConversionDO>()
                        .in(LIST_CONFIG_ID, listConfigIds)
                );
                if(tempPageConfigId3!=null && !tempPageConfigId3.isEmpty()){
                    pageConfigId3.addAll(tempPageConfigId3);
                }

                // 查询字段下的数据格式化
                List<DataFormatDO> tempPageConfigId4 = dataFormatMapper.selectList(new QueryWrapper<DataFormatDO>()
                        .in(LIST_CONFIG_ID, listConfigIds)
                );
                if(tempPageConfigId4!=null && !tempPageConfigId4.isEmpty()){
                    pageConfigId4.addAll(tempPageConfigId4);
                }

                // 查询字段下的组件属性
                List<ColumnComponentAttributeDO> tempPageConfigId5 = columnComponentAttributeMapper.selectList(new QueryWrapper<ColumnComponentAttributeDO>()
                        .in("column_id", listConfigIds)
                );
                if(tempPageConfigId5!=null && !tempPageConfigId5.isEmpty()){
                    pageConfigId5.addAll(tempPageConfigId5);
                }
            }

            Map<Long, List<ValidateRulesDO>> mapValidate = pageConfigId.stream().collect(Collectors.groupingBy(ValidateRulesDO::getPageConfigId));
            Map<Long, List<PageLinkageDO>> mapLink = pageConfigId1.stream().collect(Collectors.groupingBy(PageLinkageDO::getPageConfigId));
            List<Long> list = pageConfigId1.stream().map(PageLinkageDO::getId).collect(Collectors.toList());
            // 查询条件配置
            List<ConditionalTableDO> conditionalTableDOList = conditionalTableMapper.selectList(ConditionalTableDO::getRelevanceId, list);
            Map<Long, List<ConditionalTableDO>> map = conditionalTableDOList.stream().collect(Collectors.groupingBy(ConditionalTableDO::getRelevanceId));
            Map<Long, List<EventConfigDO>> mapEvent = pageConfigId2.stream().collect(Collectors.groupingBy(EventConfigDO::getPageConfigId));
            Map<Long,DataConversionDO> mapConversion = pageConfigId3.stream().collect(Collectors.toMap(DataConversionDO::getListConfigId, Function.identity()));
            Map<Long, DataFormatDO> mapFormat = pageConfigId4.stream().collect(Collectors.toMap(DataFormatDO::getListConfigId, Function.identity(), (existingValue, newValue) -> existingValue));
            List<Long> attributeIdList = pageConfigId5.stream().map(ColumnComponentAttributeDO::getAttributeId).collect(Collectors.toList());
            if(attributeIdList!=null && !attributeIdList.isEmpty()){
                List<ComponentAttributeDO> attributeList = componentAttributeMapper.selectList(new QueryWrapper<ComponentAttributeDO>()
                        .in("id", attributeIdList));
                if(attributeList!=null && !attributeList.isEmpty()){
                    pageConfigId5.forEach(data -> {
                        for(ComponentAttributeDO dto : attributeList){
                            if(data.getAttributeId()!=null && data.getAttributeId().equals(dto.getId())){
                                data.setAttributeCode(dto.getAttributeCode());
                                break;
                            }
                        }
                    });
                }
            }

            Map<Long, List<ColumnComponentAttributeDO>> mapColumen = pageConfigId5.stream().collect(Collectors.groupingBy(ColumnComponentAttributeDO::getColumnId));

            bean.forEach(config -> {
                if (config.getColumnTagCode() != null) {
                    config.setColumnTag(Arrays.asList(config.getColumnTagCode().split(",")));
                }

                // 添加字段别名
                ModuleFieldDO moduleFieldDO =
                        moduleFieldDOMap.get(config.getModuleTableId() + ":" + config.getFieldId());
                // NPE异常
                if (moduleFieldDO != null) {
                    if (StringUtils.isNotBlank(moduleFieldDO.getColumnAliasName())) {
                        config.setSysAliasName(moduleFieldDO.getColumnAliasName());
                    } else {
                        config.setSysAliasName(moduleFieldDO.getSysAliasName());
                    }
                }

                if (mapValidate.get(config.getId()) != null) {
                    List<ValidateRulesRespVO> bean1 = BeanUtils.toBean(mapValidate.get(config.getId()), ValidateRulesRespVO.class);
                    bean1.forEach(s -> {
                        if (s.getRulesName() != null) {
                            s.setCondition(JSON.parse(s.getRulesName()));
                        }
                    });
                    config.setValidateRulesRespVOS(bean1);
                } else {
                    config.setValidateRulesRespVOS(null);
                }
                if (mapLink.get(config.getId()) != null) {
                    List<PageLinkageRespVO> bean1 = BeanUtils.toBean(mapLink.get(config.getId()), PageLinkageRespVO.class);
                    bean1.forEach(s -> {
                        if (map.get(s.getId())!=null && !map.get(s.getId()).isEmpty()) {
                            List<ConditionalTableSaveReqVO> sceneList = new ArrayList<>();
                            map.get(s.getId()).forEach(v -> {
                                ConditionalTableSaveReqVO bean2 = BeanUtils.toBean(v, ConditionalTableSaveReqVO.class);
                                if (bean2.getColumnDisplayComponent() != null && componentTableDOMap!=null && componentTableDOMap.containsKey(bean2.getColumnDisplayComponent())) {
                                    bean2.setColumnDisplayComponentName(componentTableDOMap.get(bean2.getColumnDisplayComponent()).getComponentName());
                                }

                                // 这里是【表单页>>表单项配置>>联动配置>>切换输入类型>>切换为】的属性配置，不是字段的显示组件的组件属性（挂在cfg_page_list_config表下，用ColumnComponentAttributeDO.columnId关联的）
                                // ColumnComponentAttributeDO的LinkageId，存cfg_conditional_table表的id，只挂在cfg_conditional_table表下面
                                List<ColumnComponentAttributeDO> columnList = this.findColumnComponentAttribute(bean2.getId());
                                if(columnList!=null && !columnList.isEmpty()){
                                    bean2.setColumnComponentAttributeDO(columnList);
                                }else{
                                    bean2.setColumnComponentAttributeDO(null);
                                }

                                if (bean2.getContent() != null) {
                                    bean2.setCondition(JSON.parse(bean2.getContent()));
                                }
                                sceneList.add(bean2);
                            });
                            s.setSceneList(sceneList);
                        }
                    });
                    config.setPageLinkageRespVOS(bean1);
                } else {
                    config.setPageLinkageRespVOS(null);
                }
                config.setEventConfigRespVOS(mapEvent.get(config.getId()) != null ? BeanUtils.toBean(mapEvent.get(config.getId()), EventConfigRespVO.class) : null);
                config.setDataConversionRespVOS(mapConversion.get(config.getId()) != null ? BeanUtils.toBean(mapConversion.get(config.getId()), DataConversionRespVO.class) : null);
                config.setDataFormatRespVOS(mapFormat.get(config.getId()) != null ? BeanUtils.toBean(mapFormat.get(config.getId()), DataFormatRespVO.class) : null);
                config.setColumnComponentAttributeDO(mapColumen.get(config.getId()) != null ? BeanUtils.toBean(mapColumen.get(config.getId()), ColumnComponentAttributeDO.class) : null);
                if (StringUtils.isNotBlank(config.getColumnDictType())) {
                    config.setColumnDictTypeName(null != dictTypeRespDTOMap.get(config.getColumnDictType()) ? dictTypeRespDTOMap.get(config.getColumnDictType()).getName() : null);
                }
            });
        }
        Map<Long, List<PageListConfigSaveReqVO>> mapConfig = bean.stream().collect(Collectors.groupingBy(PageListConfigSaveReqVO::getPageApiId));

        // 查询查询条件
        List<PageListConditionDO> pageListConditionDOS = pageListConditionMapper.selectByPageId(pageId);
        List<PageListConditionSaveReqVO> beanCondition = BeanUtils.toBean(pageListConditionDOS, PageListConditionSaveReqVO.class);
        if (!beanCondition.isEmpty()) {
            List<Long> listConfigIds = beanCondition.stream().map(PageListConditionSaveReqVO::getId).collect(Collectors.toList());
            List<String> dict = beanCondition.stream().map(PageListConditionSaveReqVO::getColumnDictType).filter(Objects::nonNull).collect(Collectors.toList());
            List<DictTypeRespDTO> dictTypeList = dictTypeApi.getDictTypeList(dict);
            Map<String, DictTypeRespDTO> dictTypeRespDTOMap = dictTypeList.stream().collect(Collectors.toMap(DictTypeRespDTO::getType, Function.identity()));

            // 查询字段下的联动配置
            List<PageLinkageDO> pageConfigId1 = new ArrayList<>();
            // 查询字段下的组件属性
            List<ColumnComponentAttributeDO> pageConfigId5 = new ArrayList<>();

            if(listConfigIds.size()>=1000){
                List<Long> tempSet = new ArrayList<>();
                for(Long id : listConfigIds){
                    tempSet.add(id);
                    if(tempSet.size()>=999){
                        // 查询字段下的联动配置
                        List<PageLinkageDO> tempPageConfigId1 = pageLinkageMapper.selectList(new QueryWrapper<PageLinkageDO>()
                                .in(PAGE_CONFIG_ID, tempSet)
                        );
                        if(tempPageConfigId1!=null && !tempPageConfigId1.isEmpty()){
                            pageConfigId1.addAll(tempPageConfigId1);
                        }
                        // 查询字段下的组件属性
                        List<ColumnComponentAttributeDO> tempPageConfigId5 = columnComponentAttributeMapper.selectList(new QueryWrapper<ColumnComponentAttributeDO>()
                                .in("column_id", tempSet)
                        );
                        if(tempPageConfigId5!=null && !tempPageConfigId5.isEmpty()){
                            pageConfigId5.addAll(tempPageConfigId5);
                        }

                        tempSet.clear();
                    }
                }
                if(!tempSet.isEmpty()){
                    // 查询字段下的联动配置
                    List<PageLinkageDO> tempPageConfigId1 = pageLinkageMapper.selectList(new QueryWrapper<PageLinkageDO>()
                            .in(PAGE_CONFIG_ID, tempSet)
                    );
                    if(tempPageConfigId1!=null && !tempPageConfigId1.isEmpty()){
                        pageConfigId1.addAll(tempPageConfigId1);
                    }
                    // 查询字段下的组件属性
                    List<ColumnComponentAttributeDO> tempPageConfigId5 = columnComponentAttributeMapper.selectList(new QueryWrapper<ColumnComponentAttributeDO>()
                            .in("column_id", tempSet)
                    );
                    if(tempPageConfigId5!=null && !tempPageConfigId5.isEmpty()){
                        pageConfigId5.addAll(tempPageConfigId5);
                    }

                    tempSet.clear();
                }
            }else{
                // 查询字段下的联动配置
                List<PageLinkageDO> tempPageConfigId1 = pageLinkageMapper.selectList(new QueryWrapper<PageLinkageDO>()
                        .in(PAGE_CONFIG_ID, listConfigIds)
                );
                if(tempPageConfigId1!=null && !tempPageConfigId1.isEmpty()){
                    pageConfigId1.addAll(tempPageConfigId1);
                }

                // 查询字段下的组件属性
                List<ColumnComponentAttributeDO> tempPageConfigId5 = columnComponentAttributeMapper.selectList(new QueryWrapper<ColumnComponentAttributeDO>()
                        .in("column_id", listConfigIds)
                );
                if(tempPageConfigId5!=null && !tempPageConfigId5.isEmpty()){
                    pageConfigId5.addAll(tempPageConfigId5);
                }
            }

            Map<Long, List<PageLinkageDO>> mapLink = pageConfigId1.stream().collect(Collectors.groupingBy(PageLinkageDO::getPageConfigId));
            List<Long> list = pageConfigId1.stream().map(PageLinkageDO::getId).collect(Collectors.toList());
            // 查询条件配置
            List<ConditionalTableDO> conditionalTableDOList = conditionalTableMapper.selectList(ConditionalTableDO::getRelevanceId, list);
            Map<Long, List<ConditionalTableDO>> map = conditionalTableDOList.stream().collect(Collectors.groupingBy(ConditionalTableDO::getRelevanceId));

            List<Long> attributeIdList = pageConfigId5.stream().map(ColumnComponentAttributeDO::getAttributeId).collect(Collectors.toList());
            if(attributeIdList!=null && !attributeIdList.isEmpty()){
                List<ComponentAttributeDO> attributeList = componentAttributeMapper.selectList(new QueryWrapper<ComponentAttributeDO>()
                        .in("id", attributeIdList));
                if(attributeList!=null && !attributeList.isEmpty()){
                    pageConfigId5.forEach(data -> {
                        for(ComponentAttributeDO dto : attributeList){
                            if(data.getAttributeId()!=null && data.getAttributeId().equals(dto.getId())){
                                data.setAttributeCode(dto.getAttributeCode());
                                break;
                            }
                        }
                    });
                }
            }

            Map<Long, List<ColumnComponentAttributeDO>> mapColumen = pageConfigId5.stream().collect(Collectors.groupingBy(ColumnComponentAttributeDO::getColumnId));

            beanCondition.forEach(config -> {
                if (mapLink.get(config.getId()) != null) {
                    List<PageLinkageRespVO> bean1 = BeanUtils.toBean(mapLink.get(config.getId()), PageLinkageRespVO.class);
                    bean1.forEach(s -> {
                        if (map.get(s.getId())!=null && !map.get(s.getId()).isEmpty()) {
                            List<ConditionalTableSaveReqVO> sceneList = new ArrayList<>();
                            map.get(s.getId()).forEach(v -> {
                                ConditionalTableSaveReqVO bean2 = BeanUtils.toBean(v, ConditionalTableSaveReqVO.class);
                                if (bean2.getColumnDisplayComponent() != null) {
                                    bean2.setColumnDisplayComponentName(componentTableDOMap.get(bean2.getColumnDisplayComponent()).getComponentName());
                                }

                                // 这里是【表单页>>表单项配置>>联动配置>>切换输入类型>>切换为】的属性配置，不是字段的显示组件的组件属性（挂在cfg_page_list_config表下，用ColumnComponentAttributeDO.columnId关联的）
                                // ColumnComponentAttributeDO的LinkageId，存cfg_conditional_table表的id，只挂在cfg_conditional_table表下面
                                List<ColumnComponentAttributeDO> columnList = this.findColumnComponentAttribute(bean2.getId());
                                if(columnList!=null && !columnList.isEmpty()){
                                    bean2.setColumnComponentAttributeDO(columnList);
                                }else{
                                    bean2.setColumnComponentAttributeDO(null);
                                }

                                if (bean2.getContent() != null) {
                                    bean2.setCondition(JSON.parse(bean2.getContent()));
                                }
                                sceneList.add(bean2);
                            });
                            s.setSceneList(sceneList);
                        }
                    });
                    config.setPageLinkageRespVOS(bean1);
                } else {
                    config.setPageLinkageRespVOS(null);
                }
                config.setColumnComponentAttributeDO(mapColumen.get(config.getId()) != null ? BeanUtils.toBean(mapColumen.get(config.getId()), ColumnComponentAttributeDO.class) : null);
                if (StringUtils.isNotBlank(config.getColumnDictType())) {
                    config.setColumnDictTypeName(dictTypeRespDTOMap.get(config.getColumnDictType()).getName());
                }
            });
        }
        Map<Long, List<PageListConditionSaveReqVO>> mapCondition = beanCondition.stream().collect(Collectors.groupingBy(PageListConditionSaveReqVO::getPageApiId));

        // 获取模型数据
        List<ModuleInfoDO> moduleInfoDOS = moduleInfoMapper.selectList(ModuleInfoDO::getId, pageApiRespVOS.stream().map(PageApiSaveReqVO::getModuleId).collect(Collectors.toList()));
        Map<Long, ModuleInfoDO> mapModule = moduleInfoDOS.stream().collect(Collectors.toMap(ModuleInfoDO::getId, Function.identity()));

        Set<Long> moduleIdList = pageApiRespVOS.stream().map(PageApiSaveReqVO::getModuleId).collect(Collectors.toSet());
        Map<Long,List<ModuleTableSaveReqVO>> tableMap = new TreeMap<>();
        if (CollUtil.isNotEmpty(moduleIdList)) {
            List<ModuleTableSaveReqVO> tables = moduleTableMapper.selectTable(moduleIdList);
            if (CollUtil.isNotEmpty(tables)) {
                tableMap.putAll(tables.stream().collect(Collectors.groupingBy(ModuleTableSaveReqVO::getModuleId)));
            }
        }
        pageApiRespVOS.forEach(s -> {
            // 组装分组信息
            s.setPageGroups(mapGroup.get(s.getId()) != null ? mapGroup.get(s.getId()) : null);
            // 组装子表信息
            s.setTableLayoutConfig(mapSubTable.get(String.valueOf(s.getId())) != null ? mapSubTable.get(String.valueOf(s.getId())) : null);
            // 组装列表配置信息
            s.setPageListConfigs(mapConfig.get(s.getId()) != null ? mapConfig.get(s.getId()) : null);

            // 组装查询条件
            s.setPageListConditions(mapCondition.get(s.getId()) != null ? mapCondition.get(s.getId()) : null);

            // 组装模型数据
            s.setMainTableId(mapModule.get(s.getModuleId()) != null ? mapModule.get(s.getModuleId()).getMainTableId() : null);
            //设置模型表数据
            s.setModuleTables(tableMap.get(s.getModuleId()));
        });
    }

    /**
     * 【表单页>>表单项配置>>联动配置>>切换输入类型>>切换为】的属性配置
     *
     * @param id cfg_page_linkage表的id
     * @return
     */
    private List<ColumnComponentAttributeDO> findColumnComponentAttribute(Long id) {
        List<ColumnComponentAttributeDO> columnList = columnComponentAttributeMapper.selectList(new QueryWrapper<ColumnComponentAttributeDO>()
                .in("linkage_id", id)
        );
        if(columnList!=null && !columnList.isEmpty()){
            List<Long> attributeIds = columnList.stream().map(ColumnComponentAttributeDO::getAttributeId).collect(Collectors.toList());
            if(attributeIds!=null && !attributeIds.isEmpty()){
                List<ComponentAttributeDO> attributeList = componentAttributeMapper.selectList(new QueryWrapper<ComponentAttributeDO>()
                        .in("id", attributeIds));
                if(attributeList!=null && !attributeList.isEmpty()){
                    columnList.forEach(data -> {
                        for(ComponentAttributeDO dto : attributeList){
                            if(data.getAttributeId()!=null && data.getAttributeId().equals(dto.getId())){
                                data.setAttributeCode(dto.getAttributeCode());
                                break;
                            }
                        }
                    });
                }
            }
        }
        return columnList;
    }

    @Override
    public PageResult<PageInfoDO> getPageInfoPage(PageInfoPageReqVO pageReqVO) {
        return pageInfoMapper.selectPage(pageReqVO);
    }

    /**
     * 获取页面列表
     * @param ids
     * @return
     */
    @Override
    public List<PageInfoDO> getPageList(Collection<Long> ids) {
        return pageInfoMapper.selectList(new QueryWrapper<PageInfoDO>().in("id", ids));
    }

    @Override
    public List<TreeNode> listPage() {
        List<PageInfoDO> list = pageInfoMapper.selectList();
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<TreeNode> treeList = new ArrayList<>();
        Set<Long> menuSet = new HashSet<>();
        TreeNode node = null;
        for (PageInfoDO pageInfoDO : list) {
            node = new TreeNode();
            node.setId(pageInfoDO.getId());
            node.setName(pageInfoDO.getPageName());
            node.setParentId(pageInfoDO.getMenuId());
            if (Objects.nonNull(pageInfoDO.getMenuId())) {
                treeList.add(node);
                menuSet.add(pageInfoDO.getMenuId());
            }
        }
        List<FunctionInfoDO> functionList = functionInfoMapper.selectBatchIds(menuSet);
        if (CollUtil.isNotEmpty(functionList)) {
            for (FunctionInfoDO functionInfoDO : functionList) {
                node = new TreeNode();
                node.setId(functionInfoDO.getId());
                node.setName(functionInfoDO.getFunctionName());
                node.setParentId(0L);
                treeList.add(node);
            }
        }
        return treeList;
    }

    /**
     * 复制页面基本信息
     *
     * @param id
     * @param type 复制成页面0、复制成移动端页面1
     */
    @Override
    public void copyPageInfo(Long id, String type) {
        PageInfoDO vo = pageInfoMapper.selectById(id);
        // 插入
        PageInfoDO pageInfo = BeanUtil.copyProperties(vo, PageInfoDO.class);
        pageInfo.setOldId(id);
        pageInfo.setId(null);
        pageInfo.setPageName(pageInfo.getPageName()+"(1)");
        pageInfo.setPageCode(pageInfo.getPageCode()+"(1)");

        if("list".equals(pageInfo.getPageType()) && "1".equals(type)){
            pageInfo.setPageType(MOBILE_LIST);
        }else if("form".equals(pageInfo.getPageType()) && "1".equals(type)){
            pageInfo.setPageType(MOBILE_FORM);
        }

        this.initData(pageInfo);
        pageInfoMapper.insert(pageInfo);

        // 菜单
        if(!"1".equals(type)){
            menuApi.copyButtonMenu(id, pageInfo.getId());
        }

        // 新增页面API
        List<PageApiDO> apiList = pageApiMapper.selectList(PAGE_ID, id);
        if (!apiList.isEmpty()) {
            apiList.forEach(api -> {
                api.setOldId(api.getId());
                api.setPageId(pageInfo.getId());
                api.setId(null);
                this.initData(api);
            });
            pageApiMapper.insertBatch(apiList);
        }

        // 子表设置
        List<SubTableSettingDO> settingList = subTableSettingMapper.selectList(PAGE_ID, id);
        if(settingList!=null && !settingList.isEmpty()){
            for(SubTableSettingDO setting : settingList){
                setting.setPageId(String.valueOf(pageInfo.getId()));
                if(!apiList.isEmpty()){
                    for(PageApiDO api : apiList){
                        if(setting.getPageApiId().equals(String.valueOf(api.getOldId())) && setting.getPageId().equals(String.valueOf(api.getPageId()))){
                            setting.setPageApiId(String.valueOf(api.getId()));
                            break;
                        }
                    }
                }
                setting.setOldId(setting.getId());
                setting.setId(null);
                this.initData(setting);
            }
            subTableSettingMapper.insertBatch(settingList);
        }

        // 插入页面扩展事件
        List<PageExtendEventDO> eventList = pageExtendEventMapper.selectList(PAGE_ID, id);
        if(eventList!=null && !eventList.isEmpty()){
            for(PageExtendEventDO event : eventList){
                event.setPageId(pageInfo.getId());
                event.setOldId(event.getId());
                event.setId(null);
                this.initData(event);
            }
            pageExtendEventMapper.insertBatch(eventList);
        }

        // 表单页查询条件
        List<PageListConditionDO> conditionList = pageListConditionMapper.selectList(PAGE_ID, id);
        if(conditionList!=null && !conditionList.isEmpty()){
            for(PageListConditionDO condition : conditionList){
                condition.setPageId(pageInfo.getId());
                condition.setOldId(condition.getId());
                condition.setId(null);
                if(!apiList.isEmpty()){
                    for(PageApiDO api : apiList){
                        if(condition.getPageApiId().equals(api.getOldId())){
                            condition.setPageApiId(api.getId());
                            break;
                        }
                    }
                }
                this.initData(condition);
            }
            pageListConditionMapper.insertBatch(conditionList);
        }

        // 新增参数page_id
        List<PageParameterDO> parameterList = pageParameterMapper.selectList(PAGE_ID, id);
        if(parameterList!=null && !parameterList.isEmpty()){
            for(PageParameterDO parameter : parameterList){
                parameter.setPageId(pageInfo.getId());
                if(!apiList.isEmpty()){
                    for(PageApiDO api : apiList){
                        if(parameter.getPageApiId().equals(api.getOldId())){
                            parameter.setPageApiId(api.getId());
                            break;
                        }
                    }
                }
                parameter.setOldId(parameter.getId());
                parameter.setId(null);
                this.initData(parameter);
            }
            pageParameterMapper.insertBatch(parameterList);
        }

        // 页面分组
        List<PageGroupDO> groupList = pageGroupMapper.selectList(PAGE_ID, id);
        if (!groupList.isEmpty()) {
            for(PageGroupDO group : groupList){
                group.setPageId(pageInfo.getId());
                if(!apiList.isEmpty()){
                    for(PageApiDO api : apiList){
                        if(group.getPageApiId().equals(api.getOldId())){
                            group.setPageApiId(api.getId());
                            break;
                        }
                    }
                }
                group.setOldId(group.getId());
                group.setId(null);
                this.initData(group);
            }
            pageGroupMapper.insertBatch(groupList);
        }

        // 列表页配置
        List<PageListConfigDO> configList = pageListConfigMapper.selectList(PAGE_ID, id);
        List<Long> pageConfigIdList = configList.stream().map(PageListConfigDO::getId).collect(Collectors.toList());
        if (!configList.isEmpty()) {
            for(PageListConfigDO config : configList){
                config.setPageId(pageInfo.getId());
                if(!apiList.isEmpty()){
                    for(PageApiDO api : apiList){
                        if(config.getPageApiId().equals(api.getOldId())){
                            config.setPageApiId(api.getId());
                            break;
                        }
                    }
                }
                config.setOldId(config.getId());
                config.setId(null);
                this.initData(config);
            }
            pageListConfigMapper.insertBatch(configList);
        }

        // 插入操作按钮
        List<PageButtonDO> buttonList = pageButtonMapper.selectList(PAGE_ID, id);
        List<Long> buttonIdList = buttonList.stream().map(PageButtonDO::getId).collect(Collectors.toList());
        if (!buttonList.isEmpty()) {
            for(PageButtonDO button : buttonList){
                button.setPageId(pageInfo.getId());
                button.setOldId(button.getId());
                button.setId(null);
                this.initData(button);
            }
            pageButtonMapper.insertBatch(buttonList);
        }

        // 新增路由参数
        if(!"1".equals(type)){
            List<ParamterListDO> paramterList = paramterListMapper.selectList(PAGE_ID, id);
            if (!paramterList.isEmpty()) {
                for(ParamterListDO paramter : paramterList){
                    paramter.setPageId(pageInfo.getId());
                    paramter.setOldId(paramter.getId());
                    paramter.setId(null);
                    this.initData(paramter);
                }
                paramterListMapper.insertBatch(paramterList);
            }
        }

        // 附件管理
        List<PageAttachmentInfoDO> attachmentList = pageAttachmentInfoMapper.selectList(PAGE_ID, id);
        List<Long> attachmentIdList = attachmentList.stream().map(PageAttachmentInfoDO::getId).collect(Collectors.toList());
        if (!attachmentList.isEmpty()) {
            for(PageAttachmentInfoDO attachment : attachmentList){
                attachment.setPageId(pageInfo.getId());
                attachment.setOldId(attachment.getId());
                attachment.setId(null);
                this.initData(attachment);
            }
            pageAttachmentInfoMapper.insertBatch(attachmentList);
        }

        if(!attachmentIdList.isEmpty()){
            // 附件管理-指定上传文件
            List<PageAttachmentUploadfileDO> uploadFileList = pageAttachmentUploadfileMapper.selectList("attachment_id", attachmentIdList);
            if (!uploadFileList.isEmpty()) {
                for(PageAttachmentUploadfileDO uploadFile : uploadFileList){
                    for(PageAttachmentInfoDO attachment : attachmentList){
                        if(uploadFile.getAttachmentId().equals(attachment.getOldId())){
                            uploadFile.setAttachmentId(attachment.getId());
                            break;
                        }
                    }
                    uploadFile.setOldId(uploadFile.getId());
                    uploadFile.setId(null);
                    this.initData(uploadFile);
                }
                pageAttachmentUploadfileMapper.insertBatch(uploadFileList);
            }
        }

        if(!buttonIdList.isEmpty()){
            // 按钮条件
            List<ConditionalTableDO> conditionalList = conditionalTableMapper.selectList(RELEVANCE_ID, buttonIdList);
            if (!conditionalList.isEmpty()) {
                for(ConditionalTableDO conditional : conditionalList){
                    for(PageButtonDO button : buttonList){
                        if(conditional.getRelevanceId().equals(button.getOldId())){
                            conditional.setRelevanceId(button.getId());
                            break;
                        }
                    }
                    conditional.setOldId(conditional.getId());
                    conditional.setId(null);
                    this.initData(conditional);
                }
                conditionalTableMapper.insertBatch(conditionalList);
            }

            // 按钮动作
            List<ButtonActionDO> actionList = buttonActionMapper.selectList("page_button_id", buttonIdList);
            if (!actionList.isEmpty()) {
                for(ButtonActionDO action : actionList){
                    for(PageButtonDO button : buttonList){
                        if(action.getPageButtonId().equals(button.getOldId())){
                            action.setPageButtonId(button.getId());
                            break;
                        }
                    }
                    action.setOldId(action.getId());
                    action.setId(null);
                    this.initData(action);
                }
                buttonActionMapper.insertBatch(actionList);
            }
        }

        if(!pageConfigIdList.isEmpty()){
            // 页面校验规则
            List<ValidateRulesDO> ruleList = validateRulesMapper.selectList(PAGE_CONFIG_ID, pageConfigIdList);
            if(ruleList!=null && !ruleList.isEmpty()){
                for(ValidateRulesDO rule : ruleList){
                    for(PageListConfigDO config : configList){
                        if(rule.getPageConfigId().equals(config.getOldId())){
                            rule.setPageConfigId(config.getId());
                            break;
                        }
                    }
                    rule.setOldId(rule.getId());
                    rule.setId(null);
                    this.initData(rule);
                }
                validateRulesMapper.insertBatch(ruleList);
            }

            // 页面联动配置
            List<PageLinkageDO> linkageList = pageLinkageMapper.selectList(PAGE_CONFIG_ID, pageConfigIdList);
            List<Long> linkageIdList = linkageList.stream().map(PageLinkageDO::getId).collect(Collectors.toList());
            if(!linkageList.isEmpty()){
                for(PageLinkageDO linkage : linkageList){
                    for(PageListConfigDO config : configList){
                        if(linkage.getPageConfigId().equals(config.getOldId())){
                            linkage.setPageConfigId(config.getId());
                            break;
                        }
                    }
                    linkage.setOldId(linkage.getId());
                    linkage.setId(null);
                    this.initData(linkage);
                }
                pageLinkageMapper.insertBatch(linkageList);

                // 条件
                List<ConditionalTableDO> tableList = conditionalTableMapper.selectList(RELEVANCE_ID, linkageIdList);
                List<Long> tableIdList = tableList.stream().map(ConditionalTableDO::getId).collect(Collectors.toList());
                if(tableList!=null && !tableList.isEmpty()){
                    for(ConditionalTableDO table : tableList){
                        for(PageLinkageDO linkage : linkageList){
                            if(table.getRelevanceId().equals(linkage.getOldId())){
                                table.setRelevanceId(linkage.getId());
                                break;
                            }
                        }
                        table.setOldId(table.getId());
                        table.setId(null);
                        this.initData(table);
                    }
                    conditionalTableMapper.insertBatch(tableList);
                }

                // 【表单页>>表单项配置>>联动配置>>切换输入类型>>切换为】的属性配置
                List<ColumnComponentAttributeDO> attributeList = columnComponentAttributeMapper.selectList("linkage_id", tableIdList);
                if(attributeList!=null && !attributeList.isEmpty()){
                    for(ColumnComponentAttributeDO attribute : attributeList){
                        for(ConditionalTableDO table : tableList){
                            if(attribute.getLinkageId().equals(table.getOldId())){
                                attribute.setLinkageId(table.getId());
                                attribute.setPageId(pageInfo.getId());
                                break;
                            }
                        }
                        attribute.setOldId(attribute.getId());
                        attribute.setId(null);
                        this.initData(attribute);
                    }
                    columnComponentAttributeMapper.insertBatch(attributeList);
                }

            }

            // 页面事件配置
            List<EventConfigDO> eventConfigList = eventConfigMapper.selectList(PAGE_CONFIG_ID, pageConfigIdList);
            if(eventConfigList!=null && !eventConfigList.isEmpty()){
                for(EventConfigDO event : eventConfigList){
                    for(PageListConfigDO config : configList){
                        if(event.getPageConfigId().equals(config.getOldId())){
                            event.setPageConfigId(config.getId());
                            break;
                        }
                    }
                    event.setOldId(event.getId());
                    event.setId(null);
                    this.initData(event);
                }
                eventConfigMapper.insertBatch(eventConfigList);
            }

            // 新增数据转换规则
            List<DataConversionDO> conversionList = dataConversionMapper.selectList(LIST_CONFIG_ID, pageConfigIdList);
            if(conversionList!=null && !conversionList.isEmpty()){
                for(DataConversionDO conversion : conversionList){
                    for(PageListConfigDO config : configList){
                        if(conversion.getListConfigId().equals(config.getOldId())){
                            conversion.setListConfigId(config.getId());
                            break;
                        }
                    }
                    conversion.setOldId(conversion.getId());
                    conversion.setId(null);
                    this.initData(conversion);
                }
                dataConversionMapper.insertBatch(conversionList);
            }

            // 页面数据格式化
            List<DataFormatDO> formatList = dataFormatMapper.selectList(LIST_CONFIG_ID, pageConfigIdList);
            if(formatList!=null && !formatList.isEmpty()){
                for(DataFormatDO format : formatList){
                    for(PageListConfigDO config : configList){
                        if(format.getListConfigId().equals(config.getOldId())){
                            format.setListConfigId(config.getId());
                            break;
                        }
                    }
                    format.setOldId(format.getId());
                    format.setId(null);
                    this.initData(format);
                }
                dataFormatMapper.insertBatch(formatList);
            }

            // 新增组件属性
            List<ColumnComponentAttributeDO> attributeList = columnComponentAttributeMapper.selectList("column_id", pageConfigIdList);
            if(attributeList!=null && !attributeList.isEmpty()){
                for(ColumnComponentAttributeDO attribute : attributeList){
                    for(PageListConfigDO config : configList){
                        if(attribute.getColumnId().equals(config.getOldId())){
                            attribute.setColumnId(config.getId());
                            attribute.setPageId(config.getPageId());
                            break;
                        }
                    }
                    attribute.setOldId(attribute.getId());
                    attribute.setId(null);
                    this.initData(attribute);
                }
                columnComponentAttributeMapper.insertBatch(attributeList);
            }
        }
    }

    /**
     * 初始化
     *
     * @param vo
     */
    private void initData(BaseDO vo) {
        vo.setCreator(null);
        vo.setCreateTime(null);
        vo.setUpdater(null);
        vo.setUpdateTime(null);
    }

}
