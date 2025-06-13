package com.joyintech.yuntai.module.cfg.framework.mybatis.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.joyintech.yuntai.framework.common.util.date.DateUtils;
import com.joyintech.yuntai.framework.common.util.spring.SpringUtils;
import com.joyintech.yuntai.module.cfg.service.datasourcechangesql.CfgDatasourceChangeSqlService;
import com.joyintech.yuntai.module.cfg.utils.TableListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
 
/**
 * 基于Mybatis Plus的SQL输出拦截器。
 * 完美的输出打印 SQL 及执行时长、statement。
 * 注意：该插件打印复杂长sql性能消耗只有不到30毫秒，是不是很快
 */
@Slf4j
@Intercepts(value = {
        //prepare方法似乎更加靠后执行，因此获得的sql更加准确（目前用来解决某些情况下比如既有更新又有查询的情况下不打印sql的问题）
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        //更新sql也需要打印
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
//@Component //用于注入到spring中，后续会被mybatis-plus-boot-starter中的代码从spring中取出并注入到mybatis中
public class MybatisPlusPrintSqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //1. 执行sql
        long proceedStart = System.currentTimeMillis();
        Object returnValue = null;
        Exception proceedException = null;
        //执行sql时catch下异常，即使sql语法报错，也要打印完整sql
        try {
            System.out.println("即将执行sql");
            returnValue = invocation.proceed();
        } catch (Exception e) {
            proceedException = e;
        }
        long proceedCost = System.currentTimeMillis() - proceedStart;
        //2.打印sql
        long printBegin = System.currentTimeMillis();
        //MappedStatement这个对象后续要用，这里判断以下避免后面强行getArgs()[0]转MappedStatement失败
        if(!(invocation.getArgs()[0] instanceof MappedStatement)){
            return returnValue;
        }
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //部分mybatisplus拦截器内部可能会对Args中的sql进行修改，因此从Args中获取boundSql更接近与真实执行sql
        BoundSql boundSql = null;
        for (int i = invocation.getArgs().length - 1; i >= 0; i--) {
            if (invocation.getArgs()[i] instanceof BoundSql) {
                boundSql = (BoundSql) invocation.getArgs()[i];
            }
        }
        //routingStatementHandler在责任链中更加靠后，获得的sql更加准确
        if(invocation.getTarget() instanceof RoutingStatementHandler){
            RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler)invocation.getTarget();
            BoundSql boundSql1 = routingStatementHandler.getBoundSql();
            boundSql = boundSql1;
        }
        if (boundSql == null) {
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            boundSql = mappedStatement.getBoundSql(parameter);
        }
        String statement = mappedStatement.getId();
        //过滤包，过滤表
        if (!TableListUtils.containsPackage(statement) || !TableListUtils.containsTable(boundSql.getSql())) return returnValue;
        Configuration configuration = mappedStatement.getConfiguration();
        showSql(configuration, boundSql, proceedCost, statement);
        long printEnd = System.currentTimeMillis();
        System.out.println("本次打印sql消耗时间：" + (printEnd - printBegin));

        //3. sql执行异常的报错扔出去
        if (proceedException != null) {
            throw proceedException;
        }
        return returnValue;
    }
 
    private void showSql(Configuration configuration, BoundSql boundSql, long elapsed, String statement) {
        String logText = formatMessage(elapsed, getSqlWithValues(boundSql.getSql(), buildParameterValues(configuration, boundSql)), statement);
        log.info("\n{}", logText);
    }
 
 
    // com.baomidou.mybatisplus.core.MybatisParameterHandler#setParameters
    private static Map<Integer, Object> buildParameterValues(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        // ParameterMapping描述参数，包括属性、名称、表达式、javaType、jdbcType、typeHandler等信息
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Map<Integer, Object> parameterValues = new HashMap<>();
            //类型处理器用于注册所有的 TypeHandler，并建立 Jdbc 类型、JDBC 类型与 TypeHandler 之间的对应关系
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    parameterValues.put(i, new Value(value));
                }
            }
            return parameterValues;
        }
        return Collections.emptyMap();
    }
 
    public static String formatMessage( long elapsed, String sql, String statement) {
        CfgDatasourceChangeSqlService cfgDatasourceChangeSqlService = SpringUtils.getBean(CfgDatasourceChangeSqlService.class);
        if (cfgDatasourceChangeSqlService != null) {
            cfgDatasourceChangeSqlService.save(sql, System.currentTimeMillis(),null);
        }
        return StringUtils.isNotBlank(sql) ?
//                " Consume Time：" + elapsed + " ms "  + " (" + statement + ")" + "  Execute SQL：" + sql.replaceAll("[\\s]+", " ")
                //" Consume Time：" + elapsed + " ms ,  Execute SQL：" + sql.replaceAll("[\\s]+", " ")
//                " Consume Time：" + elapsed + " ms ,  Execute SQL：" + sql
                " 执行sql消耗时间：" + elapsed + " ms "  + " (" + statement + ")" + "  执行sql打印：" + sql
                : "";
    }
 
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
 
    @Override
    public void setProperties(Properties properties0) {
    }
 
    public static String getSqlWithValues(String statementQuery, Map<Integer, Object> parameterValues) {
        final StringBuilder sb = new StringBuilder();
        // iterate over the characters in the query replacing the parameter placeholders
        // with the actual values
        int currentParameter = 0;
        for (int pos = 0; pos < statementQuery.length(); pos++) {
            char character = statementQuery.charAt(pos);
            if (statementQuery.charAt(pos) == '?' && currentParameter <= parameterValues.size()) {
                // replace with parameter value
                Object value = parameterValues.get(currentParameter);
                sb.append(value != null ? value.toString() : new Value().toString());
                currentParameter++;
            } else {
                sb.append(character);
            }
        }
        return sb.toString();
    }
 
    /**
     * 基于p6spy的简易数据类型转换类。
     *
     * @author laiqi
     * @date 2023-4-4
     */
    public static class Value {
        public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
 
        public static final String databaseDialectDateFormat = NORM_DATETIME_PATTERN;
        public static final String databaseDialectTimestampFormat = NORM_DATETIME_PATTERN;
 
        public static final String databaseDialectBooleanFormat = "numeric";
 
        private Object value;
 
        public Value(Object valueToSet) {
            this();
            this.value = valueToSet;
        }
 
        public Value() {
        }
 
        public Object getValue() {
            return value;
        }
 
        public void setValue(Object value) {
            this.value = value;
        }
 
        @Override
        public String toString() {
            return convertToString(this.value);
        }
 
        public String convertToString(Object value) {
            String result;
            if (value == null) {
                result = "NULL";
            } else {
                if (value instanceof byte[]) {
                    result = new String((byte[]) value);
                    result = quoteIfNeeded(result, value);
                } else if (value instanceof Timestamp) {
                    result = ((Timestamp)value).toLocalDateTime().format(DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
                    result = formatTimestamp(result);
                } else if (value instanceof Date) {
                    result = new SimpleDateFormat(databaseDialectDateFormat).format(value);
                    result = formatDateTime(result);
                } else if (value instanceof LocalDate) {
                    result = ((LocalDate)value).format(DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY)) ;
                    result = formatDateTime(result);
                }else if(value instanceof LocalDateTime) {
                    result = ((LocalDateTime)value).format(DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
                    result = formatTimestamp(result);
                } else if (value instanceof Boolean) {
                    result = Boolean.FALSE.equals(value) ? "0" : "1";
                    result = quoteIfNeeded(result, value);
                } else {
                    result = value.toString();
                    result = quoteIfNeeded(result, value);
                }
            }
            return result;
        }
 
        private String quoteIfNeeded(String stringValue, Object obj) {
            if (stringValue == null) {
                return null;
            }
            if (Number.class.isAssignableFrom(obj.getClass()) || Boolean.class.isAssignableFrom(obj.getClass())) {
                return stringValue;
            } else {
                return "'" + escape(stringValue) + "'";
            }
        }
        private String escape(String stringValue) {
            return stringValue.replaceAll("'", "''");
        }
 
    }

    private static String formatTimestamp(String field) {
        return String.format("to_timestamp('%s','yyyy-mm-dd hh24:mi:ss')", field);
    }
    private static String formatDate(String field) {
        return String.format("to_date('%s','yyyy-mm-dd')", field);
    }
    private static String formatDateTime(String field) {
        return String.format("to_date('%s','yyyy-mm-dd hh24:mi:ss')", field);
    }
}