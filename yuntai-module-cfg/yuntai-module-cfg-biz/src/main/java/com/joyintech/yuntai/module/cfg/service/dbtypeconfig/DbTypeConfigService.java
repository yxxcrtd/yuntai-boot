package com.joyintech.yuntai.module.cfg.service.dbtypeconfig;


import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnTypeVO;

import java.util.List;

/**
 *
 * @author 兆尹云台
 */
public interface DbTypeConfigService {
    /**
     * 获取可供选择的列类型
     * @param dataSourceId
     * @return
     */
    List<ColumnTypeVO> getColumnTypeList(Long dataSourceId);
}
