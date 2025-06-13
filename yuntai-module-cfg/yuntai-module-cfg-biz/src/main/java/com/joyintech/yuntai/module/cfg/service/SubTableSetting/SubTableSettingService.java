package com.joyintech.yuntai.module.cfg.service.SubTableSetting;

import java.util.*;
import javax.validation.*;
import com.joyintech.yuntai.module.cfg.controller.admin.SubTableSetting.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.SubTableSetting.SubTableSettingDO;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;

/**
 * 子表设置 Service 接口
 *
 * @author 兆尹云台
 */
public interface SubTableSettingService {

    /**
     * 创建子表设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSubTableSetting(@Valid SubTableSettingSaveReqVO createReqVO);

    /**
     * 更新子表设置
     *
     * @param updateReqVO 更新信息
     */
    void updateSubTableSetting(@Valid SubTableSettingSaveReqVO updateReqVO);

    /**
     * 删除子表设置
     *
     * @param id 编号
     */
    void deleteSubTableSetting(Long id);

    /**
     * 获得子表设置
     *
     * @param id 编号
     * @return 子表设置
     */
    SubTableSettingDO getSubTableSetting(Long id);

    /**
     * 获得子表设置分页
     *
     * @param pageReqVO 分页查询
     * @return 子表设置分页
     */
    PageResult<SubTableSettingDO> getSubTableSettingPage(SubTableSettingPageReqVO pageReqVO);

    /**
     * 批量插入子表数据
     * @param subTableSettingSaveReqVOS
     */
    void addSubTableSettingList(List<SubTableSettingSaveReqVO> subTableSettingSaveReqVOS);
}