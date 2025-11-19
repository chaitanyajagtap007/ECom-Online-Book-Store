package com.mh.crj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mh.crj.entity.FilesEntity;

@Repository
public interface FileRepo extends JpaRepository<FilesEntity, Long>{

}
