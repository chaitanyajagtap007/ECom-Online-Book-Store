package com.mh.crj.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mh.crj.entity.mongo.FIlesMongo;

public interface FilesMongoRepo extends MongoRepository<FIlesMongo, String> {

}
