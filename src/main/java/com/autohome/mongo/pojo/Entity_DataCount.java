package com.autohome.mongo.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zzn
 * @date 2020/2/23 19:46
 */
@Document("PersonalSpace_Entity_DataCount")
public class Entity_DataCount {
    @Id
    private int key;

    private int FollowersCount;
    private int FollowingCount;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getFollowersCount() {
        return FollowersCount;
    }

    public void setFollowersCount(int followersCount) {
        FollowersCount = followersCount;
    }

    public int getFollowingCount() {
        return FollowingCount;
    }

    public void setFollowingCount(int followingCount) {
        FollowingCount = followingCount;
    }
}
