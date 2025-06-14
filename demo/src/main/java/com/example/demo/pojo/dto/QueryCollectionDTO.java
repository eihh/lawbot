package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class QueryCollectionDTO {
    //保存查询收藏的参数  2个 标题 和 用户名
    private String title;
    private String username;


    public QueryCollectionDTO() {
    }

    public QueryCollectionDTO(String title, String username) {
        this.title = title;
        this.username = username;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return "QueryCollectionDTO{title = " + title + ", username = " + username + "}";
    }
}