package com.jointcorp.jcdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class LocalDateConverAdapter {

    @Bean
    public Converter<String, LocalDate> dateConvert() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if(source.contains(".")) {
                    return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                }
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }

}
