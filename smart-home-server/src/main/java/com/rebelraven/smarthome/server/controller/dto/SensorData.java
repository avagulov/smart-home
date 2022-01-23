package com.rebelraven.smarthome.server.controller.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

@Value
@AllArgsConstructor
@Introspected
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class SensorData {
    @NonNull
    Integer sensorId;
    @NonNull
    MeasurementData measurementData;
}
