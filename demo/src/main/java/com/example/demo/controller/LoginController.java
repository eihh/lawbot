package com.example.demo.controller;

import com.example.demo.pojo.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.common.Result;

@RestController
@Slf4j
@RequestMapping
public class LoginController {


    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String auth = loginRequest.getAuth();

        // 校验用户名、密码和验证码
        if (username.equals("123") || password.equals(123456)|| auth != null) {
            return Result.success("登录成功");
        }else {
            return Result.error("登录失败");
        }

        // 处理登录逻辑

    }
}
