package com.example.demo.controller;

import com.example.demo.pojo.LoginRequest;
import com.example.demo.pojo.User;
import com.example.demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.common.Result;

@RestController
@Slf4j
@RequestMapping
public class LoginController {
    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        return Result.success("登录成功");

//        User user = loginService.login(username, password);
//        if (user != null) {
//            return Result.success("登录成功");
//        }
//        else {
//            return Result.error("登录失败");
//        }



    }
}
