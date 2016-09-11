package com.boe.service.impl;

import com.boe.service.inter.domain.User;
import com.boe.service.inter.service.UserService;
import com.boe.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Boe on 2016-09-10.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
}
