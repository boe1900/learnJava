package com.boe.service.impl;

import com.boe.service.inter.domain.User;
import com.boe.service.inter.domain.collections.UserDocument;
import com.boe.service.inter.service.UserService;
import com.boe.mapper.UserMapper;
import com.boe.mongo.dao.UserMongoDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Boe on 2016-09-10.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMongoDao userMongoDao;

    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    public void insertUser2Mongo(UserDocument user) {
        userMongoDao.save(user);
    }
}
