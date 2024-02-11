package com.example.guideapp;

import java.util.ArrayList;
import java.util.List;

public class Place {

    private String placeId;


    private String name;
    private String description;
    private String category;
    private double latitude;
    private double longitude;
    private String neighborhood;
    private String district;
    private String text;
    private String images;
    private String sources;


    private List<Review> reviews;

    public Place() {
        super();
        this.reviews = new ArrayList<>();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Place(String placeId, String name, String description, String category, double latitude, double longitude,
                 String neighborhood, String district, String text, String images, String sources) {
        super();
        this.placeId = placeId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.neighborhood = neighborhood;
        this.district = district;
        this.text = text;
        this.images = images;
        this.sources = sources;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String PlaceId) {
        this.placeId =PlaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }


}