package com.joyintech.yuntai.module.cfg.sqlgen.trans;

/**
 * 功能接口函数
 *
 * @author abator 2024/9/27
 */
@FunctionalInterface
public interface ServiceExecutor<T> {

    /**
     * execute中执行的方法，必须开启新的事务，才能切换数据源
     */
    T execute();
}
