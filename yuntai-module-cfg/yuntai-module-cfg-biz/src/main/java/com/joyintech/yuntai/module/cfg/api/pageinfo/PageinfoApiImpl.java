package com.joyintech.yuntai.module.cfg.api.pageinfo;

import cn.hutool.core.bean.BeanUtil;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.ProcessDesignRespVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import com.joyintech.yuntai.module.cfg.pageinfo.PageinfoApi;
import com.joyintech.yuntai.module.cfg.pageinfo.dto.PageInfoDTO;
import com.joyintech.yuntai.module.cfg.service.pageinfo.PageInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/11/7
 */
@Service
public class PageinfoApiImpl implements PageinfoApi {
    @Resource
    private PageInfoService pageInfoService;

    @Override
    public List<PageInfoDTO> getPageInfoList(Set<Long> pageIds) {
        List<PageInfoDO> pageList = pageInfoService.getPageList(pageIds);
        return BeanUtil.copyToList(pageList, PageInfoDTO.class);
    }

}
