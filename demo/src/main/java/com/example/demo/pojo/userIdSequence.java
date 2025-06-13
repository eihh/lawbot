package com.example.demo.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class userIdSequence {
    @Id
    private String userSeq; //  主键名称
    private long userId;    //  主键值
}
