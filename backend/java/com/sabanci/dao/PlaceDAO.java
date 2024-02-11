package com.sabanci.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.sabanci.model.Place;

import java.util.List;



public interface PlaceDAO extends MongoRepository<Place, String> {
	public List<Place> findByNameContainsIgnoreCase(String name);
	public List<Place> findByCategoryContainsIgnoreCase(String category);
	public List<Place> findByNeighborhoodContainsIgnoreCase(String neighborhood);
	public List<Place> findByDistrict(String district);
	public List<Place> findByDistrictAndCategoryAndNeighborhood(String district, String category, String neighborhood);
	public List<Place> findByDistrictAndCategory(String district, String category);
	public List<Place> findByDistrictAndNeighborhood(String district, String neighborhood);
	Place findByPlaceId(String placeId);
	public List<Place> findByCategoryAndNeighborhood(String category, String neighborhood);
	
	
}
