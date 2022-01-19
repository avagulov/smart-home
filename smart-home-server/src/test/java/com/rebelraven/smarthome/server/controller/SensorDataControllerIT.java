package com.rebelraven.smarthome.server.controller;

import com.rebelraven.smarthome.server.MongoIT;
import com.rebelraven.smarthome.server.controller.dto.SensorData;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
        final SensorData[] result = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/v1/telemetry/sensor-data/1/1/"), SensorData[].class);
        assertThat(result.length, equalTo(0));
    }

    @Test
    void should_do_e2e_well() {
        HttpResponse<Void> response = httpClient.toBlocking()
                .exchange(HttpRequest.POST("/v1/telemetry/sensor-data/99/99/", new SensorData(99d)));
        assertThat(response.getStatus(), equalTo(HttpStatus.CREATED));

        response = httpClient.toBlocking()
                .exchange(HttpRequest.POST("/v1/telemetry/sensor-data/99/99/", new SensorData(98d)));
        assertThat(response.getStatus(), equalTo(HttpStatus.CREATED));

        final SensorData latest = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/v1/telemetry/sensor-data/99/99/latest"), SensorData.class);
        assertThat(latest.getValue(), equalTo(98d));
        assertThat(latest.getDatetime(), notNullValue());

        final SensorData[] result = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/v1/telemetry/sensor-data/99/99"), SensorData[].class);
        assertThat(Arrays.stream(result).map(SensorData::getValue).collect(Collectors.toSet()),
                contains(99d, 98d));
    }
}