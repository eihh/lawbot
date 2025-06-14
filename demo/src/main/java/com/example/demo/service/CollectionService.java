package com.example.demo.service;

import com.example.demo.pojo.DialogCollection;
import com.example.demo.pojo.dto.CollectionDTO;
import com.example.demo.pojo.dto.QueryCollectionDTO;

import java.util.List;
import java.util.stream.Stream;

public interface CollectionService {
    //保存收藏
    void saveCollection(CollectionDTO collectionDTO);

    //根据文件名或用户名查看收藏
    List<DialogCollection> listCollection(QueryCollectionDTO queryCollectionDTO);


    //删除所有收藏
    void deleteAllCollections(QueryCollectionDTO queryCollectionDTO);

    //删除一个收藏
    void deleteOneCollection(QueryCollectionDTO queryCollectionDTO);
}
