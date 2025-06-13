package com.joyintech.yuntai.module.system.api.dict.dto;

import com.joyintech.yuntai.framework.common.enums.CommonStatusEnum;
import lombok.Data;

@Data
public class DictTypeRespDTO {

    /**
     * 字典值
     */
    private String name;
    /**
     * 字典类型
     */
    private String type;
    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
}
