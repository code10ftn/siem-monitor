package com.code10.security.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${db.name}")
    private String name;

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private int port;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(new ServerAddress(host, port),
                MongoCredential.createCredential(username, getDatabaseName(), password.toCharArray()), MongoClientOptions.builder().build());
    }

    @Override
    protected String getDatabaseName() {
        return name;
    }
}
