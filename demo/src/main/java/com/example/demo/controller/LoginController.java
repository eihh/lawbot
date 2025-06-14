package com.example.demo.controller;

import com.example.demo.pojo.LoginRequest;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserVO;
import com.example.demo.service.LoginService;
import com.example.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.common.Result;

import java.util.HashMap;

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

        User user = loginService.login(username, password);
        if(user == null){
            return Result.error("登录失败");
        }

        //登录成功就发放jwt令牌
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("username", username);

        String jwtToken = JwtUtils.generateJwt(claims);
        System.out.println("本次登录产生的jwt令牌:{}" + jwtToken);

        UserVO userVO = UserVO.builder()
                .userId(user.getUserId())
                .username(username)
                .token(jwtToken)
                .build();

        return Result.success(userVO);

//        User user = loginService.login(username, password);
//        if (user != null) {
//            return Result.success("登录成功");
//        }
//        else {
//            return Result.error("登录失败");
//        }
    }
}
