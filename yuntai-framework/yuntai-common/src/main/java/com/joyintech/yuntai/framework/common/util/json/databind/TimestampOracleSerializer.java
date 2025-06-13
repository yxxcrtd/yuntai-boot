package com.joyintech.yuntai.framework.common.util.json.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.joyintech.yuntai.framework.common.util.date.DateUtils;
import oracle.sql.TIMESTAMP;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaofu
 * @description LocalDate 时间类型装换
 * @date 2020/9/1 17:25
 */
public class TimestampOracleSerializer extends JsonSerializer<TIMESTAMP> {
    @Override
    public void serialize(TIMESTAMP value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            gen.writeString(value.toLocalDateTime().format(DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
