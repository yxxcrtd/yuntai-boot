package com.joyintech.yuntai.module.cfg.pageinfo;

import com.joyintech.yuntai.module.cfg.pageinfo.dto.PageInfoDTO;

import java.util.List;
import java.util.Set;

/**
 * 描述
 * 页面基本信息表
 * @Author Administrator
 * @Date 2024/11/7
 */
public interface PageinfoApi {


    /**
     * 根据id 查询页面信息
     * @param pageIds
     * @return
     */
    List<PageInfoDTO> getPageInfoList(Set<Long> pageIds);

}
