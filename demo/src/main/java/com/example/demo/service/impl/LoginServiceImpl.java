package com.example.demo.service.impl;

import com.example.demo.exception.ServiceException;
import com.example.demo.pojo.User;
import com.example.demo.repository.LoginRepository;
import com.example.demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.common.Constants.CODE_500;




@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginRepository loginRepository;
    @Override
    public User login(String username, String password) {
        User user = loginRepository.findByUsername(username);
        //判断是否存在
        if(user == null){
            //log.info("用户不存在");
            throw new ServiceException(CODE_500,"用户不存在");
        }

        //判断密码是否正确
        if(!user.getPassword().equals(password)){
            //log.info("密码错误");
            throw new ServiceException(CODE_500,"密码错误");
        }
        return user;
    }
}
