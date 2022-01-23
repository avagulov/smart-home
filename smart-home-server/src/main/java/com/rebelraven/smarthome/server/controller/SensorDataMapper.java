package com.rebelraven.smarthome.server.controller;

import com.rebelraven.smarthome.server.controller.dto.MeasurementData;
import com.rebelraven.smarthome.server.repo.SensorDataEntity;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.core.annotation.NonNull;

@Prototype
public class SensorDataMapper {
    public MeasurementData toSensorData(@NonNull SensorDataEntity sensorDataEntity) {
        return new MeasurementData(sensorDataEntity.getDatetime(), sensorDataEntity.getValue());
    }

    public SensorDataEntity toSensorDateEntity
            (@NonNull Integer houseId,
             @NonNull Integer sensorId,
             @NonNull MeasurementData measurementData) {
        return new SensorDataEntity(houseId, sensorId, measurementData.getValue(), measurementData.getDatetime());
    }
}
