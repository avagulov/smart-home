package com.rebelraven.smarthome.server.service;

import com.rebelraven.smarthome.server.domain.House;
import jakarta.inject.Singleton;

import java.util.HashMap;

@Singleton
public class StateService {
    private HashMap<Integer, House> houses = new HashMap<>();

    public House getOrCreate(Integer houseId) {
        House house = houses.computeIfAbsent(houseId, id -> new House());
        houses.putIfAbsent(houseId, house);

        return house;
    }
}
