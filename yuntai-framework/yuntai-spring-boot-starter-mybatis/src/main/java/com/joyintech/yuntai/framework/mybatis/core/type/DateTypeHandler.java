package com.joyintech.yuntai.framework.mybatis.core.type;

import cn.hutool.core.date.DateUtil;
import com.joyintech.yuntai.framework.common.util.date.DateUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

/**
 * 字符串转日期类型
 *
 * @author 兆尹云台
 */
@MappedJdbcTypes(JdbcType.DATE)
@MappedTypes(String.class)
public class DateTypeHandler implements TypeHandler<String> {

    private static final String COMMA = ",";

    @Override
    public void setParameter(PreparedStatement ps, int i, String strings, JdbcType jdbcType) throws SQLException {
        if (null != strings && !strings.isEmpty()) {
            ps.setDate(i, DateUtil.parse(strings).toSqlDate());
        } else {
            ps.setDate(i, null);
        }
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        Date value = rs.getDate(columnName);
        return getResult(value);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        Date value = rs.getDate(columnIndex);
        return getResult(value);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Date value = cs.getDate(columnIndex);
        return getResult(value);
    }

    private String getResult(Date value) {
        if (value == null) {
            return null;
        }
        return DateUtil.format(new Date(value.getTime()), DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }
}
