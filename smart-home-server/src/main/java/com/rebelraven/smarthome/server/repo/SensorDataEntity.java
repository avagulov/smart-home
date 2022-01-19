package com.rebelraven.smarthome.server.repo;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Introspected
@Getter
public class SensorDataEntity {
    @NonNull
    @NotBlank
    @BsonProperty("house-id")
    private final Integer houseId;

    @NonNull
    @NotBlank
    @BsonProperty("sensor-id")
    private final Integer sensorId;

    @NonNull
    @NotBlank
    @BsonProperty("value")
    private final Double value;

    @NonNull
    @NotBlank
    @BsonProperty("datetime")
    private final Instant datetime;

    @Creator
    @BsonCreator
    public SensorDataEntity(
            @NonNull @BsonProperty("house-id") Integer houseId,
            @NonNull @BsonProperty("sensor-id") Integer sensorId,
            @NonNull @BsonProperty("value") Double value,
            @NonNull @BsonProperty("datetime") Instant datetime
    ) {
        this.houseId = houseId;
        this.sensorId = sensorId;
        this.value = value;
        this.datetime = datetime;
    }
}
