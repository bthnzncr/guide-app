package com.sabanci.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sabanci.model.Event;

import java.util.List;

public interface EventDAO extends MongoRepository<Event, String> {
	public List<Event> findByDate(String date);
	public List<Event> findByCategoryContainsIgnoreCase(String category);
	public List<Event> findByNeighborhood(String neighborhood);
	public List<Event> findByDistrict(String district);
    public List<Event> findByDistrictAndCategoryAndNeighborhood(String district, String category, String neighborhood);
	public List<Event> findByDistrictAndCategory(String district, String category);
	public List<Event> findByDistrictAndNeighborhood(String district, String neighborhood);
	public List<Event> findByEventId(String eventId);
	public List<Event> findByCategoryAndNeighborhood(String category, String neighborhood);
}
