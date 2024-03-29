package com.rebelraven.smarthome.server.domain;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.*;

public class House {
    public static final int MAX_QUEUE_SIZE = 1000;

    private HashMap<Integer, Queue<SensorData>> sensorsData = new HashMap<>();

    public Queue<SensorData> getOrCreateSensorDataQueue(Integer sensorId) {
        Queue<SensorData> sensorData = sensorsData.computeIfAbsent(sensorId, id -> new CircularFifoQueue<>(MAX_QUEUE_SIZE));
        sensorsData.putIfAbsent(sensorId, sensorData);
        return sensorData;
    }

    public Optional<SensorData> getLatestSensorData(Integer sensorId) {
        CircularFifoQueue<SensorData> queue = (CircularFifoQueue<SensorData>) sensorsData.computeIfAbsent(sensorId, id -> new CircularFifoQueue<>(MAX_QUEUE_SIZE));
        if (!queue.isEmpty())
            return Optional.of(queue.get(queue.size() - 1));
        return Optional.empty();
    }
}
