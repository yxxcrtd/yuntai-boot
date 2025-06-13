package com.joyintech.yuntai.module.system.api.dict;

import cn.hutool.core.lang.Dict;
import com.joyintech.yuntai.module.system.api.dict.dto.DictDataRespDTO;
import com.joyintech.yuntai.module.system.api.dict.dto.DictTypeRespDTO;

import java.util.List;

/**
 * 字典类型表
 */
public interface DictTypeApi {

    /**
     * 获得指定字典类型的字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictTypeRespDTO> getDictTypeList(List<String> dictType);
}
