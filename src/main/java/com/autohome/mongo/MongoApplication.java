package com.autohome.mongo;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class MongoApplication {

    @Autowired
    private MongoClient mongoClient;


    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }
}
