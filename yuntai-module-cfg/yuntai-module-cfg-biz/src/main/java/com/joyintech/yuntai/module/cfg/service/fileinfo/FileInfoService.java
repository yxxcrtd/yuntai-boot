package com.joyintech.yuntai.module.cfg.service.fileinfo;

import java.util.List;
import java.util.Map;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.fileinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.fileinfo.FileInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;

/**
 * 上传附件 Service 接口
 *
 * @author 兆尹云台
 */
public interface FileInfoService {

    /**
     * 创建上传附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFileInfo(@Valid FileInfoSaveReqVO createReqVO);

    /**
     * 更新上传附件
     *
     * @param updateReqVO 更新信息
     */
    void updateFileInfo(@Valid FileInfoSaveReqVO updateReqVO);

    /**
     * 删除上传附件
     *
     * @param id 编号
     */
    void deleteFileInfo(Long id);

    /**
     * 获得上传附件
     *
     * @param id 编号
     * @return 上传附件
     */
    FileInfoDO getFileInfo(Long id);

    /**
     * 获得上传附件分页
     *
     * @param pageReqVO 分页查询
     * @return 上传附件分页
     */
    PageResult<FileInfoDO> getFileInfoPage(FileInfoPageReqVO pageReqVO);

    void updateBatch(List<Map> attachmentList);

    void delAttachmentList(String pageId);

    List<FileInfoDO> findAttachmentList(List<String> pageId);

    List<FileInfoDO> getFileListByFileId(List<String> list);
}