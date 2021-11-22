package com.rebelraven.smarthome.server.service;

import com.rebelraven.smarthome.server.domain.House;
import com.rebelraven.smarthome.server.domain.SensorData;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class TelemetryService {
    private StateService stateService;

    @Inject
    public TelemetryService(StateService stateService) {
        this.stateService = stateService;
    }

    public void addMeasurement(Integer houseId, Integer sensorId, double value) {
        House house = stateService.getOrCreate(houseId);
        house.getOrCreateSensorDataQueue(sensorId).add(new SensorData(value));
    }

    public List<SensorData> getMeasurements(Integer houseId, Integer sensorId){
        House house = stateService.getOrCreate(houseId);
        return house.getOrCreateSensorDataQueue(sensorId).stream()
                .collect(Collectors.toList());
    }
}
