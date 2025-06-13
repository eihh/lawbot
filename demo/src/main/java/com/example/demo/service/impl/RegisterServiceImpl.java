package com.example.demo.service.impl;

import com.example.demo.exception.ServiceException;
import com.example.demo.pojo.User;
import com.example.demo.pojo.dto.RegisterDTO;
import com.example.demo.repository.RegisterRepository;
import com.example.demo.service.RegisterService;
import com.example.demo.utils.AutoIncrementUserIdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private AutoIncrementUserIdUtils autoIncrementUserIdUtils;
    @Override
    public User register(RegisterDTO registerDTO) {
        //正则表达式校验
        //用户名 数字 字母 3-10位
        //密码 数字 字母 6-10位
        String usernamePattern = "^[a-zA-Z0-9]{3,10}$";
        String passwordPattern = "^[a-zA-Z0-9]{6,10}$";

        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();

        //判断用户名是否已经存在
        User user = registerRepository.findUserByUsername(username);
        if(user != null){
            throw new ServiceException(500,"用户名已存在");
        }

        if(!username.matches(usernamePattern)){
            throw new ServiceException(500,"用户名格式错误");
        }
        else if(!password.matches(passwordPattern)){
            throw new ServiceException(500,"密码格式错误");
        }
        else {
            user = new User();
            BeanUtils.copyProperties(registerDTO,user);
            long userId = autoIncrementUserIdUtils.getNextId("userSeq");
            user.setUserId((int) userId);
            //保存到数据库中
            registerRepository.save(user);
        }
        return user;
    }
}
