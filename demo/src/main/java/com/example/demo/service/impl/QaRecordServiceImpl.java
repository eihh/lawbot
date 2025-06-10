package com.example.demo.service.impl;

import com.example.demo.pojo.Dialog;
import com.example.demo.pojo.dto.DialogDTO;
import com.example.demo.repository.QaRecordRepository;
import com.example.demo.service.QaRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class QaRecordServiceImpl implements QaRecordService {
    @Autowired
    private QaRecordRepository qaRecordRepository;

    /*// 保存问答记录的方法
    @Override
    public void saveQaRecord(QaRecord qaRecord) {
        //为更新时间 和 userId赋值
        qaRecord.setUpdateTime(LocalDateTime.now());
        qaRecord.setUserId(ThreadLocalUtils.getCurrentId());
        log.info("问答记录:{}",qaRecord);

        qaRecordRepository.save(qaRecord);
    }*/

   /* @Override
    public String findByQuestion(String question) {
        QaRecord qaRecord;
        qaRecord = qaRecordRepository.findByQuestion(question);

        if (qaRecord == null){
            return "没有找到该问题的答案";
        }
        return qaRecord.getAnswer();
    }*/

    // 保存对话记录的方法
    @Override
    public void save(DialogDTO dialogDTO) {
        Dialog  dialog = new Dialog();
        //复制属性
        BeanUtils.copyProperties(dialogDTO,dialog);
        dialog.setUpdateTime(LocalDateTime.now());

        qaRecordRepository.save(dialog);
    }

    // 查询所有对话记录的方法
    @Override
    public List<Dialog> list() {
        List<Dialog> dialogs = qaRecordRepository.findAll();
        return dialogs;
    }

    //查询number次对话的信息
    @Override
    public List<Dialog> list(Integer number) {
        //查询前number次对话
        List<Dialog> dialogs = qaRecordRepository.findAll();
        if (dialogs.size() < number) {
            return dialogs;
        }
        else {
            return dialogs.subList(0, number);
        }



    }

    // 删除对话记录的方法
    @Override
    public void delete() {
        qaRecordRepository.deleteAll();
    }

    // 其他业务方法（如查询）
    // public List<QaRecord> getAllRecords() {
    //     return qaRecordRepository.findAll();
    // }
}