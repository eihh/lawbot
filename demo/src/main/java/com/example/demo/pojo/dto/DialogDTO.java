package com.example.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.PriorityQueue;

@Data

public class DialogDTO {
    /*private String username;*/
    private String content;
    private Integer role;


    public DialogDTO() {
    }

    public DialogDTO(String content, Integer role) {
        this.content = content;
        this.role = role;
    }

    /**
     * 获取
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取
     * @return role
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置
     * @param role
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    public String toString() {
        return "DialogDTO{content = " + content + ", role = " + role + "}";
    }
}
