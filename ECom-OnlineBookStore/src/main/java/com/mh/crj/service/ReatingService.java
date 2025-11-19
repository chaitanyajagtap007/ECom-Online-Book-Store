package com.mh.crj.service;

import java.util.List;

import com.mh.crj.entity.Rating;
import com.mh.crj.model.ReatingDto;

public interface ReatingService {

	public Rating saveReating(ReatingDto reatingDto);

	public Rating updadteReating(ReatingDto reatingDto);

	public List<Rating> fetchAllReating();

	public String deleteReating(Long userId,Long bookId);

}
