package com.example.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.PriorityQueue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialogDTO {
    private String username;
    private String content;
    private Integer role;

}
