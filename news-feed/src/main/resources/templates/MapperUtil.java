package templates;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

/**
 * Includes helper methods which can be used by other mappers
 *
 */
@Mapper
public interface MapperUtil {

    /**
     * Maps from {@link DateTime} to {@link LocalDateTime}
     *
     * @param in {@link DateTime}
     * @return {@link LocalDateTime}
     */
    default LocalDateTime map(final DateTime in) {
        if (in == null) {
            return null;
        }
        return LocalDateTime.of(
                in.getYear(),
                in.getMonthOfYear(),
                in.getDayOfMonth(),
                in.getHourOfDay(),
                in.getMinuteOfHour(),
                in.getSecondOfMinute(),
                in.getMillisOfSecond()
        );
    }

    /**
     * Maps from {@link LocalDateTime} to {@link DateTime}
     *
     * @param in {@link LocalDateTime}
     * @return {@link DateTime}
     */
    default DateTime map(final LocalDateTime in) {
        if (in == null) {
            return null;
        }
        return new DateTime(
                in.getYear(),
                in.getMonthValue(),
                in.getDayOfMonth(),
                in.getHour(),
                in.getMinute(),
                in.getSecond(),
                DateTimeZone.forOffsetHours(0)
        );
    }

}
