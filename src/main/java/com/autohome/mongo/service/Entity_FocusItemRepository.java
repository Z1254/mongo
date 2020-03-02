package com.autohome.mongo.service;

import com.autohome.mongo.pojo.Entity_DataCount;
import com.autohome.mongo.pojo.Entity_FocusItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zzn
 * @date 2020/2/27 17:34
 */
public interface Entity_FocusItemRepository extends MongoRepository<Entity_FocusItem, ObjectId> {
}
