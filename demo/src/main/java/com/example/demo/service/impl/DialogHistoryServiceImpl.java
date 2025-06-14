package com.example.demo.service.impl;

import com.example.demo.pojo.Dialog;
import com.example.demo.pojo.dto.DialogDTO;
import com.example.demo.repository.DialogHistoryRepository;
import com.example.demo.service.DialogHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class DialogHistoryServiceImpl implements DialogHistoryService {
    @Autowired
    private DialogHistoryRepository dialogHistoryRepository;

    // 保存对话记录的方法
    @Override
    public void save(DialogDTO dialogDTO,String username) {
        Dialog  dialog = new Dialog();
        //复制属性
        BeanUtils.copyProperties(dialogDTO,dialog);
        dialog.setUpdateTime(LocalDateTime.now());
        dialog.setUsername(username);

        dialogHistoryRepository.save(dialog);
    }

    // 查询所有对话记录的方法
    @Override
    public List<Dialog> list(String username) {
        List<Dialog> dialogs = dialogHistoryRepository.findAllByUsername(username);
        return dialogs;
    }

    //查询number次对话的信息
    @Override
    public List<Dialog> list(Integer number, String username) {
        if(number==null || number<=0){
            throw new RuntimeException("number不能小于1");
        }
        //查询一共有几次对话
        List<Dialog> dialogs = dialogHistoryRepository.findAllByUsername(username);
        if(number>dialogs.size()){
            return dialogs;
        }else {
            //返回前number次对话
            return dialogs.subList(number,number+1);
        }


//        return dialogHistoryRepository
//                .findTopNumberByUsernameOrderByUpdateTimeDesc(username, number);
    }

    // 删除对话记录的方法
    @Override
    public void delete(String username) {
        dialogHistoryRepository.deleteAllByUsername(username);
    }

    // 其他业务方法（如查询）
    // public List<QaRecord> getAllRecords() {
    //     return qaRecordRepository.findAll();
    // }
}