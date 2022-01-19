package com.rebelraven.smarthome.server;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.naming.Named;

@ConfigurationProperties("db")
public interface MongoDbConfig extends Named {
    @NonNull
    String getSensorData();
}
