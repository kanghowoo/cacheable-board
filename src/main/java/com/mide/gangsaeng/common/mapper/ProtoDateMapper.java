package com.mide.gangsaeng.common.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;

import com.google.protobuf.Timestamp;

@Mapper(
        config = BaseMapper.class,
        uses = { ProtoDateMapper.class }
)
public abstract class ProtoDateMapper {
    public Timestamp map(LocalDateTime time) {
        if (time == null) return null;
        return Timestamp.newBuilder()
                        .setSeconds(time.toEpochSecond(ZoneOffset.UTC))
                        .setNanos(time.getNano())
                        .build();
    }

    public LocalDateTime map(Timestamp timestamp) {
        if (timestamp == null) return null;
        return LocalDateTime.ofEpochSecond(
                timestamp.getSeconds(), timestamp.getNanos(), ZoneOffset.UTC);
    }
}
