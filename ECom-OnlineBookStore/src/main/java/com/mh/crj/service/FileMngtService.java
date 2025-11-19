package com.mh.crj.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileMngtService {

	public String storeFile(MultipartFile file) throws IOException;
}
