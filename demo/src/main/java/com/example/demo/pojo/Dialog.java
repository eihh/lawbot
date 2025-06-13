package com.example.demo.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "dialog_db")//  集合名
public class Dialog {
    private String username;
    private Integer role;
    private String content;
    private LocalDateTime updateTime;
}

