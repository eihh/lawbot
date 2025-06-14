package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.utils.RequestContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/yuce")


public class YuceController {

    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            return Result.error("空文件");
        }

        //保存文件名到ThreadLocal
        RequestContextHolder.setFileName(RequestContextHolder.SCENE_YUCE,filename);

        try {
            String textContent = extractTextFromFile(file);
            System.out.println(textContent);
                return Result.success(textContent);

        } catch (UnsupportedOperationException e) {

            return Result.error("不支持的文件类型");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("文件解析失败");
        }
    }


    @PostMapping("/send")
    public Result handleRequest(@RequestBody String requestData) {
        System.out.println("接收到的字符串: " + requestData);

        //return Result.success(requestData);

//        // 构建请求
        try {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        data.put("fact", requestData);

        String jsonBody = mapper.writeValueAsString(data);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7860/prediction"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // 发送请求并处理响应

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response body: " + response.body());



            //返回结果
        return Result.success(response.body());



        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("错误");
        }



    }












    private String extractTextFromFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        if (filename == null) throw new UnsupportedOperationException("无效文件名");

        if (filename.endsWith(".txt")) {
            return readTxt(file.getInputStream());
        } else if (filename.endsWith(".pdf")) {
            return readPdf(file.getInputStream());
        } else if (filename.endsWith(".docx")) {
            return readDocx(file.getInputStream());
        } else {
            throw new UnsupportedOperationException("不支持的文件类型: " + filename);
        }
    }

    private String readTxt(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private String readPdf(InputStream is) throws IOException {
        try (PDDocument document = PDDocument.load(is)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }


    private String readDocx(InputStream is) throws IOException {
        try (XWPFDocument docx = new XWPFDocument(is)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
            return extractor.getText();
        }
    }
}






