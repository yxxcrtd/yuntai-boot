package com.joyintech.yuntai.module.cfg.service.pagebutton;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.controller.admin.buttonaction.vo.ButtonActionSaveReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.conditionaltable.vo.ConditionalTableRespVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.buttonaction.ButtonActionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columncomponentattribute.ColumnComponentAttributeDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.conditionaltable.ConditionalTableDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageapi.PageApiDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.buttonaction.ButtonActionMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.columncomponentattribute.ColumnComponentAttributeMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.conditionaltable.ConditionalTableMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.pagebutton.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagebutton.PageButtonDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.pagebutton.PageButtonMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面操作按钮 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageButtonServiceImpl implements PageButtonService {

    @Resource
    private PageButtonMapper pageButtonMapper;

    @Resource
    private ConditionalTableMapper conditionalTableMapper;

    @Resource
    private ButtonActionMapper buttonActionMapper;
    @Resource
    private ColumnComponentAttributeMapper columnComponentAttributeMapper;

    @Override
    public Long createPageButton(PageButtonSaveReqVO createReqVO) {
        // 插入
        PageButtonDO pageButton = BeanUtils.toBean(createReqVO, PageButtonDO.class);
        pageButtonMapper.insert(pageButton);
        // 返回
        return pageButton.getId();
    }

    @Override
    public void updatePageButton(PageButtonSaveReqVO updateReqVO) {
        // 校验存在
        validatePageButtonExists(updateReqVO.getId());
        // 更新
        PageButtonDO updateObj = BeanUtils.toBean(updateReqVO, PageButtonDO.class);
        pageButtonMapper.updateById(updateObj);
    }

    @Override
    public void deletePageButton(Long id) {
        // 校验存在
        validatePageButtonExists(id);
        // 删除
        pageButtonMapper.deleteById(id);
    }

    private void validatePageButtonExists(Long id) {
        if (pageButtonMapper.selectById(id) == null) {
            throw exception(PAGE_BUTTON_NOT_EXISTS);
        }
    }

    @Override
    public PageButtonDO getPageButton(Long id) {
        return pageButtonMapper.selectById(id);
    }

    @Override
    public PageResult<PageButtonDO> getPageButtonPage(PageButtonPageReqVO pageReqVO) {
        return pageButtonMapper.selectPage(pageReqVO);
    }

    public void addPageButton(List<PageButtonSaveReqVO> pageButtons) {
        // 插入操作按钮
        List<PageButtonDO> pageButtonDOS = BeanUtils.toBean(pageButtons, PageButtonDO.class);
        pageButtonMapper.insertOrUpdateBatch(pageButtonDOS);

        for (int i = 0; i < pageButtons.size(); i++) {
            pageButtons.get(i).setId(pageButtonDOS.get(i).getId());
        }

        // 删除没有的操作按钮
        if (!pageButtonDOS.isEmpty()) {
            List<Long> list = pageButtonDOS.stream().map(PageButtonDO::getId).collect(Collectors.toList());
            pageButtonMapper.delete(new QueryWrapper<PageButtonDO>()
                    .eq("page_id", pageButtonDOS.get(0).getPageId())
                    .notIn("id", list)
            );
        }

        /*Map<String, PageButtonDO> map = pageButtonDOS.stream().collect(Collectors.toMap(
                pageButtonDO -> pageButtonDO.getButtonType() + pageButtonDO.getButtonName() + pageButtonDO.getOpenWay() + pageButtonDO.getButtonStyle(),
                Function.identity()
        ));*/
        // 按钮条件
        List<ConditionalTableRespVO> conditionalTableRespVOS =new ArrayList<>();
        List<Long> longs = new ArrayList<>();
        // 按钮动作
        List<ButtonActionDO> buttonActionSaveReqVOS =new ArrayList<>();
        List<Long> longs1 = new ArrayList<>();
        // 按钮组件
        List<ColumnComponentAttributeDO> componentAttributeList =new ArrayList<>();
        List<Long> longsa = new ArrayList<>();
        pageButtons.forEach(button -> {
            if (button.getConditionalTableRespVO() != null) {
                button.getConditionalTableRespVO().setRelevanceId(button.getId());
                if (button.getConditionalTableRespVO().getCondition() != null) {
                    button.getConditionalTableRespVO().setContent(JSON.toJSONString(button.getConditionalTableRespVO().getCondition()));
                }
                // 显示类型
                if (CollectionUtil.isNotEmpty(button.getConditionalTableRespVO().getShowPageType())) {
                    button.getConditionalTableRespVO().setShowPageTypeCode(String.join(",", button.getConditionalTableRespVO().getShowPageType()));
                }

                conditionalTableRespVOS.add(button.getConditionalTableRespVO());
            } else {
                longs.add(button.getId());
            }
            if (button.getShowButtonActionCfg() != null) {
                button.getShowButtonActionCfg().setPageButtonId(button.getId());
                ButtonActionDO bean = BeanUtils.toBean(button.getShowButtonActionCfg(), ButtonActionDO.class);
                if (CollectionUtil.isNotEmpty(button.getShowButtonActionCfg().getServerParams())) {
                    bean.setServerParams(String.join(",", button.getShowButtonActionCfg().getServerParams()));
                }
                buttonActionSaveReqVOS.add(bean);
            } else {
                longs1.add(button.getId());
            }
            //按钮组件
            if (CollectionUtil.isEmpty(button.getColumnComponentAttributeDO())) {
                longsa.add(button.getId());
            }else {
                button.getColumnComponentAttributeDO().forEach(e-> {
                    e.setColumnId(button.getId());
                });
                componentAttributeList.addAll(button.getColumnComponentAttributeDO());
            }
        });
        // 按钮条件
        if (!conditionalTableRespVOS.isEmpty()) {
            List<ConditionalTableDO> bean1 = BeanUtils.toBean(conditionalTableRespVOS, ConditionalTableDO.class);
            conditionalTableMapper.insertOrUpdateBatch(bean1);
        }
        if (!longs.isEmpty()) {
            // 删除没有的按钮显示配置
            conditionalTableMapper.delete(new QueryWrapper<ConditionalTableDO>()
                    .in("relevance_id", longs)
            );
        }
        // 按钮动作
        if (!buttonActionSaveReqVOS.isEmpty()) {
            buttonActionMapper.insertOrUpdateBatch(buttonActionSaveReqVOS);
        }

        if (!longs1.isEmpty()) {
            // 删除没有的按钮动作
            buttonActionMapper.delete(new QueryWrapper<ButtonActionDO>()
                    .in("page_button_id", longs1)
            );
        }
        //按钮组件
        if (!componentAttributeList.isEmpty()) {
            columnComponentAttributeMapper.insertOrUpdateBatch(componentAttributeList);
        }
        if (!longsa.isEmpty()) {
            // 删除没有的按钮组件
            columnComponentAttributeMapper.delete(new QueryWrapper<ColumnComponentAttributeDO>().in("COLUMN_ID", longsa));
        }

    }
}