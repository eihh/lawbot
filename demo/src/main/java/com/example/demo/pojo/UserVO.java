package com.example.demo.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVO {
    private Integer userId;
    private String username;
    private String token;
}
