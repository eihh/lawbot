package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.pojo.dto.DialogDTO;
import com.example.demo.service.DialogHistoryService;
import com.example.demo.utils.JwtUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wenda")



public class WendaController {

    @Autowired
    private DialogHistoryService dialogHistoryService;




    @PostMapping("/send")
    public Result SendMassage(@RequestBody Map<String, Object> requestData,
                              @RequestHeader String jwtToken) throws IOException, InterruptedException {
        System.out.println("接收到的字符串: " + requestData);

        String conversation_id = (String) requestData.get("id");
        String content = (String) requestData.get("content");

        //对话的用户名
        String username = JwtUtils.getUsernameFromToken(jwtToken);


        DialogDTO d1 = new DialogDTO();
        d1.setContent(content);
        d1.setRole(1);
        dialogHistoryService.save(d1,username);




        //构建请求
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            data.put("conversation_id",conversation_id);
            data.put("instruction", content);
            data.put("max_turns", "10");
            String jsonBody = mapper.writeValueAsString(data);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:7860/chat"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // 发送请求并处理响应

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());
            System.out.println("Status code: " + response.statusCode());

            System.out.println("Response body: " + response.body());


            // 创建 ObjectMapper 实例
            ObjectMapper mapper1 = new ObjectMapper();
            // 解析 JSON 字符串
            JsonNode rootNode = mapper.readTree(response.body());
            // 获取 response 字段的值
            String text = rootNode.get("response").asText();
            String conversation_id_new = rootNode.get("conversation_id").asText();
            // 去除前缀 "</think>\n\n"
            String cleanedResponse = text.replaceFirst("^</think>\\s*", "");
            // 打印处理后的内容
            System.out.println("处理后的内容：\n" + cleanedResponse);


            //String txt = id + " : " + content;

            //返回json数据同时带回conversation_id

            data.put("conversation_id", conversation_id_new);
            data.put("message", cleanedResponse);

            //通过jwt获取用户名构造dialog 存入数据库
            DialogDTO d2 = new DialogDTO();
            d2.setContent(cleanedResponse);
            d2.setRole(0);
            dialogHistoryService.save(d2,username);

            String response_jsonBody = mapper.writeValueAsString(data);

            return Result.success(response_jsonBody);


        }catch (Exception e){
            return Result.error(e.getMessage());
        }



    }



}
