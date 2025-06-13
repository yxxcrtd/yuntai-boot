package com.joyintech.yuntai.module.cfg.service.sqlgen;

import java.util.List;

/**
 * 执行ddl Service 接口
 *
 * @author 兆尹云台
 */
public interface CommonDdlService {

    /**
     * 注意：ddl语句在大多数数据库中都无法rollback
     * 添加@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)作用是切换数据源
     */
    void executeSql(List<String> sql);
}
