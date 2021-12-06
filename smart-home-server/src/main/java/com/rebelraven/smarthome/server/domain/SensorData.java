package com.rebelraven.smarthome.server.domain;

import lombok.*;

import java.time.Instant;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SensorData {
    Instant datetime = Instant.now();
    double value;
}
