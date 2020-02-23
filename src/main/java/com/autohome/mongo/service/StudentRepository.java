package com.autohome.mongo.service;

import com.autohome.mongo.pojo.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zzn
 * @date 2020/2/23 19:48
 */
public interface StudentRepository extends MongoRepository<Student, String> {
}
