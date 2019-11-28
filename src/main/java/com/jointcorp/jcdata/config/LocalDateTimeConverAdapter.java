package com.jointcorp.jcdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LocalDateTimeConverAdapter {

    @Bean
    public Converter<String, LocalDateTime> dateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if(source.contains("-")) {
                    return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
            }
        };
    }


}
