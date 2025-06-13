package com.joyintech.yuntai.module.cfg.service.paramterlist;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.controller.admin.pagelistconfig.vo.PageListConfigSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pagegroup.PageGroupDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.paramterlist.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.paramterlist.ParamterListDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.paramterlist.ParamterListMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 页面路由参数 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class ParamterListServiceImpl implements ParamterListService {

    @Resource
    private ParamterListMapper paramterListMapper;

    @Override
    public Long createParamterList(ParamterListSaveReqVO createReqVO) {
        // 插入
        ParamterListDO paramterList = BeanUtils.toBean(createReqVO, ParamterListDO.class);
        paramterListMapper.insert(paramterList);
        // 返回
        return paramterList.getId();
    }

    @Override
    public void updateParamterList(ParamterListSaveReqVO updateReqVO) {
        // 校验存在
        validateParamterListExists(updateReqVO.getId());
        // 更新
        ParamterListDO updateObj = BeanUtils.toBean(updateReqVO, ParamterListDO.class);
        paramterListMapper.updateById(updateObj);
    }

    @Override
    public void deleteParamterList(Long id) {
        // 校验存在
        validateParamterListExists(id);
        // 删除
        paramterListMapper.deleteById(id);
    }

    private void validateParamterListExists(Long id) {
        if (paramterListMapper.selectById(id) == null) {
            throw exception(PARAMTER_LIST_NOT_EXISTS);
        }
    }

    @Override
    public ParamterListDO getParamterList(Long id) {
        return paramterListMapper.selectById(id);
    }

    @Override
    public PageResult<ParamterListDO> getParamterListPage(ParamterListPageReqVO pageReqVO) {
        return paramterListMapper.selectPage(pageReqVO);
    }

    public void addParamterList(List<ParamterListSaveReqVO> paramterListSaveReqVOS) {
        // 新增路由参数
        List<ParamterListDO> bean = BeanUtils.toBean(paramterListSaveReqVOS, ParamterListDO.class);
        paramterListMapper.insertOrUpdateBatch(bean);

        // 删除没有的路由参数
        List<Long> list = bean.stream().map(ParamterListDO::getId).collect(Collectors.toList());
        paramterListMapper.delete(new QueryWrapper<ParamterListDO>()
                .eq("page_id", bean.get(0).getPageId())
                .notIn("id", list)
        );
    }
}