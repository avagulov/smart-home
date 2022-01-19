package com.rebelraven.smarthome.server.controller;

import com.rebelraven.smarthome.server.controller.dto.SensorData;
import com.rebelraven.smarthome.server.repo.SensorDataRepository;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/v1/telemetry/sensor-data")
public class SensorDataController {
    private final SensorDataRepository sensorDataRepository;
    private final SensorDataMapper sensorDataMapper;

    @Inject
    public SensorDataController(
            SensorDataRepository sensorDataRepository,
            SensorDataMapper sensorDataMapper) {
        this.sensorDataRepository = sensorDataRepository;
        this.sensorDataMapper = sensorDataMapper;
    }

    @Get(uri = "{houseId}/{sensorId}", produces = MediaType.APPLICATION_JSON)
    public Publisher<SensorData> getAll(@Parameter Integer houseId, @Parameter Integer sensorId) {
        return Flux
                .merge(sensorDataRepository.list(houseId, sensorId))
                .map(sensorDataMapper::toSensorData);
    }

    @Get(uri = "{houseId}/{sensorId}/latest", produces = MediaType.APPLICATION_JSON)
    public Mono<SensorData> getLatest(@Parameter Integer houseId, @Parameter Integer sensorId) {
        return sensorDataRepository.latest(houseId, sensorId)
                .map(sensorDataMapper::toSensorData);
    }

    @Post(uri = "{houseId}/{sensorId}")
    public Mono<HttpStatus> post(@Parameter Integer houseId, @Parameter Integer sensorId,
                                 @NonNull @NotNull @Valid SensorData sensorData) {
        return sensorDataRepository.save(
                        sensorDataMapper.toSensorDateEntity(houseId, sensorId, sensorData))
                .map(added -> Boolean.TRUE.equals((added)) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }
}
