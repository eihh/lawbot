package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class CollectionDTO {
    private String username;//用户名
    private String title; //文件名称
    private String content;//ai的响应内容
    private Integer role;//角色 1是用户 0是ai


    public CollectionDTO() {
    }



    public CollectionDTO(String username, String title, String content, Integer role) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.role = role;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
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

    public String toString() {
        return "CollectionDTO{title = " + title + ", content = " + content + ", role = " + role + "}";
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }
}
