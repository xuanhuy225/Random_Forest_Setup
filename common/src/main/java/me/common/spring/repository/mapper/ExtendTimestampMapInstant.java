package me.common.spring.repository.mapper;

import me.common.helper.MapstructHelper;

import java.sql.Timestamp;
import java.time.Instant;


public interface ExtendTimestampMapInstant {
    default Timestamp map(Instant value) {
        return MapstructHelper.mapToTimestamp(value);
    }

    default Instant map(Timestamp value) {
        return MapstructHelper.mapToInstant(value);
    }
}
