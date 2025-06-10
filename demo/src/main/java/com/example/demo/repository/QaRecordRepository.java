package com.example.demo.repository;

import com.example.demo.pojo.Dialog;
import org.springframework.data.mongodb.repository.MongoRepository;

// 继承 MongoRepository 接口
public interface QaRecordRepository extends MongoRepository<Dialog, String> {
    // 可以在此添加自定义查询方法，不过基本的 CRUD 方法已由父接口提供

}