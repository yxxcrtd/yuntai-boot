package com.joyintech.yuntai.framework.mybatis.core.enums;

import com.joyintech.yuntai.framework.common.exception.enums.GlobalErrorCodeConstants;

/**
 * 功能列表解析器服务的消息定义
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/24 0024 上午 9:17
 */
public enum SqlExecutionExceptionEnum implements WithCodeMsgFormatEnum {

    SQL_EXECUTE_FAIL(GlobalErrorCodeConstants.ERROR_CONFIGURATION.getCode().toString(),
                     "SQL执行出错, 出错的SQL为:【{0}】, 异常的堆栈为:【{1}】") {
        @Override
        public String getMsg() {
            return "SQL执行出错!";
        }
    },
    SQL_BUILD_FAIL(GlobalErrorCodeConstants.ERROR_CONFIGURATION.getCode().toString(),
                   "SQL构建出错, 出错的SQL为:【{0}】, 异常的堆栈为:【{1}】") {
        @Override
        public String getMsg() {
            return "SQL构建出错!请检查是否有特殊符号";
        }
    },
    ;

    public final String code;

    public final String format;

    public String errorMsg;

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.errorMsg;
    }

    SqlExecutionExceptionEnum(String code, String format) {
        this.code = code;
        this.format = format;
    }

}
