package com.joyintech.yuntai.module.infra.service.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.date.DateUtils;
import com.joyintech.yuntai.framework.common.util.io.FileUtils;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.infra.framework.file.core.client.FileClient;
import com.joyintech.yuntai.module.infra.framework.file.core.client.s3.FilePresignedUrlRespDTO;
import com.joyintech.yuntai.module.infra.framework.file.core.utils.FileTypeUtils;
import com.joyintech.yuntai.module.infra.controller.admin.file.vo.file.FileCreateReqVO;
import com.joyintech.yuntai.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import com.joyintech.yuntai.module.infra.controller.admin.file.vo.file.FilePresignedUrlRespVO;
import com.joyintech.yuntai.module.infra.dal.dataobject.file.FileDO;
import com.joyintech.yuntai.module.infra.dal.mysql.file.FileMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.infra.enums.ErrorCodeConstants.FILE_NOT_EXISTS;

/**
 * 文件 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileConfigService fileConfigService;

    @Resource
    private FileMapper fileMapper;

    @Override
    public PageResult<FileDO> getFilePage(FilePageReqVO pageReqVO) {
        return fileMapper.selectPage(pageReqVO);
    }

    private static final Log logger = LogFactory.getLog(FileServiceImpl.class);

    @Override
    @SneakyThrows
    public FileDO createFile(String name, String path, byte[] content, FileDO dto) {
        String type = FileTypeUtils.getMineType(content, name);
        InputStream input = new ByteArrayInputStream(content);

        //DE 临时文件路径
        String filePath = "/home/tomcat/uploadFile/templateFile/"+ DateUtils.getNewSysDate();
        savePic(input, filePath, name);

        // 保存到数据库
        FileDO file = new FileDO();
        file.setName(name);
        file.setPath(name);
        file.setUrl(filePath+"/"+name);
        file.setType(type);
        file.setSize(content.length);

        if(dto!=null){
            file.setAttachmentType(dto.getAttachmentType());
            file.setUploadUser(dto.getUploadUser());
            file.setUploadTime(dto.getUploadTime());
            file.setRemark(dto.getRemark());
        }

        fileMapper.insert(file);
        return file;

        /*
        // 计算默认的 path 名
        String type = FileTypeUtils.getMineType(content, name);
        if (StrUtil.isEmpty(path)) {
            path = FileUtils.generatePath(content, name);
        }
        // 如果 name 为空，则使用 path 填充
        if (StrUtil.isEmpty(name)) {
            name = path;
        }

        // 上传到文件存储器
        FileClient client = fileConfigService.getMasterFileClient();
        Assert.notNull(client, "客户端(master) 不能为空");
        String url = client.upload(content, path, type);

        // 保存到数据库
        FileDO file = new FileDO();
        file.setConfigId(client.getId());
        file.setName(name);
        file.setPath(path);
        file.setUrl(url);
        file.setType(type);
        file.setSize(content.length);
        fileMapper.insert(file);
        return url;*/
    }

    @Override
    public Long createFile(FileCreateReqVO createReqVO) {
        FileDO file = BeanUtils.toBean(createReqVO, FileDO.class);
        fileMapper.insert(file);
        return file.getId();
    }

    @Override
    public void deleteFile(Long id) throws Exception {
        // 校验存在
        FileDO file = validateFileExists(id);

        // 从文件存储器中删除
        FileClient client = fileConfigService.getFileClient(file.getConfigId());
        Assert.notNull(client, "客户端({}) 不能为空", file.getConfigId());
        client.delete(file.getPath());

        // 删除记录
        fileMapper.deleteById(id);
    }

    private FileDO validateFileExists(Long id) {
        FileDO fileDO = fileMapper.selectById(id);
        if (fileDO == null) {
            throw exception(FILE_NOT_EXISTS);
        }
        return fileDO;
    }

    @Override
    public byte[] getFileContent(Long configId, String path) throws Exception {
        FileClient client = fileConfigService.getFileClient(configId);
        Assert.notNull(client, "客户端({}) 不能为空", configId);
        return client.getContent(path);
    }

    @Override
    public FilePresignedUrlRespVO getFilePresignedUrl(String path) throws Exception {
        FileClient fileClient = fileConfigService.getMasterFileClient();
        FilePresignedUrlRespDTO presignedObjectUrl = fileClient.getPresignedObjectUrl(path);
        return BeanUtils.toBean(presignedObjectUrl, FilePresignedUrlRespVO.class,
                object -> object.setConfigId(fileClient.getId()));
    }

    /**
     * 根据文件流保存文件到指定的目录
     * @param inputStream 文件流
     * @param bookpath 文件路径
     * @param filename 文件名
     * @return void
     * @throws
     */
    public void savePic(InputStream inputStream, String bookpath, String filename){
        logger.info("文件保存到指定的目录");
        String seperator = File.separator;
        String url = bookpath + seperator + filename;
        // 判断文件夹是否存在，若不存在在新建
        this.mkdirs(bookpath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(url);

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                fos.write(bs, 0, len);
            }
        } catch (FileNotFoundException e) {
            logger.error("文件不存在："+url, e);
        } catch (IOException e) {
            logger.error("读取流或者写入流异常", e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * @Description: 检查路径是否存在，若不存在则创建
     * @param fileurl 路径
     * @return void
     */
    public void mkdirs(String fileurl) {
        File dir = new File(fileurl);
        // 判断文件夹是否存在
        if (!dir.exists()) {
            // 不存在则创建
            if (!System.getProperty("os.name").toLowerCase(Locale.ENGLISH).equals("windows")) {
                // Linux下设置写权限，windows下不用此语句
                try{
                    if (!dir.setWritable(true, false)) {
                        logger.error("dir.setWritable failed");
                    }
                } catch(Exception e) {
                    logger.error("error", e);
                }
            }
            try{
                dir.mkdirs();
            } catch(Exception e) {
                logger.error("error", e);
            }
        }
    }

    /**
     * 查找附件
     *
     * @param id id值
     * @return
     */
    @Override
    public FileDO findFile(Long id) {
        return fileMapper.selectById(id);
    }

}
