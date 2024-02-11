package com.sabanci.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class User {
    @Id
    private String email;
    private String name;
    
    @Indexed(unique = true)
    private String nickname;
   
    private List<String> favoritePlaceIds;
    
    
	public User( String nickname, String name, String email) {
		super();
		this.nickname = nickname;
		this.name = name;
		this.email = email;
	}
	
		 public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

		public User() {
		        super();
		        this.favoritePlaceIds = new ArrayList<>(); 
		    }
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getFavoritePlaceIds() {
		return favoritePlaceIds;
	}
	public void setFavoritePlaceIds(List<String> favoritePlaceIds) {
		this.favoritePlaceIds = favoritePlaceIds;
	}
	

    
}
