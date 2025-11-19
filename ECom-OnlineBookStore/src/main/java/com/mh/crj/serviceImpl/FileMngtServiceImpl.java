package com.mh.crj.serviceImpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mh.crj.entity.FilesEntity;
import com.mh.crj.entity.mongo.FIlesMongo;
import com.mh.crj.repository.FileRepo;
import com.mh.crj.repository.mongo.FilesMongoRepo;
import com.mh.crj.service.FileMngtService;

@Service
public class FileMngtServiceImpl implements FileMngtService{

	@Autowired
	private FileRepo fileRepo; 
	
	@Autowired
	private FilesMongoRepo filesMongoRepo;
	
	@Override
	public String storeFile(MultipartFile file) throws IOException {

		FilesEntity entity = new FilesEntity();
		entity.setFileName(file.getOriginalFilename());
		entity.setFileType(file.getContentType());
		entity.setData(file.getBytes());
		fileRepo.save(entity);
		
		FIlesMongo fIlesMongo = new FIlesMongo();
		fIlesMongo.setFileName(file.getOriginalFilename());
		fIlesMongo.setFileType(file.getContentType());
		fIlesMongo.setData(file.getBytes());
		filesMongoRepo.save(fIlesMongo);
		return "file save with name : "+file.getOriginalFilename();
	}

	
	
}
