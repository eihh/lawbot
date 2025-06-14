package com.example.demo.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVO {
    private Integer userId;
    private String username;
    private String token;


    // 私有构造函数，只能通过 Builder 创建
    private UserVO(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.token = builder.token;
    }

    // Getter 方法
    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    // Builder 静态内部类
    public static class Builder {
        private Integer userId;
        private String username;
        private String token;

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public UserVO build() {
            return new UserVO(this);
        }
    }

    // 提供静态方法获取 Builder 实例
    public static Builder builder() {
        return new Builder();
    }

    // 可选：重写 toString()
    @Override
    public String toString() {
        return "UserVO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }


}


