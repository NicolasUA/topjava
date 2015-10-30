package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Nicolas on 30.10.2015.
 */
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        return StringUtils.isEmpty(s) ? null : LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}