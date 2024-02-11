package com.example.guideapp;


public class Review {

    private String reviewId;


    private String placeId;

    private String nickname;
    private float rating;
    private String text;


    public Review(String reviewId, String placeId, String nickname, float rating, String text) {
        super();
        this.reviewId = reviewId;

        this.placeId = placeId;
        this.nickname = nickname;
        this.rating = rating;
        this.text = text;

    }
    public Review() {
        super();

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

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

}
