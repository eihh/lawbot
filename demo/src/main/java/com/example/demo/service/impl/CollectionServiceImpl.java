package com.example.demo.service.impl;

import com.example.demo.pojo.DialogCollection;
import com.example.demo.pojo.dto.CollectionDTO;
import com.example.demo.pojo.dto.QueryCollectionDTO;
import com.example.demo.repository.CollectionRepository;
import com.example.demo.service.CollectionService;
import com.example.demo.utils.RequestContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;

    //把CollectionDTO组装成DialogCollection
    private DialogCollection GetADialogCollection(CollectionDTO collectionDTO) {
        DialogCollection dialogCollection = new DialogCollection();
        //拷贝属性
        BeanUtils.copyProperties(collectionDTO,dialogCollection);


        //设置更新时间
        dialogCollection.setUpdateTime(LocalDateTime.now());

        return dialogCollection;
    }

    //保存收藏
    @Override
    public void saveCollection(CollectionDTO collectionDTO) {
        DialogCollection dialogCollection = GetADialogCollection(collectionDTO);

        dialogCollection.setUpdateTime(LocalDateTime.now());

        dialogCollection.setContent(collectionDTO.getContent());

        dialogCollection.setTitle(collectionDTO.getTitle());

        collectionRepository.save(dialogCollection);
    }


    //根据文件名或者用户名删除收藏对话
    @Override
    public void deleteAllCollections(QueryCollectionDTO queryCollectionDTO) {

        //根据标题或用户名清空所有收藏
        String title = queryCollectionDTO.getTitle();
        String username = queryCollectionDTO.getUsername();

        collectionRepository.deleteAllByTitleOrUsername(title,username);
    }

    //根据文件名和用户名删除一条收藏对话
    @Override
    public void deleteOneCollection(QueryCollectionDTO queryCollectionDTO) {
        String title = queryCollectionDTO.getTitle();
        String username = queryCollectionDTO.getUsername();
        collectionRepository.deleteByTitleAndUsername(title,username);
    }

    //根据标题或用户名查询收藏的对话
    @Override
    public List<DialogCollection> listCollection(QueryCollectionDTO queryCollectionDTO) {
        String username = queryCollectionDTO.getUsername();
        String title = queryCollectionDTO.getTitle();

        return collectionRepository.findByTitleOrUsername(title,username);
    }




}
