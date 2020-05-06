package com.autohome.dao.domain;

import java.util.Date;

/**
 * @author zzn
 * @date 2020/5/5 1:18
 */

public class SmsBill {
    private String msgId;
    private String accountId;
    private String appendId;
    private String mobile;
    private String content;
    private Date pushTime;
    private Date reportTime;
    private String status;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAppendId() {
        return appendId;
    }

    public void setAppendId(String appendId) {
        this.appendId = appendId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SmsBill{" +
                "msgId='" + msgId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", appendId='" + appendId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                ", pushTime=" + pushTime +
                ", reportTime=" + reportTime +
                ", status='" + status + '\'' +
                '}';
    }
}
