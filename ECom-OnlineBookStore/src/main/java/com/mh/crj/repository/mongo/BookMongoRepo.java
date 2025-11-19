package com.mh.crj.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mh.crj.entity.mongo.BookMongo;

public interface BookMongoRepo extends MongoRepository<BookMongo, String> {

}
