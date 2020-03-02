package com.autohome.mongo.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author zzn
 * @date 2020/2/27 17:30
 */
@Document("PersonalSpace_Entity_FocusItem")
public class Entity_FocusItem {
    @Id
    private long key;

    private int OriginalUid;
    private int TargetUid;
    private int UserType;
    private String Remark;
    private int IsQuietFollow;
    private int IsBlock;
    private Date FocusTime;

    public Entity_FocusItem() {
    }

    public Entity_FocusItem(long key, int originalUid, int targetUid, int userType, String remark, int isQuietFollow, int isBlock, Date focusTime) {
        this.key = key;
        OriginalUid = originalUid;
        TargetUid = targetUid;
        UserType = userType;
        Remark = remark;
        IsQuietFollow = isQuietFollow;
        IsBlock = isBlock;
        FocusTime = focusTime;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public int getOriginalUid() {
        return OriginalUid;
    }

    public void setOriginalUid(int originalUid) {
        OriginalUid = originalUid;
    }
}
