package com.smartwater.demo.DAO;

import com.smartwater.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper
{
    User validateUser(String username,String password);
}
