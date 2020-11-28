package com.nettm.exercise.base.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@UtilityClass
public class DateUtil {

    public LocalDate convertToLocalDate(Date dateToConvert) {
        if (Objects.isNull(dateToConvert)) {
            return null;
        }
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        if (Objects.isNull(dateToConvert)) {
            return null;
        }
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public Date convertToDate(LocalDate dateToConvert) {
        if (Objects.isNull(dateToConvert)) {
            return null;
        }
        return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date convertToDate(LocalDateTime dateToConvert) {
        if (Objects.isNull(dateToConvert)) {
            return null;
        }
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalDate convertToLocalDate(Long dateToConvert) {
        if (Objects.isNull(dateToConvert)) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateToConvert), TimeZone.getDefault().toZoneId()).toLocalDate();
    }

    public LocalDateTime convertToLocalDateTime(Long dateToConvert) {
        if (Objects.isNull(dateToConvert)) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateToConvert), TimeZone.getDefault().toZoneId());
    }

}
