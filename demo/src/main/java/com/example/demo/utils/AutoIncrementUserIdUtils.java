package com.example.demo.utils;

import com.example.demo.pojo.userIdSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class AutoIncrementUserIdUtils {
    @Autowired
    private MongoTemplate mongoTemplate;

    //改造函数
    public AutoIncrementUserIdUtils(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public long getNextId(String collName) {
        userIdSequence seq = mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(collName)),
                new Update().inc("userId", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                userIdSequence.class);
        if(seq == null){return 1;}
        return seq.getUserId();
    }
}
