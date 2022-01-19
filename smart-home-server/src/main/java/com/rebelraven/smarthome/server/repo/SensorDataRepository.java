package com.rebelraven.smarthome.server.repo;

import io.micronaut.core.annotation.NonNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SensorDataRepository {
    @NonNull
    Publisher<SensorDataEntity> list();

    @NonNull
    Publisher<SensorDataEntity> list(@NonNull Integer houseId, @NonNull Integer sensorId);

    @NonNull
    Mono<SensorDataEntity> latest(@NonNull Integer houseId, @NonNull Integer sensorId);

    @NonNull
    Mono<Boolean> save(@NonNull @NotNull @Valid SensorDataEntity sensorDataEntity);
}
