package com.example.demo.controller;


import com.example.demo.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/wenshu")

public class WenShuController {


    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            return Result.error("空文件");
        }

        try {
            String textContent = extractTextFromFile(file);

            //return Result.success(textContent);

             //构建请求
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            data.put("document", textContent);

            String jsonBody = mapper.writeValueAsString(data);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:7860/summarize"))
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


        } catch (UnsupportedOperationException e) {

            return Result.error("不支持的文件类型");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("文件解析失败");
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




