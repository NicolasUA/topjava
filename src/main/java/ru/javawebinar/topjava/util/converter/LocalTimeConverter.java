package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Nicolas on 30.10.2015.
 */
public class LocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String s) {
        return StringUtils.isEmpty(s) ? null : LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}