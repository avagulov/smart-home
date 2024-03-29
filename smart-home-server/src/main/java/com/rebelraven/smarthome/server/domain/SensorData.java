package com.rebelraven.smarthome.server.domain;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import java.time.Instant;

@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Introspected
public class SensorData {
    Instant datetime = Instant.now();
    Double value;
}
