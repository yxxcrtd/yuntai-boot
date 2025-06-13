package com.joyintech.yuntai.module.infra.api.file;

import com.joyintech.yuntai.module.infra.dal.dataobject.file.FileDO;
import com.joyintech.yuntai.module.infra.service.file.FileService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 文件 API 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class FileApiImpl implements FileApi {

    @Resource
    private FileService fileService;

    @Override
    public String createFile(String name, String path, byte[] content) {
        FileDO file = fileService.createFile(name, path, content, null);
        if(file!=null){
            return file.getUrl();
        }
        return null;
    }

}
