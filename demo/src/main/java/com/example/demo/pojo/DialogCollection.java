package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder

@Document(collection = "dialog_collection")
//收藏的对话
public class DialogCollection {
    private String title;   //文件标题
    private String username;    //用户名
    private Integer role;   //角色编号
    private String content; //内容
    private LocalDateTime updateTime;   //更新时间


    private DialogCollection(Builder builder) {
        this.title = builder.title;
        this.username = builder.username;
        this.role = builder.role;
        this.content = builder.content;
        this.updateTime = builder.updateTime;
    }

    // 静态内部 Builder 类
    public static class Builder {
        private String title;
        private String username;
        private Integer role;
        private String content;
        private LocalDateTime updateTime;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder role(Integer role) {
            this.role = role;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public DialogCollection build() {
            return new DialogCollection(this);
        }
    }

    // 提供静态方法调用 builder
    public static Builder builder() {
        return new Builder();
    }



    public DialogCollection() {
    }

    public DialogCollection(String title, String username, Integer role, String content, LocalDateTime updateTime) {
        this.title = title;
        this.username = username;
        this.role = role;
        this.content = content;
        this.updateTime = updateTime;
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

    public String toString() {
        return "DialogCollection{title = " + title + ", username = " + username + ", role = " + role + ", content = " + content + ", updateTime = " + updateTime + "}";
    }
}
