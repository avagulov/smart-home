package com.rebelraven.smarthome.server.controller;

import com.rebelraven.smarthome.server.controller.dto.DoubleValue;
import com.rebelraven.smarthome.server.controller.dto.Measurement;
import com.rebelraven.smarthome.server.service.TelemetryService;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/v1/telemetry")
public class TelemetryController {

    private TelemetryService telemetryService;

    @Inject
    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public String index() {
        return "Telemetry Controller";
    }

    @Get(uri = "{houseId}/{sensorId}/measurements",
            produces = MediaType.APPLICATION_JSON)
    public List<Measurement> getTelemetry(@Parameter Integer houseId, @Parameter Integer sensorId){
        return telemetryService.getMeasurements(houseId, sensorId).stream()
                .map(v -> new Measurement(v.getDatetime(), new DoubleValue(v.getValue())))
                .collect(Collectors.toList());
    }

    @Post(uri = "{houseId}/{sensorId}/measurements",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    public Measurement postTelemetry(@Parameter Integer houseId, @Parameter Integer sensorId, @Parameter DoubleValue value){
        telemetryService.addMeasurement(houseId, sensorId, value.getValue());
        return new Measurement(Instant.now(), new DoubleValue(value.getValue()));
    }
}