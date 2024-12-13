package me.common.spring.utils;

import java.sql.Date;
import java.sql.Timestamp;

public class RepositoryConvertHelper {

    public static Long parseTimestampToLong(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.getTime();
        }
        return null;
    }

    public static Timestamp parseLongToTimestamp(Long timestamp) {
        if (timestamp != null) {
            return new Timestamp(timestamp);
        }
        return null;
    }

    public static Long parseDateToLong(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Date parseLongToDate(Long date) {
        if (date != null) {
            return new Date(date);
        }
        return null;
    }
}
