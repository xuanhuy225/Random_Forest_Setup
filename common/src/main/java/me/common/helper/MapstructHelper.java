package me.common.helper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class MapstructHelper {
    private static final ZoneId DEFAULT_ZONE_TIME = ZoneId.systemDefault();

    public static ZonedDateTime mapToZonedDateTime(Instant value) {
        if (value != null) {
            return value.atZone(DEFAULT_ZONE_TIME);
        }
        return null;
    }
    public static Instant mapToInstant(ZonedDateTime value) {
        if (value != null) {
            return value.toInstant();
        }
        return null;
    }

    public static Timestamp mapToTimestamp(Instant value) {
        if (value != null) {
            return new Timestamp(value.toEpochMilli());
        }
        return null;
    }
    public static Instant mapToInstant(Timestamp value) {
        if (value != null) {
            return value.toInstant();
        }
        return null;
    }
}
