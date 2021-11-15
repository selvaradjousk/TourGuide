package tourGuide.service;

import java.util.List;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tripPricer.Provider;

public interface ITourGuideService {

	List<UserReward> getUserRewards(User user);

	VisitedLocation getUserLocation(User user);

	User getUser(String userName);

	List<User> getAllUsers();

	void addUser(User user);

	List<Provider> getTripDeals(User user);

	VisitedLocation trackUserLocation(User user);

	List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

}