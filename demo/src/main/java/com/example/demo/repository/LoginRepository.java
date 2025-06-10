package com.example.demo.repository;

import com.example.demo.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<User, String> {
    /**
     * 根据用户名和密码查询用户
     */
    User findByUsername(String username);
}
