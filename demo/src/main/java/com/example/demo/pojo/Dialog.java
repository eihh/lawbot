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

    public Dialog(String username, LocalDateTime updateTime, String content, Integer role) {
        this.username = username;
        this.updateTime = updateTime;
        this.content = content;
        this.role = role;
    }

    public Dialog() {
    }


    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

