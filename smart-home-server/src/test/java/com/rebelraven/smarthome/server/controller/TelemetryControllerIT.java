package com.rebelraven.smarthome.server.controller;

import com.rebelraven.smarthome.server.controller.dto.Measurement;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class TelemetryControllerIT {
    @Client("/")
    @Inject
    HttpClient httpClient;

    @Test
    void should_return_empty_measurements() {
        final Measurement[] result = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/v1/telemetry/1/1/measurements"), Measurement[].class);

        assertThat(result.length, equalTo(0));
    }

    @Test
    void should_return_not_found_measurements() {
        HttpClientResponseException hcre = assertThrows(HttpClientResponseException.class, () -> {
            final HttpResponse<Measurement> result = httpClient.toBlocking()
                    .exchange(HttpRequest.GET("/v1/telemetry/1/1/measurements/latest"),
                            Argument.of(Measurement.class));
        });

        assertThat(hcre.getStatus(), equalTo(HttpStatus.NOT_FOUND));
    }
}