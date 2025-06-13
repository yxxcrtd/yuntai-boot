package com.joyintech.yuntai.module.system.api.dict;

import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.system.api.dict.dto.DictDataRespDTO;
import com.joyintech.yuntai.module.system.api.dict.dto.DictTypeRespDTO;
import com.joyintech.yuntai.module.system.dal.dataobject.dict.DictDataDO;
import com.joyintech.yuntai.module.system.dal.dataobject.dict.DictTypeDO;
import com.joyintech.yuntai.module.system.service.dict.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictTypeApiImpl implements DictTypeApi {

    @Resource
    private DictTypeService dictTypeService;

    @Override
    public List<DictTypeRespDTO> getDictTypeList(List<String> dictType) {
        List<DictTypeDO> list = dictTypeService.getDictTypeLists(dictType);
        return BeanUtils.toBean(list, DictTypeRespDTO.class);
    }
}
