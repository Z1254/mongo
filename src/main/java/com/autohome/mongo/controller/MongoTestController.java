package com.autohome.mongo.controller;

import com.autohome.mongo.pojo.Student;
import com.autohome.mongo.service.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzn
 * @date 2020/2/23 19:53
 */
@RestController
public class MongoTestController {
    @Autowired
    StudentRepository studentRepository;
    @RequestMapping("/addMongo")
    public String addMongo(){
        List<Student> list = new ArrayList<>();
        //预存2条数据
            Student student1 = new Student();
            Student student2 = new Student();
            student1.setId(String.valueOf(001));
            student1.setName("张三");
            student2.setId(String.valueOf(002));
            student2.setName("李四");
            list.add(student1);
            list.add(student2);
        this.studentRepository.saveAll(list);
        return list.toString();
    }
}
