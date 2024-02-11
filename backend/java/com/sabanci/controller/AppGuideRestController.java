package com.sabanci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sabanci.dao.EventDAO;
import com.sabanci.dao.PlaceDAO;
import com.sabanci.dao.ReviewDAO;
import com.sabanci.dao.UserDAO;
import com.sabanci.model.Event;
import com.sabanci.model.Place;
import com.sabanci.model.Review;
import com.sabanci.model.User;

import java.util.ArrayList;

import java.util.List;



@RestController
@RequestMapping("/")
public class AppGuideRestController {

    @Autowired
    private PlaceDAO placeDAO;
    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ReviewDAO reviewDAO;
    
   
    
    @GetMapping("/places/filter")
    public ResponseEntity<List<Place>> getFilteredPlaces(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String neighborhood) {
        
        List<Place> places;

        if (district != null && category != null && neighborhood != null) {
            places = placeDAO.findByDistrictAndCategoryAndNeighborhood(district, category, neighborhood);
        } else if (district != null && category != null) {
            places = placeDAO.findByDistrictAndCategory(district, category);
        } else if (neighborhood != null && category != null) {
            places = placeDAO.findByCategoryAndNeighborhood(category, neighborhood);
        }else if (district != null && neighborhood != null) {
            places = placeDAO.findByDistrictAndNeighborhood(district, neighborhood);
        } else if (district != null) {
            places = placeDAO.findByDistrict(district);
        } else if (category != null) {
            places = placeDAO.findByCategoryContainsIgnoreCase(category);
        } else if (neighborhood != null) {
            places = placeDAO.findByNeighborhoodContainsIgnoreCase(neighborhood);
        } else {
            places = placeDAO.findAll();
        }

        return ResponseEntity.ok(places);
    }
    
    @GetMapping("/events/filter")
    public ResponseEntity<List<Event>> getFilteredEvents(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String neighborhood) {
        
        List<Event> events;

        if (district != null && category != null && neighborhood != null) {
            events = eventDAO.findByDistrictAndCategoryAndNeighborhood(district, category, neighborhood);
        } else if (district != null && category != null) {
            events = eventDAO.findByDistrictAndCategory(district, category);
        } else if (district != null && neighborhood != null) {
            events = eventDAO.findByDistrictAndNeighborhood(district, neighborhood);
        } else if (neighborhood != null && category != null) {
            events = eventDAO.findByCategoryAndNeighborhood(category, neighborhood);
        }else if (district != null) {
            events = eventDAO.findByDistrict(district);
        } else if (category != null) {
            events = eventDAO.findByCategoryContainsIgnoreCase(category);
        } else if (neighborhood != null) {
            events = eventDAO.findByNeighborhood(neighborhood);
        } 
        else {
            events = eventDAO.findAll();
        }

        return ResponseEntity.ok(events);
    }

  
   
   

   
    @GetMapping("/{nickname}/favorites")
    public ResponseEntity<?> getUserFavorites(@PathVariable String nickname) {
        User user = userDAO.findByNickname(nickname);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
        return ResponseEntity.ok(user.getFavoritePlaceIds());
        }
    }

    @PostMapping("/{nickname}/favorites")
    public ResponseEntity<?> toggleUserFavorite(@PathVariable String nickname, @RequestParam String placeId) {
        User user = userDAO.findByNickname(nickname);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Place place = placeDAO.findByPlaceId(placeId);
        if (place == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Place not found");
        }
        if (user.getFavoritePlaceIds() == null) {
            user.setFavoritePlaceIds(new ArrayList<>());
        }
        
        if (user.getFavoritePlaceIds().contains(placeId)) {         
            user.getFavoritePlaceIds().remove(placeId);
        } 
        else {
            
            user.getFavoritePlaceIds().add(placeId);
        }
        userDAO.save(user);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{placeId}/reviews")
    public ResponseEntity<?> postReviewForPlace(@PathVariable String placeId, @RequestBody Review review) {
    	 Place place = placeDAO.findByPlaceId(placeId);
    	 if (place == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Place not found");
    	 }
    	 User user = userDAO.findByNickname(review.getNickname());
         if (user == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
         }
        Review existingReview = reviewDAO.findByPlaceIdAndNickname(placeId, review.getNickname());
        if (existingReview != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You've already posted a review.");
        }

        review.setPlaceId(placeId);
        
        Review savedReview = reviewDAO.save(review); 

       
        
        if (place != null) {
            place.getReviews().add(savedReview); 
            placeDAO.save(place);
        }

        return ResponseEntity.ok(savedReview);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        User existingUser = userDAO.findByEmail(newUser.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email already exists");
        }
        User existing = userDAO.findByNickname(newUser.getNickname());
        if (existing!= null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname is already taken");
        }
        User savedUser = userDAO.save(newUser);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{placeId}/reviews")
    public ResponseEntity<?> getReviewsForPlace(@PathVariable String placeId) {
    	Place place = placeDAO.findByPlaceId(placeId);
    	if (place == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Place not found");
   	 }
        List<Review> reviews = reviewDAO.findByPlaceId(placeId);
        if (reviews.isEmpty()) {
        	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No review found");
        }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/places/save")
    public ResponseEntity<?> savePlace(@RequestBody Place place) {
        Place savedPlace = placeDAO.save(place);
        return ResponseEntity.ok(savedPlace);
    }

    
    @PostMapping("/events/save")
    public ResponseEntity<?> saveEvent(@RequestBody Event event) {
        Event savedEvent = eventDAO.save(event);
        return ResponseEntity.ok(savedEvent);
    }
  
    @GetMapping("/users")
    public ResponseEntity<?> getUser() {
        List<User> users = userDAO.findAll();
       
        return ResponseEntity.ok(users);
    }
  
}