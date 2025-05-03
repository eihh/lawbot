package com.example.demo.pojo;


import java.time.LocalDateTime;


public class User {
    private Integer userId;
    private String username;
    private String password;

    public User() {
    }

    public User(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }








    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User{userId = " + userId + ", username = " + username + ", password = " + password + "}";
    }



    //private String email;
    //private String phone;
    //private Short gender;
    //private Short status;
    //private Short roleId;
    //private String image;
    //private LocalDateTime createTime;
    //private LocalDateTime updateTime;
}
