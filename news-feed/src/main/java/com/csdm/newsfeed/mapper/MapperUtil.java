package com.csdm.newsfeed.mapper;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

/**
 * Includes helper methods which can be used by other mappers
 *
 */
@Mapper
public interface MapperUtil {

    /**
     * Maps from {@link DateTime} to {@link String}
     *
     * @param in {@link DateTime}
     * @return {@link String}
     */
    default String map(final DateTime in) {
        if (in == null) {
            return null;
        }
        DateTime dateTime = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String resultWithoutTimezone = formatter.print(dateTime);

        return resultWithoutTimezone;
    }

    /**
     * Maps from {@link String} to {@link DateTime}
     *
     * @param in {@link String}
     * @return {@link DateTime}
     */
    default DateTime map(final String in) {
        if (StringUtils.isEmpty(in)) {
            return null;
        }
        DateTime dateTime = new DateTime(in);

        return dateTime;
    }
}