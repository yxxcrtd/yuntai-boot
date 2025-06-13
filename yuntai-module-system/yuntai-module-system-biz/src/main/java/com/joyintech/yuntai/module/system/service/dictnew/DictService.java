package com.joyintech.yuntai.module.system.service.dictnew;

import javax.validation.*;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.system.controller.admin.dictnew.vo.DictPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dictnew.vo.DictSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dictnew.DictDO;

/**
 * 字典主表Service 接口
 *
 * @author 兆尹云台
 */
public interface DictService {

    /**
     * 创建字典主表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDict(@Valid DictSaveReqVO createReqVO);

    /**
     * 更新字典主表
     *
     * @param updateReqVO 更新信息
     */
    void updateDict(@Valid DictSaveReqVO updateReqVO);

    /**
     * 删除字典主表
     *
     * @param id 编号
     */
    void deleteDict(String id);

    /**
     * 获得字典主表
     *
     * @param id 编号
     * @return 字典主表
     */
    DictDO getDict(String id);

    /**
     * 获得字典主表分页
     *
     * @param pageReqVO 分页查询
     * @return 字典主表分页
     */
    PageResult<DictDO> getDictPage(DictPageReqVO pageReqVO);

}