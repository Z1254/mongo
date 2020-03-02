package com.autohome.mongo;

import com.autohome.mongo.pojo.Entity_DataCount;
import com.autohome.mongo.pojo.Entity_FocusItem;
import com.autohome.mongo.service.Entity_DataCountRepository;
import com.autohome.mongo.service.Entity_FocusItemRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zzn
 * @date 2020/2/27 17:28
 */
@SpringBootTest
public class MongoApplicationTests2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoApplicationTests2.class);
    @Autowired
    private Entity_FocusItemRepository entity_focusItemRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    MongoOperations mongoOperations;

    @Test
    void contextLoads() {
    }

    //新增
    @Test
    public void InsertData2() throws Exception {
        //分批次写 插入100的整数倍
        long start = System.currentTimeMillis();
        insert(10);
        long end = System.currentTimeMillis();
        LOGGER.info("所用时长:{}", end - start);
    }


    //修改
    @Test
    public void update() throws Exception {
        LOGGER.info("------批量修改开始执行......");
        long start = System.currentTimeMillis();
        FileReader fileReader = new FileReader("D:\\IdeaProject\\mongo\\folder\\data2.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String key = "";
        List<Entity_DataCount> list = new ArrayList<>();
        while ((key = bufferedReader.readLine()) != null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(Integer.valueOf(key)));
            Update update = Update.update("TargetUid",2).set("UserType",2);
            this.mongoTemplate.upsert(query,update,"PersonalSpace_Entity_FocusItem");
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
        FileReader fileReader = new FileReader("D:\\IdeaProject\\mongo\\folder\\data2.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String key = "";
        List<Entity_FocusItem> list = new ArrayList<>();
        while ((key = bufferedReader.readLine()) != null) {
            Entity_FocusItem entity_focusItem = new Entity_FocusItem();
            entity_focusItem.setKey(Long.valueOf(key));
            list.add(entity_focusItem);
            if (list.size() == 10) {
                this.entity_focusItemRepository.deleteAll(list);
                list.clear();
            }
        }
        bufferedReader.close();
        fileReader.close();
        long end = System.currentTimeMillis();
        LOGGER.info("------删除完成");
        LOGGER.info("所用时长:{}", end - start);
    }


    public void insert(int number) throws IOException {
        LOGGER.info("------分批次{}条新增数据 开始执行......", number);
        FileWriter fileWriter = new FileWriter(new File("D:\\IdeaProject\\mongo\\folder\\data2.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int index = 10000;index < 10000 + number;index++){
            Entity_FocusItem entity_focusItem = new Entity_FocusItem(index,index,1,1,"1",1,1,new Date());
            this.mongoOperations.insert(entity_focusItem);
            //主键 存入本地文件
            bufferedWriter.write(index + "\n");
        }

        bufferedWriter.close();
        fileWriter.close();
        LOGGER.info("-------新增数据完成");
    }
}
