package com.example.demo.controller;


import com.example.demo.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wenda")

public class WendaController {


    @PostMapping("/send")
    public Result SendMassage(@RequestBody Map<String, Object> requestData) {
        System.out.println("接收到的字符串: " + requestData);
        Integer id = (Integer) requestData.get("id");
        String content = (String) requestData.get("content");
        String txt = id + " : " + content;


        return Result.success(txt);

    }



}
