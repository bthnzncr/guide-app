package com.sabanci.dao;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.sabanci.model.User;

public interface UserDAO extends MongoRepository<User, String>{
	
	
	User findByNickname(String nickname);
	User findByEmail(String email);
	
}
;