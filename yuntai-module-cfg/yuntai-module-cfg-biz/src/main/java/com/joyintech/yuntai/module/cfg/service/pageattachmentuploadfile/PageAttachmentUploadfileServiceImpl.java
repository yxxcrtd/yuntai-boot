package com.joyintech.yuntai.module.cfg.service.pageattachmentuploadfile;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.PAGE_ATTACHMENT_UPLOADFILE_NOT_EXISTS;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.PageAttachmentUploadfilePageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.pageattachmentuploadfile.vo.PageAttachmentUploadfileSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageattachmentuploadfile.PageAttachmentUploadfileDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.pageattachmentuploadfile.PageAttachmentUploadfileMapper;

/**
 * 表单页配置-附件管理-指定上传文件 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class PageAttachmentUploadfileServiceImpl implements PageAttachmentUploadfileService {

    @Resource
    private PageAttachmentUploadfileMapper pageAttachmentUploadfileMapper;

    @Override
    public Long createPageAttachmentUploadfile(PageAttachmentUploadfileSaveReqVO createReqVO) {
        // 插入
        PageAttachmentUploadfileDO pageAttachmentUploadfile = BeanUtils.toBean(createReqVO, PageAttachmentUploadfileDO.class);
        pageAttachmentUploadfileMapper.insert(pageAttachmentUploadfile);
        // 返回
        return pageAttachmentUploadfile.getId();
    }

    @Override
    public void updatePageAttachmentUploadfile(PageAttachmentUploadfileSaveReqVO updateReqVO) {
        // 校验存在
        validatePageAttachmentUploadfileExists(updateReqVO.getId());
        // 更新
        PageAttachmentUploadfileDO updateObj = BeanUtils.toBean(updateReqVO, PageAttachmentUploadfileDO.class);
        pageAttachmentUploadfileMapper.updateById(updateObj);
    }

    @Override
    public void deletePageAttachmentUploadfile(Long id) {
        // 校验存在
        validatePageAttachmentUploadfileExists(id);
        // 删除
        pageAttachmentUploadfileMapper.deleteById(id);
    }

    private void validatePageAttachmentUploadfileExists(Long id) {
        if (pageAttachmentUploadfileMapper.selectById(id) == null) {
            throw exception(PAGE_ATTACHMENT_UPLOADFILE_NOT_EXISTS);
        }
    }

    @Override
    public PageAttachmentUploadfileDO getPageAttachmentUploadfile(Long id) {
        return pageAttachmentUploadfileMapper.selectById(id);
    }

    @Override
    public PageResult<PageAttachmentUploadfileDO> getPageAttachmentUploadfilePage(PageAttachmentUploadfilePageReqVO pageReqVO) {
        return pageAttachmentUploadfileMapper.selectPage(pageReqVO);
    }

}