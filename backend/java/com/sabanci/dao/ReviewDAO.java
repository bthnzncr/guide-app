package com.sabanci.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sabanci.model.Review;

import java.util.List;

public interface ReviewDAO extends MongoRepository<Review, String> {
    List<Review> findByNickname(String nickname);
    List<Review> findByPlaceId(String placeId);
    
    Review findByPlaceIdAndNickname(String placeId, String nickname);
   
}