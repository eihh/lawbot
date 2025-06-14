package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.exception.ServiceException;
import com.example.demo.pojo.DialogCollection;
import com.example.demo.pojo.dto.QueryCollectionDTO;
import com.example.demo.service.CollectionService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.RequestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.web.bind.annotation.*;
import com.example.demo.pojo.dto.CollectionDTO;

import java.util.List;

@RestController
@RequestMapping("/collection")
@Slf4j
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    //预测模块的收藏对话功能（把要收藏的对话保存进收藏集合）
    //收藏对话的标题就是文件名 文件名从 “上传文件”功能获取
    @RequestMapping("/yuce/save")
    public Result saveCollection(@RequestBody CollectionDTO collectionDTO,
                                 @RequestHeader String jwtToken){


        //从jwt解析获取用户名
        try{
            String username = JwtUtils.getUsernameFromToken(jwtToken);
            collectionDTO.setUsername(username);

            collectionService.saveCollection(collectionDTO);
            return Result.success("收藏成功!");
        }
        catch (Exception e){
            throw new ServiceException(500,"收藏失败");
        }



    }

    //根据标题或用户名展示收藏的对话
    //QueryCollectionDTO包含两个查询参数 title和username
    @GetMapping("/list")
    public Result listCollection(@RequestParam(required = false) String title,
                                 @RequestParam(required = false) String username){
        // 构造 QueryCollectionDTO
        QueryCollectionDTO queryCollectionDTO = new QueryCollectionDTO();
        queryCollectionDTO.setTitle(title);
        queryCollectionDTO.setUsername(username);


        List<DialogCollection> dialogCollections = collectionService.listCollection(queryCollectionDTO);



        return Result.success(dialogCollections);
    }

    //删除所有收藏对话
    @DeleteMapping("/clean")
    public Result cleanCollection(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String username){

        // 构造 QueryCollectionDTO
        QueryCollectionDTO queryCollectionDTO = new QueryCollectionDTO();
        queryCollectionDTO.setTitle(title);
        queryCollectionDTO.setUsername(username);

        collectionService.deleteAllCollections(queryCollectionDTO);
        return Result.success();
    }

    //删除一个收藏对话
    @DeleteMapping("/delete")
    public Result deleteCollection(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String username){
        // 构造 QueryCollectionDTO
        QueryCollectionDTO queryCollectionDTO = new QueryCollectionDTO();
        queryCollectionDTO.setTitle(title);
        queryCollectionDTO.setUsername(username);

        collectionService.deleteOneCollection(queryCollectionDTO);
        return Result.success();
    }



}
