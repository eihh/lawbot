package com.example.demo.controller;
import com.example.demo.common.Result;
import com.example.demo.pojo.Dialog;
import com.example.demo.pojo.dto.DialogDTO;
import com.example.demo.service.QaRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/history")
@Slf4j
public class QaRecordController {
    @Autowired
    private QaRecordService qaRecordService;

    //保存对话记录
    @PostMapping("/save")
    public Result get(@RequestBody DialogDTO dialogDTO) {
        //log.info("对话内容:{}",dialogDTO);

        qaRecordService.save(dialogDTO);
        return Result.success();
    }

    //获取所有对话记录
    @GetMapping("/list")
    public Result list() {
        List<Dialog> dialogs = qaRecordService.list();
        return Result.success(dialogs);
    }

    //展示前十次对话
    @GetMapping("/list/{number}")
    public Result list(@PathVariable Integer number) {
        List<Dialog> dialogs = qaRecordService.list(number);
        return Result.success(dialogs);
    }

    //删除所有对话信息
    @DeleteMapping("/delete")
    public Result delete() {
        qaRecordService.delete();
        return Result.success();
    }
}
