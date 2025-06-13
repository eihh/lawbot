package com.example.demo.service;

import com.example.demo.pojo.User;
import com.example.demo.pojo.dto.RegisterDTO;

public interface RegisterService {
    User register(RegisterDTO registerDTO);
}
