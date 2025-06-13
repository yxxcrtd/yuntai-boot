package com.joyintech.yuntai.module.system.service.dict;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.date.LocalDateTimeUtils;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.system.controller.admin.dict.vo.data.DictDataRespVO;
import com.joyintech.yuntai.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dict.vo.type.DictTypeRespVO;
import com.joyintech.yuntai.module.system.controller.admin.dict.vo.type.DictTypeSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dict.DictDataDO;
import com.joyintech.yuntai.module.system.dal.dataobject.dict.DictTypeDO;
import com.joyintech.yuntai.module.system.dal.mysql.dict.DictTypeMapper;
import com.google.common.annotations.VisibleForTesting;
import com.joyintech.yuntai.module.system.dal.mysql.dictnew.DictMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.*;

/**
 * 字典类型 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Resource
    private DictDataService dictDataService;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictMapper dictMapper;

    @Override
    public PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO pageReqVO) {
        return dictTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<DictTypeDO> getDictTypePage2(DictTypePageReqVO pageReqVO) {
        Page<DictTypeDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        IPage<DictTypeDO> resultPage = dictMapper.selectPageUnion(page, pageReqVO);
        List<DictTypeDO> list = resultPage.getRecords();
        if(list!=null && !list.isEmpty()){
            for(DictTypeDO dict : list){
                dict.setType(dict.getType().replaceAll(" ", ""));
                dict.setName(dict.getName().replaceAll(" ", ""));
            }
        }
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }

    @Override
    public DictTypeDO getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public DictTypeDO getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }

    @Override
    public Long createDictType(DictTypeSaveReqVO createReqVO) {
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(null, createReqVO.getName());
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(null, createReqVO.getType());

        // 插入字典类型
        DictTypeDO dictType = BeanUtils.toBean(createReqVO, DictTypeDO.class);
        dictType.setDeletedTime(LocalDateTimeUtils.EMPTY); // 唯一索引，避免 null 值
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    public void updateDictType(DictTypeSaveReqVO updateReqVO) {
        // 校验自己存在
        validateDictTypeExists(updateReqVO.getId());
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(updateReqVO.getId(), updateReqVO.getType());

        // 更新字典类型
        DictTypeDO updateObj = BeanUtils.toBean(updateReqVO, DictTypeDO.class);
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictType(Long id) {
        // 校验是否存在
        DictTypeDO dictType = validateDictTypeExists(id);
        // 校验是否有字典数据
        if (dictDataService.getDictDataCountByDictType(dictType.getType()) > 0) {
            throw exception(DICT_TYPE_HAS_CHILDREN);
        }
        // 删除字典类型
        dictTypeMapper.updateToDelete(id, LocalDateTime.now());
    }

    @Override
    public List<DictTypeDO> getDictTypeList() {
        return dictTypeMapper.selectList();
    }

    @Override
    public List<DictTypeDO> getDictTypeLists(List<String> dictType) {
        List<DictTypeDO> list = dictMapper.selectListNew(dictType);
        if(list!=null && !list.isEmpty()){
            for(DictTypeDO dict : list){
                dict.setType(dict.getType().replaceAll(" ", ""));
                dict.setName(dict.getName().replaceAll(" ", ""));
            }
        }
        return list;
    }

    @VisibleForTesting
    void validateDictTypeNameUnique(Long id, String name) {
        DictTypeDO dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
    }

    @VisibleForTesting
    void validateDictTypeUnique(Long id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        DictTypeDO dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    @VisibleForTesting
    DictTypeDO validateDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        DictTypeDO dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }

    /**
     * 获得字典类型详情
     *
     * @param type 字典类型
     * @return 字典类型
     */
    @Override
    public DictTypeRespVO getDictTypeByType(String type) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectByType(type);
        if(dictTypeDO!=null){
            DictTypeRespVO dictTypeRespVO = BeanUtils.toBean(dictTypeDO, DictTypeRespVO.class);
            List<DictDataDO> list = dictDataService.getDictDataListByDictType(type);
            if(list!=null && !list.isEmpty()){
                dictTypeRespVO.setDictDataList(BeanUtils.toBean(list, DictDataRespVO.class));
            }
            return dictTypeRespVO;
        }
        return null;
    }

}
