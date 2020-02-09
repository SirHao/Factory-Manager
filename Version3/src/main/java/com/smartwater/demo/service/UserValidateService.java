package com.smartwater.demo.service;


import com.smartwater.demo.DAO.UserMapper;
import com.smartwater.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
public class UserValidateService implements Serializable
{
    private static  final long serialVersionUID = 1L;
    @Autowired
    UserMapper userMapper;

    public  int  validatebyName(String username,String password)
    {
        User user = userMapper.validateUser(username,password);
        if(user == null)
            return 0;
        else
            return 1;
    }
}
