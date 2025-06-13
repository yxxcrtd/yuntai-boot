package com.joyintech.yuntai.module.system.service.dictitem;

import java.util.List;
import javax.validation.*;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.system.controller.admin.dict.vo.data.DictDataSimpleRespVO;
import com.joyintech.yuntai.module.system.controller.admin.dictitem.vo.DictItemPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dictitem.vo.DictItemSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dictitem.DictItemDO;

/**
 * 字典子表 Service 接口
 *
 * @author 兆尹云台
 */
public interface DictItemService {

    /**
     * 创建字典子表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDictItem(@Valid DictItemSaveReqVO createReqVO);

    /**
     * 更新字典子表
     *
     * @param updateReqVO 更新信息
     */
    void updateDictItem(@Valid DictItemSaveReqVO updateReqVO);

    /**
     * 删除字典子表
     *
     * @param id 编号
     */
    void deleteDictItem(String id);

    /**
     * 获得字典子表
     *
     * @param id 编号
     * @return 字典子表
     */
    DictItemDO getDictItem(String id);

    /**
     * 获得字典子表分页
     *
     * @param pageReqVO 分页查询
     * @return 字典子表分页
     */
    PageResult<DictItemDO> getDictItemPage(DictItemPageReqVO pageReqVO);

    List<DictDataSimpleRespVO> getDataList();

    List<DictItemDO> getDictItemByCode(String dictCode);
}