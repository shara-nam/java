package com.csdm.newsfeed.mapper;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Includes helper methods which can be used by other mappers
 *
 */
public abstract class MapperUtil {

    private MapperUtil() {

    }

    private static final Logger LOGGER = Logger.getLogger(MapperUtil.class);

    /**
     * Maps from {@link Date} to {@link Timestamp}
     *
     * @param in {@link Date}
     * @return {@link Timestamp}
     */
    public static Timestamp map(final Date in) {
        if (in == null) {
            return null;
        }
        Timestamp timestamp = new Timestamp(in.getTime());

        return timestamp;
    }

    /**
     * Maps from {@link String} to {@link Timestamp}
     *
     * @param in {@link String}
     * @return {@link Timestamp}
     */
    public static Timestamp mapFromString(final String in) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(in);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp;
        } catch(ParseException e) {
            LOGGER.debug("Date could not be parsed!");
        }

        return null;
    }

    /**
     * Maps from {@link Timestamp} to {@link String}
     *
     * @param in {@link Timestamp}
     * @return {@link String}
     */
    public static String mapFromTimestamp(final Timestamp in) {
        Date date = new Date(in.getTime());

        return date.toString();
    }
}