package com.mh.crj.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mh.crj.entity.mongo.CustomerMongo;

public interface CustomerMongoRepo extends MongoRepository<CustomerMongo, String> {

}
