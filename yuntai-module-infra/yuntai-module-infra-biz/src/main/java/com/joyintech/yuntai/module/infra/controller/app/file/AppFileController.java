package com.joyintech.yuntai.module.infra.controller.app.file;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import cn.hutool.core.io.IoUtil;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.module.infra.controller.app.file.vo.AppFileUploadReqVO;
import com.joyintech.yuntai.module.infra.dal.dataobject.file.FileDO;
import com.joyintech.yuntai.module.infra.service.file.FileService;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 文件存储")
@RestController
@RequestMapping("/infra/file")
@Validated
@Slf4j
public class AppFileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public CommonResult<FileDO> uploadFile(AppFileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String path = uploadReqVO.getPath();

        FileDO dto = new FileDO();
        dto.setAttachmentType(uploadReqVO.getAttachmentType());
        dto.setUploadUser(uploadReqVO.getUploadUser());
        dto.setUploadTime(uploadReqVO.getUploadTime());
        dto.setRemark(uploadReqVO.getRemark());

        return success(fileService.createFile(file.getOriginalFilename(), path, IoUtil.readBytes(file.getInputStream()), dto));
    }

    @Operation(summary = "下载文件")
    @GetMapping("/download")
    public void download(HttpServletResponse response, @RequestParam("id") Long id) throws IOException {
        FileDO file = fileService.findFile(id);
        Path path = Paths.get(file.getUrl());
        byte[] content = Files.readAllBytes(path);
        // 设置 header 和 contentType
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        response.setContentType(file.getType());
        // 针对 video 的特殊处理，解决视频地址在移动端播放的兼容性问题
        if (StrUtil.containsIgnoreCase(file.getType(), "video")) {
            response.setHeader("Content-Length", String.valueOf(content.length - 1));
            response.setHeader("Content-Range", String.valueOf(content.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
        }
        // 输出附件
        IoUtil.write(response.getOutputStream(), false, content);
    }


}
