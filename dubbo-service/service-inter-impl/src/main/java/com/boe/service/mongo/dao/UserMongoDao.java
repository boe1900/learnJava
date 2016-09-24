package com.boe.service.mongo.dao;

import com.boe.service.inter.domain.collections.UserDocument;
import com.boe.service.mongo.base.MongodbBaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Boe on 2016-09-20.
 */
@Repository
public class UserMongoDao extends MongodbBaseDao<UserDocument> {

    @Override
    public Class<UserDocument> getEntityClass() {
        return UserDocument.class;
    }
}
