package com.example.demo.service;

import com.example.demo.pojo.User;

public interface LoginService {
    /**
     * 登录
     * @param username
     * @param password
     */
    User login(String username, String password);
}
