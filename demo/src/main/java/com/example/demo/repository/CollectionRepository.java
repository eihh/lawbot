package com.example.demo.repository;

import com.example.demo.pojo.DialogCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollectionRepository extends MongoRepository<DialogCollection,String> {

    //删除一条收藏
    void deleteByTitleAndUsername(String title,String username);

    //根据文件名或用户名查询收藏的对话
    List<DialogCollection> findByTitleOrUsername(String title, String username);

    //根据文件名或用户名删除所有收藏对话
    void deleteAllByTitleOrUsername(String title, String username);
}
