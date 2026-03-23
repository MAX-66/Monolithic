package com.brenden.config;

import com.brenden.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-24 03:38
 */

@Configuration
public class JacksonConfig {

    @ConditionalOnMissingBean(ObjectMapper.class)
    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new JsonMapper();
        // 反序列化: JSON 字段中有Java对象中没有不报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁用默认的多态类型处理
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 序列化: 排除值为 null 的对象
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateUtil.DATE_TIME_FORMATTER;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_TIME_PATTERN);
        simpleDateFormat.setTimeZone(timeZone);
        mapper.setDateFormat(simpleDateFormat);
        mapper.setTimeZone(timeZone);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        mapper.registerModule(javaTimeModule);
        return mapper;
    }
}
