package com.amose.demo1;

import java.util.Date;

public class ClientMessage {

    private String message;
    private Date createTime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "message='" + message + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
