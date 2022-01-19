package com.rebelraven.smarthome.server.controller;

import com.rebelraven.smarthome.server.controller.dto.SensorData;
import com.rebelraven.smarthome.server.repo.SensorDataEntity;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.core.annotation.NonNull;

@Prototype
public class SensorDataMapper {
    public SensorData toSensorData(@NonNull SensorDataEntity sensorDataEntity) {
        return new SensorData(sensorDataEntity.getDatetime(), sensorDataEntity.getValue());
    }

    public SensorDataEntity toSensorDateEntity
            (@NonNull Integer houseId,
             @NonNull Integer sensorId,
             @NonNull SensorData sensorData) {
        return new SensorDataEntity(houseId, sensorId, sensorData.getValue(), sensorData.getDatetime());
    }
}
