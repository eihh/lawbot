package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class DialogDTO {
    String content;
    Integer role;

    public DialogDTO(Integer role, String content) {
        this.role = role;
        this.content = content;
    }

    public DialogDTO() {
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
}
