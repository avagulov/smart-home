package com.rebelraven.smarthome.server.controller.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import java.time.Instant;

@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Introspected
public class MeasurementData {
    Instant datetime;
    Double value;

    public MeasurementData(@NonNull Double value){
        this.datetime = Instant.now();
        this.value = value;
    }
}
