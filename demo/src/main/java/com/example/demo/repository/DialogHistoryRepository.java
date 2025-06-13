package com.example.demo.repository;

import com.example.demo.pojo.Dialog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// 继承 MongoRepository 接口
public interface DialogHistoryRepository extends MongoRepository<Dialog, String> {
    //根据用户名查询
    List<Dialog> findAllByUsername(String username);
    // 可以在此添加自定义查询方法，不过基本的 CRUD 方法已由父接口提供

    // 根据用户名查询最新的几条记录
    List<Dialog> findTopNumberByUsernameOrderByUpdateTimeDesc(String username, Integer number);

    // 根据用户名删除所有记录
    void deleteAllByUsername(String username);
}