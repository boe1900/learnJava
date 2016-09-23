package com.boe.service.inter.service;

import com.boe.service.inter.domain.User;
import com.boe.service.inter.domain.collections.UserDocument;

/**
 * Created by Boe on 2016-09-10.
 */
public interface UserService {
    User getUserById(Long id);

    void insertUser2Mongo(UserDocument user);
}
