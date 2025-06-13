package com.example.demo.repository;

import com.example.demo.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.constant.ConstantDesc;

public interface RegisterRepository extends MongoRepository<User, String> {
    // 根据用户名查询用户
    User findUserByUsername(String username);
}
