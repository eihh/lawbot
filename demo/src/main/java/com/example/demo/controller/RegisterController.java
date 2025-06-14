package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.pojo.dto.RegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.RegisterService;

@RestController
@RequestMapping("/register")
@Slf4j
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping()
    public Result register(@RequestBody RegisterDTO registerDTO) {


        registerService.register(registerDTO);

        return Result.success("注册成功!");
    }
}
