package com.joyintech.yuntai.framework.mybatis.core.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.joyintech.yuntai.framework.mybatis.core.enums.SqlExecutionExceptionEnum;
import com.joyintech.yuntai.framework.mybatis.core.exception.SqlExecutionException;

import lombok.extern.slf4j.Slf4j;

/**
 * TpAppServiceImpl(功能列表解析器应用)
 * <br/>
 *
 * @author pengzhen
 * @date 2019/10/29 0029 下午 3:55
 */
@Component
@Slf4j
public class DBDynamicSqlExecutorUtils {

    private final SqlSession sqlSession;

    @Autowired
    public DBDynamicSqlExecutorUtils(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Map<String, Object> findOneData(String sql, Map<String, Object> parameterMap) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findOneData";
            String statementId = namespace + "." + method;
            log.info("SQL执行语句： ========》" + sql + "【" + JSON.toJSONString(parameterMap) + "】");
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null, false);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                             TableInfoHelper.getTableInfo(HashMap.class));
            Map<String, Object> oneMap = sqlSession.selectOne(statementId, parameterMap);
            if (oneMap == null) {
                return new HashMap<>();
            }
            Map<String, Object> upperKeyMap = new HashMap<>();
            oneMap.forEach((k, v) -> {
                upperKeyMap.put(k, v);
            });
            return upperKeyMap;
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils.findOneData exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public <K> K findOneData(String sql, Map<String, Object> parameterMap, String resultMap, Class<K> resultClass) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findOneDataWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement =
                    new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, resultMap, false);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             resultClass,
                             TableInfoHelper.getTableInfo(HashMap.class));

            return sqlSession.selectOne(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils.findOneData exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public Map<String, Object> findOneDataWithParse(String sql, Map<String, Object> parameterMap) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findOneDataWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                             TableInfoHelper.getTableInfo(HashMap.class));
            Map<String, Object> oneMap = sqlSession.selectOne(statementId, parameterMap);
            if (oneMap == null) {
                return new HashMap<>();
            }
            Map<String, Object> upperKeyMap = new HashMap<>();
            oneMap.forEach((k, v) -> {
                if(v instanceof Clob){
                    String str = clobToString((Clob)v);
                    upperKeyMap.put(k, str);
                } else {
                    upperKeyMap.put(k, v);
                }
            });
            return upperKeyMap;
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils.findOneDataWithParse exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    private String clobToString(Clob clob){
        StringBuilder clobContent = new StringBuilder();
        if (clob != null) {
            try (Reader reader = clob.getCharacterStream();
                BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    clobContent.append(line);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return clobContent.toString();
    }

    public <K> K findOneDataWithParse(String sql, Map<String, Object> parameterMap, String resultMap,
                                      Class<K> resultClass) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findOneDataWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement =
                    new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, resultMap, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             resultClass,
                             TableInfoHelper.getTableInfo(HashMap.class));

            return sqlSession.selectOne(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils.findOneDataWithParse exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public List<Map<String, Object>> findListData(String sql, Map<String, Object> parameterMap) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findListData";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null, false);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                             TableInfoHelper.getTableInfo(HashMap.class));
            List<Map<String, Object>> mapList = sqlSession.selectList(statementId, parameterMap);
            return mapList.stream().map(map -> {
                Map<String, Object> upperKeyMap = new HashMap<>();
                map = Optional.ofNullable(map).orElse(new HashMap<>());
                map.forEach((k, v) -> {
                    upperKeyMap.put(StringUtils.upperCase(k), v);
                });
                return upperKeyMap;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils.findListData exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public <K> List<K> findListData(String sql, Map<String, Object> parameterMap, String resultMap,
                                    Class<K> resultClass) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findListDataWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement =
                    new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, resultMap, false);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             resultClass,
                             TableInfoHelper.getTableInfo(HashMap.class));

            return sqlSession.selectList(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils.findListData exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public List<Map<String, Object>> findListDataWithParse(String sql, Map<String, Object> parameterMap) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findListDataWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                             TableInfoHelper.getTableInfo(HashMap.class));
            log.info("SQL执行语句： ========》" + sql + "【" + JSON.toJSONString(parameterMap) + "】");

            List<Map<String, Object>> mapList = sqlSession.selectList(statementId, parameterMap);
            log.info("结果返回： {}", JSON.toJSONString(mapList));
            if (mapList == null) {
                return new ArrayList<>();
            }else{
                mapList.forEach(s -> {
                    s.forEach((k, v) -> {
                        if(v instanceof Clob){
                            String str = clobToString((Clob)v);
                            s.put(k, str);
                        } else {
                            s.put(k, v);
                        }
                    });
                });
            }
            return mapList.stream().filter(Objects::nonNull).map(map -> {
                Map<String, Object> upperKeyMap = new HashMap<>();
                map.forEach((k, v) -> {
                    upperKeyMap.put(k, v);
                });
                return upperKeyMap;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils.findListDataWithParse exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public List<Map<String, Object>> findListDataNoUpper(String sql, Map<String, Object> parameterMap) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findListDataWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                             TableInfoHelper.getTableInfo(HashMap.class));
            log.info("SQL执行语句： ========》" + sql + "【" + JSON.toJSONString(parameterMap) + "】");

            boolean b = checkParamsInMap(sql, parameterMap);
            List<Map<String, Object>> mapList = null;
            if(b){
                mapList = sqlSession.selectList(statementId, parameterMap);
            }
            log.info("结果返回： {}", JSON.toJSONString(mapList));
            if (mapList == null) {
                return new ArrayList<>();
            } else {
                mapList.forEach(s -> {
                    s.forEach((k, v) -> {
                        if(v instanceof Clob){
                            String str = clobToString((Clob)v);
                            s.put(k, str);
                        } else {
                            s.put(k, v);
                        }
                    });
                });
            }
            return mapList;
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public boolean checkParamsInMap(String sql,Map<String, Object> paramMap){
        Pattern pattern = Pattern.compile("<foreach.*?collection=\"(.*?)\".*?>");
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            String paramName = matcher.group(1);
            if (!paramMap.containsKey(paramName)) {
                return false;
            }
        }
        return true;
    }

    public <K> List<K> findListDataWithParse(String sql, Map<String, Object> parameterMap, String resultMap,
                                             Class<K> resultClass) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findListDataWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement =
                    new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, resultMap, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             resultClass,
                             TableInfoHelper.getTableInfo(HashMap.class));

            return sqlSession.selectList(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public Object insert(String sql, Map<String, Object> parameterMap) {
        Object count = null;
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".insert";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.INSERT, method, namespace, null, false);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                    TableInfoHelper.getTableInfo(HashMap.class));
            sqlSession.insert(statementId, parameterMap);
            // 指定前缀返回新增主键, // TODO：暂时因时间问题，只做oracle的修改，不考虑兼容性
            for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                String key = entry.getKey();

                // 检查键是否以指定前缀开头
                if (key.startsWith("ID") || key.startsWith("id")) {
                    count = entry.getValue();
                }
            }
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
        return count;
    }

    public int update(String sql, Map<String, Object> parameterMap) {
        int num;
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".update";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.UPDATE, method, namespace, null, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                             TableInfoHelper.getTableInfo(HashMap.class));
            log.info("SQL执行语句update： ========》" + sql + "【" + JSON.toJSONString(parameterMap) + "】");
            num = sqlSession.update(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
        return num;
    }

    public void delete(String sql, Map<String, Object> parameterMap) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".delete";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.DELETE, method, namespace, null, false);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                             TableInfoHelper.getTableInfo(HashMap.class));
            sqlSession.delete(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    public void deleteWithParse(String sql, Map<String, Object> parameterMap) {
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".deleteWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.DELETE, method, namespace, null, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                             String.class,
                             String.class,
                             TableInfoHelper.getTableInfo(HashMap.class));
            sqlSession.delete(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    /**
     * 删除全部
     *
     * @author K 2019-7-9
     */
    public static class MappedStatement extends AbstractMethod {

        public static final String ID_PREFIX = "MlExecute";

        public static final String NAMESPACE = "MlNamespace";

        private String sql;

        private final String id;

        private final String namespace;

        private final String resultMap;

        private final SqlCommandType sqlCommandType;

        private final Boolean parse;

        public MappedStatement(String sql, SqlCommandType sqlCommandType, String id, String namespace, String resultMap,
                               Boolean parse) {
            super(id);
            this.sql = sql;
            this.sqlCommandType = sqlCommandType;
            this.id = id;
            this.namespace = namespace;
            this.resultMap = resultMap;
            this.parse = parse;
        }

        @Override
        public org.apache.ibatis.mapping.MappedStatement injectMappedStatement(Class<?> mapperClass,
                                                                               Class<?> modelClass,
                                                                               TableInfo tableInfo) {
            org.apache.ibatis.mapping.MappedStatement mappedStatement = null;
            try {

                Field mappedStatementsField = Configuration.class.getDeclaredField("mappedStatements");
                mappedStatementsField.setAccessible(true);
                Map<String, org.apache.ibatis.mapping.MappedStatement> mappedStatements =
                        (Map<String, org.apache.ibatis.mapping.MappedStatement>) mappedStatementsField.get(super.configuration);
                if (!mappedStatements.containsKey(this.namespace + "." + id)) {
                    super.builderAssistant.setCurrentNamespace(this.namespace);
                    SqlSource sqlSource = getSqlSource();
                    switch (this.sqlCommandType) {
                        case SELECT:
                            mappedStatement = addMappedStatement(mapperClass,
                                                                 id,
                                                                 sqlSource,
                                                                 SqlCommandType.SELECT,
                                                                 HashMap.class,
                                                                 resultMap,
                                                                 HashMap.class,
                                                                 new NoKeyGenerator(),
                                                                 null,
                                                                 null);
                            break;
                        case INSERT:
                            mappedStatement = addMappedStatement(mapperClass,
                                                                 id,
                                                                 sqlSource,
                                                                 SqlCommandType.INSERT,
                                                                 HashMap.class,
                                                                 null,
                                                                 Integer.class,
                                                                 new NoKeyGenerator(),
                                                                 null,
                                                                 null);
                            break;
                        case UPDATE:
                            mappedStatement = addMappedStatement(mapperClass,
                                                                 id,
                                                                 sqlSource,
                                                                 SqlCommandType.UPDATE,
                                                                 HashMap.class,
                                                                 null,
                                                                 Integer.class,
                                                                 new NoKeyGenerator(),
                                                                 null,
                                                                 null);
                            break;
                        case DELETE:
                            mappedStatement = addMappedStatement(mapperClass,
                                                                 id,
                                                                 sqlSource,
                                                                 SqlCommandType.DELETE,
                                                                 HashMap.class,
                                                                 null,
                                                                 Integer.class,
                                                                 new NoKeyGenerator(),
                                                                 null,
                                                                 null);
                            break;
                    }
                    mappedStatements.remove(mappedStatement.getId());
                    mappedStatements.put(mappedStatement.getId(), mappedStatement);
                }
            } catch (Exception e) {
                log.error("DynamicSqlExecutorUtils.injectMappedStatement error", e);
                throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_BUILD_FAIL.format(sql, e), e);
            }
            return mappedStatement;
        }

        private SqlSource getSqlSource() {
            if (this.parse) {
                this.sql = "<script>" + this.sql + "</script>";
                XPathParser parser =
                        new XPathParser(sql, false, super.configuration.getVariables(), new XMLMapperEntityResolver());
                return super.languageDriver.createSqlSource(super.configuration,
                                                            parser.evalNode("/script"),
                                                            HashMap.class);
            } else {
                return super.languageDriver.createSqlSource(super.configuration, this.sql, HashMap.class);
            }
        }
    }

    /**
     * 附件列表
     *
     * @param parentId 主表id
     * @return
     */
    public List<Map<String, Object>> findAttachmentList(List<String> parentId) {
        String sql = "select id, name as fileName, url as fileUrl, attachment_type as attachmentType, upload_user as uploadUser, upload_time as uploadTime, remark " +
                "from infra_file where deleted=0 and parent_id = '"+parentId.get(0)+"'";
        try {
            String method = "hash" + sql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".findListDataWithParse";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                    String.class,
                    String.class,
                    TableInfoHelper.getTableInfo(HashMap.class));
            log.info("SQL执行语句： ========》" + sql);

            List<Map<String, Object>> mapList = sqlSession.selectList(statementId);
            log.info("结果返回： {}", JSON.toJSONString(mapList));
            if (mapList == null) {
                return new ArrayList<>();
            }
            return mapList;
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
        }
    }

    /**
     * 删除附件
     *
     * @param parentId 主表id
     * @param idList 附件id
     * @return
     */
    public int delAttachmentList(String parentId, List<String> idList) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("parentId", parentId);
        String delSql = "update infra_file set deleted=1 where parent_id=#{parentId} ";

        if(idList!=null && !idList.isEmpty()){
            delSql = "update infra_file set deleted=1 where parent_id=#{parentId} and id not in  <foreach item=\"it\" collection=\"idList\" open=\"(\" close=\") \" separator=\",\" >#{it}</foreach>";
            parameterMap.put("idList", idList);
        }

        int num;
        try {
            String method = "hash" + delSql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".update";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(delSql, SqlCommandType.UPDATE, method, namespace, null, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                    String.class,
                    String.class,
                    TableInfoHelper.getTableInfo(HashMap.class));
            log.info("SQL执行语句update： ========》" + delSql + "【" + JSON.toJSONString(parameterMap) + "】");
            num = sqlSession.update(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(delSql, e), e);
        }
        return num;
    }

    /**
     * 把主表id，反更新进附件表
     *
     * @param parentId 主表id
     * @param idList 附件表id
     * @return
     */
    public int updateAttachmentList(String parentId, List<String> idList) {
        String addSql = "update infra_file set parent_id=#{parentId} where id in  <foreach item=\"it\" collection=\"idList\" open=\"(\" close=\") \" separator=\",\" >#{it}</foreach>";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("parentId", parentId);
        parameterMap.put("idList", idList);

        int num;
        try {
            String method = "hash" + addSql.hashCode();
            String namespace = MappedStatement.NAMESPACE + ".update";
            String statementId = namespace + "." + method;
            MappedStatement statement = new MappedStatement(addSql, SqlCommandType.UPDATE, method, namespace, null, true);
            statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()),
                    String.class,
                    String.class,
                    TableInfoHelper.getTableInfo(HashMap.class));
            log.info("SQL执行语句update： ========》" + addSql + "【" + JSON.toJSONString(parameterMap) + "】");
            num = sqlSession.update(statementId, parameterMap);
        } catch (Exception e) {
            log.error("DBDynamicSqlExecutorUtils exception", e);
            throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(addSql, e), e);
        }
        return num;
    }

}
