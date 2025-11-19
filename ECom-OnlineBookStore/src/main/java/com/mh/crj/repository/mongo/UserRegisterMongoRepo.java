package com.mh.crj.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mh.crj.entity.mongo.UserRegisterMongo;

public interface UserRegisterMongoRepo extends MongoRepository<UserRegisterMongo, String> {

}
