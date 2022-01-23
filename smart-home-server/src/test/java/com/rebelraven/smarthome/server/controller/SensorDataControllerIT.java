package com.rebelraven.smarthome.server.controller;

import com.rebelraven.smarthome.server.MongoIT;
import com.rebelraven.smarthome.server.controller.dto.MeasurementData;
import com.rebelraven.smarthome.server.controller.dto.SensorData;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@MicronautTest
class SensorDataControllerIT extends MongoIT {
    @Client("/")
    @Inject
    HttpClient httpClient;

    @Test
    void should_return_empty_sensor_data() {
        final MeasurementData[] result = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/v1/telemetry/sensor-data/1/1/"), MeasurementData[].class);
        assertThat(result.length, equalTo(0));
    }

    @Test
    void should_do_e2e_well() {
        HttpResponse<Void> response = httpClient.toBlocking()
                .exchange(HttpRequest.POST("/v1/telemetry/sensor-data/99/99/",
                        new MeasurementData(Instant.now().minusSeconds(2), 99d)));
        assertThat(response.getStatus(), equalTo(HttpStatus.CREATED));

        response = httpClient.toBlocking()
                .exchange(HttpRequest.POST("/v1/telemetry/sensor-data/99/99/",
                        new MeasurementData(Instant.now().minusSeconds(1), 98d)));
        assertThat(response.getStatus(), equalTo(HttpStatus.CREATED));

        final MeasurementData latest = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/v1/telemetry/sensor-data/99/99/latest"), MeasurementData.class);
        assertThat(latest.getValue(), equalTo(98d));
        assertThat(latest.getDatetime(), notNullValue());

        final MeasurementData[] result = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/v1/telemetry/sensor-data/99/99"), MeasurementData[].class);
        assertThat(Arrays.stream(result).map(MeasurementData::getValue).collect(Collectors.toList()),
                contains(98d, 99d));
    }

    @Test
    void should_post_multiple_well() {
        HttpResponse<Void> response = httpClient.toBlocking()
                .exchange(HttpRequest.POST("/v1/telemetry/sensor-data/999/",
                        List.of(new SensorData(999, new MeasurementData(Instant.now().minusSeconds(2), 999d)),
                                new SensorData(999, new MeasurementData(Instant.now().minusSeconds(1), 998d)),
                                new SensorData(999, new MeasurementData(Instant.now(), 997d)))));
        assertThat(response.getStatus(), equalTo(HttpStatus.CREATED));

        final MeasurementData[] result = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/v1/telemetry/sensor-data/999/999"), MeasurementData[].class);
        assertThat(Arrays.stream(result).map(MeasurementData::getValue).collect(Collectors.toList()),
                contains(997d, 998d, 999d));
    }
}