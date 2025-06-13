package com.joyintech.yuntai.module.cfg.sqlgen.trans;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

/**
 * 动态数据源执行辅助类
 *
 * @author abator 2024/9/27
 */
public class DynamicDataSourceExecuteHelper {

    public static <T> T executeWithDataSource(Long dataSourceId, ServiceExecutor<T> executor) {
        // 主数据源id默认为 0
        if (dataSourceId == 0) {
            return executor.execute();
        }
        // 获取数据源名称
        try {
            // 切换数据源
            DynamicDataSourceContextHolder.push(String.valueOf(dataSourceId));
            // 执行外部传入的 service 类的方法
            return executor.execute();
        } finally {
            // 清除当前数据源
            DynamicDataSourceContextHolder.clear();
        }
    }
}
