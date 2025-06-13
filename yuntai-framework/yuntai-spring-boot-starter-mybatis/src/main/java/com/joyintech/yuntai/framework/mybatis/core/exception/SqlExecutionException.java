package com.joyintech.yuntai.framework.mybatis.core.exception;

import java.util.Optional;

import com.joyintech.yuntai.framework.mybatis.core.enums.WithCodeMsgEnum;
import com.joyintech.yuntai.framework.mybatis.core.enums.WithCodeMsgFormatEnum;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * SQL 执行异常类
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/18 0018 下午 3:45
 */
@Getter
@Slf4j
public class SqlExecutionException extends RuntimeException {

    private final WithCodeMsgEnum type;

    public SqlExecutionException(String msg) {
        this(msg, null);
    }

    public SqlExecutionException(String msg, Throwable e) {
        this(new WithCodeMsgEnum() {
            @Override
            public String getCode() {
                return "";
            }

            @Override
            public String getMsg() {
                return msg;
            }
        }, e);
    }

    public SqlExecutionException(WithCodeMsgEnum type, Throwable e) {
        super(type.getErrorMsg(), e);
        this.type = type;
        //这里打印异常堆栈消息
        Optional.ofNullable(e).ifPresent(i -> log.error("SQL执行异常, 异常堆栈为:", e));
    }

    public SqlExecutionException(WithCodeMsgFormatEnum type, Object... arguments) {
        this(type.format(arguments), null);
    }

    public SqlExecutionException(WithCodeMsgFormatEnum type, Throwable e, Object... arguments) {
        this(type.format(arguments), e);
    }
}
