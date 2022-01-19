package com.rebelraven.smarthome.server;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.micronaut.configuration.mongo.core.DefaultMongoConfiguration;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@MicronautTest
public class MongoIT {
    @Container
    private static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
                    .withExposedPorts(27017);

    protected static MongoClient mongoClient;

    @MockBean(MongoClient.class)
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @BeforeAll
    static void initialDataSetup() {
        mongoDBContainer.start();
        DefaultMongoConfiguration conf = new DefaultMongoConfiguration(new ApplicationConfiguration());
        conf.setUri(String.format("mongodb://%s:%s", mongoDBContainer.getHost(), mongoDBContainer.getMappedPort(27017)));
        mongoClient = MongoClients.create(conf.buildSettings());
    }
}
