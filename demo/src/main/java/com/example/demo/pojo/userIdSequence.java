package com.example.demo.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class userIdSequence {
    @Id
    private String userSeq; //  主键名称
    private long userId;    //  主键值


    public userIdSequence(String userSeq, long userId) {
        this.userSeq = userSeq;
        this.userId = userId;
    }

    public userIdSequence() {
    }


    public String getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(String userSeq) {
        this.userSeq = userSeq;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
