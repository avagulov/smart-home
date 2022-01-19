package com.rebelraven.smarthome.server.repo;

import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.rebelraven.smarthome.server.MongoDbConfig;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public class MongoDbSensorDataRepository implements SensorDataRepository {
    private final MongoDbConfig mongoConfig;
    private final MongoClient mongoClient;

    @Inject
    public MongoDbSensorDataRepository(MongoDbConfig mongoConfig, MongoClient mongoClient) {
        this.mongoConfig = mongoConfig;
        this.mongoClient = mongoClient;
    }

    @Override
    public Publisher<SensorDataEntity> list() {
        return getSensorData().find();
    }

    @Override
    public Publisher<SensorDataEntity> list(Integer houseId, Integer sensorId) {
        BasicDBObject query = new BasicDBObject();
        query.put("house-id", houseId);
        query.put("sensor-id", sensorId);
        return getSensorData().find(query);
    }

    @Override
    public Mono<SensorDataEntity> latest(Integer houseId, Integer sensorId) {
        BasicDBObject query = new BasicDBObject();
        query.put("house-id", houseId);
        query.put("sensor-id", sensorId);

        BasicDBObject sorter = new BasicDBObject();
        sorter.put("datetime", -1);

        return Mono.from(getSensorData()
                .find(query)
                .sort(sorter)
                .limit(1));
    }

    @Override
    public Mono<Boolean> save(SensorDataEntity sensorDataEntity) {
        return Mono.from(getSensorData().insertOne(sensorDataEntity))
                .map(insertOneResult -> Boolean.TRUE)
                .onErrorReturn(Boolean.FALSE);
    }

    @NonNull
    private MongoCollection<SensorDataEntity> getSensorData() {
        return mongoClient.getDatabase(mongoConfig.getName())
                .getCollection(mongoConfig.getSensorData(), SensorDataEntity.class);
    }
}
