package com.joyintech.yuntai.module.cfg.service.pageattachmentinfo;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.PAGE_ATTACHMENT_INFO_NOT_EXISTS;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.PageAttachmentInfoPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentinfo.vo.PageAttachmentInfoSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentinfo.PageAttachmentInfoDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageattachmentinfo.PageAttachmentInfoMapper;

/**
 * 表单页配置-附件管理 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageAttachmentInfoServiceImpl implements PageAttachmentInfoService {

    @Resource
    private PageAttachmentInfoMapper pageAttachmentInfoMapper;

    @Override
    public Long createPageAttachmentInfo(PageAttachmentInfoSaveReqVO createReqVO) {
        // 插入
        PageAttachmentInfoDO pageAttachmentInfo = BeanUtils.toBean(createReqVO, PageAttachmentInfoDO.class);
        pageAttachmentInfoMapper.insert(pageAttachmentInfo);
        // 返回
        return pageAttachmentInfo.getId();
    }

    @Override
    public void updatePageAttachmentInfo(PageAttachmentInfoSaveReqVO updateReqVO) {
        // 校验存在
        validatePageAttachmentInfoExists(updateReqVO.getId());
        // 更新
        PageAttachmentInfoDO updateObj = BeanUtils.toBean(updateReqVO, PageAttachmentInfoDO.class);
        pageAttachmentInfoMapper.updateById(updateObj);
    }

    @Override
    public void deletePageAttachmentInfo(Long id) {
        // 校验存在
        validatePageAttachmentInfoExists(id);
        // 删除
        pageAttachmentInfoMapper.deleteById(id);
    }

    private void validatePageAttachmentInfoExists(Long id) {
        if (pageAttachmentInfoMapper.selectById(id) == null) {
            throw exception(PAGE_ATTACHMENT_INFO_NOT_EXISTS);
        }
    }

    @Override
    public PageAttachmentInfoDO getPageAttachmentInfo(Long id) {
        return pageAttachmentInfoMapper.selectById(id);
    }

    @Override
    public PageResult<PageAttachmentInfoDO> getPageAttachmentInfoPage(PageAttachmentInfoPageReqVO pageReqVO) {
        return pageAttachmentInfoMapper.selectPage(pageReqVO);
    }

}