package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.pojo.Dialog;
import com.example.demo.pojo.dto.DialogDTO;
import com.example.demo.service.DialogHistoryService;
import com.example.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@Slf4j
public class DialogHistoryController {
    @Autowired
    private DialogHistoryService dialogHistoryService;

    //保存对话记录
    @PostMapping("/save")
    //参数是dto和响应头
    public Result get(@RequestBody DialogDTO dialogDTO,@RequestHeader String jwtToken) {
        log.info("对话内容:{}",dialogDTO);
        //从响应头获取用户名
        String username = JwtUtils.getUsernameFromToken(jwtToken);

        dialogHistoryService.save(dialogDTO,username);
        return Result.success();
    }

    //获取所有对话记录
    @GetMapping("/list/{username}")
    public Result list(@PathVariable String username) {
        List<Dialog> dialogs = dialogHistoryService.list(username);
        return Result.success(dialogs);
    }

    //展示前十次对话
    @GetMapping("/list/{username}/{number}")
    public Result list(@PathVariable Integer number,@PathVariable String username) {
        List<Dialog> dialogs = dialogHistoryService.list(number,username);
        return Result.success(dialogs);
    }

    //删除所有对话信息
    @DeleteMapping("/delete/{username}")
    public Result delete(@PathVariable String username) {
        dialogHistoryService.delete(username);
        return Result.success();
    }

    //批量保存对话记录
    @PostMapping("/save/batch")
    public Result saveBatch(@RequestBody List<DialogDTO> dialogDTOs,@RequestHeader String jwtToken) {
        for (DialogDTO dialogDTO : dialogDTOs) {
            String username = JwtUtils.getUsernameFromToken(jwtToken);

            dialogHistoryService.save(dialogDTO,username);
        }
        return Result.success();
    }
}
