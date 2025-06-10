package com.example.demo.service;

import com.example.demo.pojo.Dialog;
import com.example.demo.pojo.dto.DialogDTO;

import java.util.List;

public interface QaRecordService {
    /*void saveQaRecord(QaRecord qaRecord);*/

    /**
     * 根据问题查询答案
     * @param question
     * @return
     */
    /*String findByQuestion(String question);*/

    /**
     * 保存对话记录
     * @param dialog
     */
    void save(DialogDTO dialog);

    // 查询所有对话记录
    List<Dialog> list();

    // 查询number条对话记录
    List<Dialog> list(Integer number);

    //  删除所有对话记录
    void delete();
}
