package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reload")
public class ReloadController {

    @PostMapping
    public Result Reload(@RequestBody Map<String, String> payload) throws IOException, InterruptedException {
        String content = payload.get("content");
        System.out.println("接收到字符串内容：" + content);

        try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> data = new HashMap<>();
                data.put("task", content);
                String jsonBody = mapper.writeValueAsString(data);


                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:7860/reload"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                // 发送请求并处理响应

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(
                        request, HttpResponse.BodyHandlers.ofString());

                System.out.println("Status code: " + response.statusCode());
                System.out.println("Response body: " + response.body());


                return Result.success("模型挂载成功");


        }catch ( Exception e ) {
            throw new ServiceException(500,"挂载模型异常");
        }


    }






}
