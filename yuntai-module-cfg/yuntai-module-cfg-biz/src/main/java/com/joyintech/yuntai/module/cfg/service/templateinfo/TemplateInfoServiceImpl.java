package com.joyintech.yuntai.module.cfg.service.templateinfo;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.joyintech.yuntai.framework.common.util.tree.TreeNode;
import com.joyintech.yuntai.framework.common.util.tree.TreeUtil;
import com.joyintech.yuntai.module.cfg.controller.admin.templatecontent.vo.TemplateContentSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templatecontent.TemplateContentDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templategroup.TemplateGroupDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.templatecontent.TemplateContentMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.templategroup.TemplateGroupMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import com.joyintech.yuntai.module.cfg.controller.admin.templateinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.templateinfo.TemplateInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.templateinfo.TemplateInfoMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 模版 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class TemplateInfoServiceImpl implements TemplateInfoService {

    @Resource
    private TemplateInfoMapper templateInfoMapper;

    @Resource
    private TemplateContentMapper contentMapper;

    @Resource
    private TemplateGroupMapper groupMapper;

    /**
     * 保存
     */
    private void save(TemplateInfoSaveReqVO createReqVO) {
        if (CollUtil.isNotEmpty(createReqVO.getApiList())){
            createReqVO.getApiList().forEach(item -> {
                item.setType(1);
                item.setTemplateId(createReqVO.getId());
                item.setId(IdWorker.getId());
            });
            contentMapper.insertBatch(BeanUtils.toBean(createReqVO.getApiList(), TemplateContentDO.class));
        }
        if (CollUtil.isNotEmpty(createReqVO.getParamList())){
            createReqVO.getParamList().forEach(item -> {
                item.setType(2);
                item.setTemplateId(createReqVO.getId());
                item.setId(IdWorker.getId());
            });
            contentMapper.insertBatch(BeanUtils.toBean(createReqVO.getParamList(), TemplateContentDO.class));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTemplateInfo(TemplateInfoSaveReqVO createReqVO) {
        // 插入
        TemplateInfoDO templateInfo = BeanUtils.toBean(createReqVO, TemplateInfoDO.class);
        templateInfoMapper.insert(templateInfo);
        createReqVO.setId(templateInfo.getId());
        this.save(createReqVO);
        return templateInfo.getId();
    }

    @Override
    public void updateTemplateInfo(TemplateInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateTemplateInfoExists(updateReqVO.getId());
        // 更新
        TemplateInfoDO updateObj = BeanUtils.toBean(updateReqVO, TemplateInfoDO.class);
        templateInfoMapper.updateById(updateObj);
        //删除
        contentMapper.delete(TemplateContentDO::getTemplateId, updateReqVO.getId());
        this.save(updateReqVO);
    }

    @Override
    public void deleteTemplateInfo(Long id) {
        // 校验存在
        validateTemplateInfoExists(id);
        // 删除
        templateInfoMapper.deleteById(id);
        contentMapper.delete(TemplateContentDO::getTemplateId, id);
    }

    private void validateTemplateInfoExists(Long id) {
        if (templateInfoMapper.selectById(id) == null) {
            throw exception(TEMPLATE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public TemplateInfoRespVO getTemplateInfo(Long id) {
        TemplateInfoDO infoDO = templateInfoMapper.selectById(id);
        TemplateInfoRespVO infoRespVO = BeanUtils.toBean(infoDO, TemplateInfoRespVO.class);
        List<TemplateContentDO> contentDOList = contentMapper.selectList(TemplateContentDO::getTemplateId, id);
        if (CollUtil.isNotEmpty(contentDOList)) {
            List<TemplateContentSaveReqVO> contentList = BeanUtils.toBean(contentDOList, TemplateContentSaveReqVO.class);
            List<TemplateContentSaveReqVO> apiList = contentList.stream().filter(item-> Objects.equals(item.getType(), 1)).collect(Collectors.toList());
            List<TemplateContentSaveReqVO> paramList = contentList.stream().filter(item-> Objects.equals(item.getType(), 2)).collect(Collectors.toList());
            infoRespVO.setApiList(CollUtil.isNotEmpty(apiList) ? apiList : Collections.emptyList());
            infoRespVO.setParamList(CollUtil.isNotEmpty(paramList) ? paramList : Collections.emptyList());
        }
        return infoRespVO;
    }

    @Override
    public PageResult<TemplateInfoDO> getTemplateInfoPage(TemplateInfoPageReqVO pageReqVO) {
        if (Objects.nonNull(pageReqVO.getGroupId())){
            List<Long> groupIds = new ArrayList<>();
            List<TemplateGroupDO> groupList = groupMapper.selectList();
            TreeUtil.findByIdWithChildren(BeanUtils.toBean(groupList, TreeNode.class), pageReqVO.getGroupId(),groupIds);
            pageReqVO.setGroupIds(groupIds);
            pageReqVO.setGroupId(null);
        }
        return templateInfoMapper.selectPage(pageReqVO);
    }


}
