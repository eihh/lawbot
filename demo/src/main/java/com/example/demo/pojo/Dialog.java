package com.example.demo.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "dialog_db")//  集合名
public class Dialog {
    private Integer role;
    private String content;
    private LocalDateTime updateTime;


    public Dialog(Integer role, String content, LocalDateTime updateTime) {
        this.role = role;
        this.content = content;
        this.updateTime = updateTime;
    }

    public Dialog() {
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}

