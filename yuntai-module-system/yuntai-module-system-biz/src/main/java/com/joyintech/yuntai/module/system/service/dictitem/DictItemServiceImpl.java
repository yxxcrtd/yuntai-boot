package com.joyintech.yuntai.module.system.service.dictitem;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.system.controller.admin.dict.vo.data.DictDataSimpleRespVO;
import com.joyintech.yuntai.module.system.controller.admin.dictitem.vo.DictItemPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dictitem.vo.DictItemSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dictitem.DictItemDO;
import com.joyintech.yuntai.module.system.dal.mysql.dictitem.DictItemMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.*;

/**
 * 字典子表 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DictItemServiceImpl implements DictItemService {

    @Resource
    private DictItemMapper dictItemMapper;

    @Override
    public String createDictItem(DictItemSaveReqVO createReqVO) {
        // 插入
        DictItemDO dictItem = BeanUtils.toBean(createReqVO, DictItemDO.class);
        dictItemMapper.insert(dictItem);
        // 返回
        return dictItem.getId();
    }

    @Override
    public void updateDictItem(DictItemSaveReqVO updateReqVO) {
        // 校验存在
        validateDictItemExists(updateReqVO.getId());
        // 更新
        DictItemDO updateObj = BeanUtils.toBean(updateReqVO, DictItemDO.class);
        dictItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictItem(String id) {
        // 校验存在
        validateDictItemExists(id);
        // 删除
        dictItemMapper.deleteById(id);
    }

    private void validateDictItemExists(String id) {
        if (dictItemMapper.selectById(id) == null) {
            throw exception(DICT_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public DictItemDO getDictItem(String id) {
        return dictItemMapper.selectById(id);
    }

    @Override
    public PageResult<DictItemDO> getDictItemPage(DictItemPageReqVO pageReqVO) {
        return dictItemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DictDataSimpleRespVO> getDataList() {
        List<DictDataSimpleRespVO> returnList = new ArrayList<>();
        List<DictItemDO> list = dictItemMapper.selectDictItemList();
        if(list!=null && !list.isEmpty()){
            for(DictItemDO item : list){
                DictDataSimpleRespVO vo = new DictDataSimpleRespVO();
                vo.setDictType(item.getDictCode());
                vo.setLabel(item.getItemText());
                vo.setValue(item.getItemValue());
                vo.setSource("new");
                vo.setSort(item.getSortOrder());
                returnList.add(vo);
            }
        }
        return returnList;
    }

    @Override
    public List<DictItemDO> getDictItemByCode(String dictCode) {
        return dictItemMapper.selectList("dict_code", dictCode);
    }

}