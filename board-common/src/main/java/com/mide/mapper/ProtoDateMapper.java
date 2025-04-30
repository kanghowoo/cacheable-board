package com.mide.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.mapstruct.Mapper;

import com.google.protobuf.Timestamp;

@Mapper(
        config = BaseMapper.class
)
public abstract class ProtoDateMapper {

    private static final ZoneOffset KST_OFFSET =
            ZonedDateTime.now(ZoneId.of("Asia/Seoul")).getOffset();

    public Timestamp map(LocalDateTime time) {
        if (time == null) return null;
        return Timestamp.newBuilder()
                        .setSeconds(time.toEpochSecond(KST_OFFSET))
                        .setNanos(time.getNano())
                        .build();
    }

    public LocalDateTime map(Timestamp timestamp) {
        if (timestamp == null) return null;
        return LocalDateTime.ofEpochSecond(
                timestamp.getSeconds(), timestamp.getNanos(), KST_OFFSET);
    }
}
