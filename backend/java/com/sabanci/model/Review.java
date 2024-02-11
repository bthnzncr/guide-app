package com.sabanci.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Review {
    @Id
    private String reviewId;
    
    
    private String placeId;
    
	private String nickname;
    private double rating;
    private String text;
    private LocalDateTime  date;
    

    public Review(String reviewId, String placeId, String nickname, double rating, String text) {
        super();
        this.reviewId = reviewId;
        
        this.placeId = placeId;
        this.nickname = nickname;
        this.rating = rating;
        this.text = text;
        this.date = LocalDateTime.now(); 
    }
	 public Review() {
	        super();
	        this.date = LocalDateTime.now(); 
	    }
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LocalDateTime  getDate() {
		return date;
	}
	public void setDate(LocalDateTime  date) {
		this.date = date;
	}

}
