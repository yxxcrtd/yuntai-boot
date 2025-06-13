package com.joyintech.yuntai.module.cfg.service.fileinfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.controller.admin.fileinfo.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.fileinfo.FileInfoDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.cfg.dal.mysql.fileinfo.FileInfoMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.*;

/**
 * 上传附件 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class FileInfoServiceImpl implements FileInfoService {

    @Resource
    private FileInfoMapper fileInfoMapper;

    @Override
    public Long createFileInfo(FileInfoSaveReqVO createReqVO) {
        // 插入
        FileInfoDO fileInfo = BeanUtils.toBean(createReqVO, FileInfoDO.class);
        fileInfoMapper.insert(fileInfo);
        // 返回
        return fileInfo.getId();
    }

    @Override
    public void updateFileInfo(FileInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateFileInfoExists(updateReqVO.getId());
        // 更新
        FileInfoDO updateObj = BeanUtils.toBean(updateReqVO, FileInfoDO.class);
        fileInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteFileInfo(Long id) {
        // 校验存在
        validateFileInfoExists(id);
        // 删除
        fileInfoMapper.deleteById(id);
    }

    private void validateFileInfoExists(Long id) {
        if (fileInfoMapper.selectById(id) == null) {
            throw exception(FILE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public FileInfoDO getFileInfo(Long id) {
        return fileInfoMapper.selectById(id);
    }

    @Override
    public PageResult<FileInfoDO> getFileInfoPage(FileInfoPageReqVO pageReqVO) {
        return fileInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FileInfoDO> findAttachmentList(List<String> pageId) {
        QueryWrapper<FileInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("page_id", pageId.get(0));
        queryWrapper.isNull("attachment_type");
        queryWrapper.orderByAsc("create_time");
        return fileInfoMapper.selectList(queryWrapper);
    }

    @Override
    public List<FileInfoDO> getFileListByFileId(List<String> list) {
        QueryWrapper<FileInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("FILE_ID", list);
        queryWrapper.eq("DELETED",0);
        return fileInfoMapper.selectList(queryWrapper);
    }

    @Override
    public void updateBatch(List<Map> attachmentList) {
        List<FileInfoDO> fileList = new ArrayList<>();
        for(Map attachment : attachmentList){
            FileInfoDO file = new FileInfoDO();
            if(attachment.containsKey("id")){
                file.setId(Long.valueOf((String)attachment.get("id")));
            }
            if(attachment.containsKey("pageId") && attachment.get("pageId")!=null){
                file.setPageId(Long.valueOf((String)attachment.get("pageId")));
            }
            if(attachment.containsKey("flowId")){
                file.setFlowId((String)attachment.get("flowId"));
            }
            if(attachment.containsKey("flowNodeId")){
                file.setFlowNodeId((String)attachment.get("flowNodeId"));
            }
            if(attachment.containsKey("fileType")){
                file.setFileType((String)attachment.get("fileType"));
            }
            if(attachment.containsKey("fileName")){
                file.setFileName((String)attachment.get("fileName"));
            }
            if(attachment.containsKey("uploadUserId")){
                file.setUploadUserId(String.valueOf(attachment.get("uploadUserId")));
            }
            if(attachment.containsKey("uploadDate")){
                file.setUploadDate(LocalDateTime.parse((String)attachment.get("uploadDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            if(attachment.containsKey("isRequire") && attachment.get("isRequire")!=null){
                Object value = attachment.get("isRequire");
                boolean isRequire;
                if (value instanceof Boolean) {
                    isRequire = (Boolean) value;
                } else if (value instanceof String) {
                    isRequire = Boolean.parseBoolean(String.valueOf(value));
                } else {
                    // 默认为false
                    isRequire = false;
                }
                if(value instanceof Integer){
                    file.setIsRequire(Long.valueOf(String.valueOf(value)));
                }else{
                    long numericValue = isRequire ? 1L : 0L;
                    file.setIsRequire(numericValue);
                }

            }
            if(attachment.containsKey("fileId")){
                file.setFileId((String)attachment.get("fileId"));
            }
            if(attachment.containsKey("fileUrl")){
                file.setFileUrl((String)attachment.get("fileUrl"));
            }
            if(attachment.containsKey("fileSize") && attachment.get("fileSize")!=null){
                file.setFileSize(new BigDecimal(attachment.get("fileSize").toString()));
            }
            if(attachment.containsKey("attachmentType")){
                file.setAttachmentType((String)attachment.get("attachmentType"));
            }
            if(attachment.containsKey("fileTypeText")){
                file.setFileTypeText((String)attachment.get("fileTypeText"));
            }
            if(attachment.containsKey("oriAttachmentId")){
                file.setOriAttachmentId((String)attachment.get("oriAttachmentId"));
            }
            fileList.add(file);
        }
        fileInfoMapper.insertOrUpdateBatch(fileList);

        // 删除没有的事件配置
        List<Long> longs1 = fileList.stream().map(FileInfoDO::getId).collect(Collectors.toList());
        fileInfoMapper.delete(new QueryWrapper<FileInfoDO>()
                .eq("page_id", fileList.get(0).getPageId())
                .isNull("attachment_type")
                .notIn("id", longs1)
        );

    }

    @Override
    public void delAttachmentList(String pageId) {
        fileInfoMapper.delete(new QueryWrapper<FileInfoDO>()
                .eq("page_id", pageId)
                .isNull("attachment_type"));
    }
}