package com.autohome.mongo;

import com.autohome.mongo.pojo.Entity_DataCount;
import com.autohome.mongo.service.StudentRepository;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class MongoApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoApplicationTests.class);
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
    }

    //新增
    @Test
    public void InsertData() throws Exception {
        //分批次写 插入100的整数倍
        long start = System.currentTimeMillis();
        insert(10);
        long end = System.currentTimeMillis();
        LOGGER.info("所用时长:{}", end - start);
    }

/*
    //修改
    @Test
    public void update() throws Exception {
        LOGGER.info("------批量修改开始执行......");
        long start = System.currentTimeMillis();
        FileReader fileReader = new FileReader("D:\\IdeaProject\\mongo\\folder\\data.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String key = "";
        List<Entity_DataCount> list = new ArrayList<>();
        while ((key = bufferedReader.readLine()) != null) {
            //LOGGER.info("key={}",key);
            Entity_DataCount entityDataCount = new Entity_DataCount();
            entityDataCount.setKey();
            entityDataCount.setName("张三");
            list.add(entityDataCount);
            if (list.size() == 100) {
                this.studentRepository.saveAll(list);
                list.clear();
            }
        }
        bufferedReader.close();
        fileReader.close();
        long end = System.currentTimeMillis();
        LOGGER.info("------修改完成");
        LOGGER.info("所用时长:{}", end - start);
    }

    //删除
    @Test
    public void delete() throws Exception {
        LOGGER.info("------批量删除开始执行......");
        long start = System.currentTimeMillis();
        FileReader fileReader = new FileReader("D:\\IdeaProject\\mongo\\folder\\data.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String key = "";
        List<Entity_DataCount> list = new ArrayList<>();
        while ((key = bufferedReader.readLine()) != null) {
            //LOGGER.info("key={}",key);
            Entity_DataCount entityDataCount = new Entity_DataCount();
            entityDataCount.setKey(key);
            list.add(entityDataCount);
            if (list.size() == 100) {
                this.studentRepository.deleteAll(list);
                list.clear();
            }
        }
        bufferedReader.close();
        fileReader.close();
        long end = System.currentTimeMillis();
        LOGGER.info("------删除完成");
        LOGGER.info("所用时长:{}", end - start);
    }
*/


    public void insert(int number) throws IOException {
        LOGGER.info("------分批次{}条新增数据 开始执行......", number);
        FileWriter fileWriter = new FileWriter(new File("D:\\IdeaProject\\mongo\\folder\\data.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        int current = 0;
        int batchSize = 10;
        int index = 70000000;
        while (current < number) {
            List<Entity_DataCount> list = new ArrayList<>();
            for (int i = 0; i < batchSize; i++) {
                Entity_DataCount entityDataCount = new Entity_DataCount();
                entityDataCount.setKey(index);
                entityDataCount.setFollowersCount(10);
                entityDataCount.setFollowingCount(10);
                /*BsonDocument bsonDocument = new BsonDocument();
                bsonDocument.put("key",new BsonInt32(index));
                bsonDocument.put("FollowersCount",new BsonInt32(10));
                bsonDocument.put("FollowingCount",new BsonInt32(10));
                list.add(bsonDocument);*/
                //mongoTemplate.insert(bsonDocument);
                list.add(entityDataCount);
                //主键 存入本地文件
                bufferedWriter.write(index + "\n");
                index++;
            }
            //mongoTemplate.insertAll(list);
            this.mongoTemplate.insertAll(list);
            current += batchSize;
        }
        bufferedWriter.close();
        fileWriter.close();
        LOGGER.info("-------新增数据完成");
    }
}
