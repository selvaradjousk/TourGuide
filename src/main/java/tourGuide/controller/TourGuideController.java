package tourGuide.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import gpsUtil.location.VisitedLocation;
import tourGuide.model.User;
import tourGuide.service.ITourGuideService;
import tripPricer.Provider;

@RestController
public class TourGuideController {


	private Logger logger = LoggerFactory
			.getLogger(TourGuideController.class);

	@Autowired
	ITourGuideService tourGuideService;

    public TourGuideController(ITourGuideService tourGuideService) {
		super();
		this.tourGuideService = tourGuideService;
	}


	//###############################################################
	// PORT 					: 8080
	// Endpoint					: GET http://localhost:8080/
	// POSTMAN Endpoint status	: 200 OK
	// POSTMAN Response body	: Greetings from TourGuide!
	// More info				: 
	//###############################################################




	@RequestMapping("/")
    public String index() {

        logger.info("## index() page requested");

		return "Greetings from TourGuide!";
    }




	//###############################################################
	// PORT 					: 8080
	// Endpoint					: GET http://localhost:8080/getLocation?userName=internalUser1
	// POSTMAN Endpoint status	: 200 OK
	// POSTMAN Response body	: {"longitude":-11.776629,"latitude":79.513937}
	// More info				: 
	//###############################################################

    @RequestMapping("/getLocation") 
    public String getLocation(@RequestParam String userName) {

        logger.info("## getLocation() page requested"
        		+ " for user {} : ", userName);

    	VisitedLocation visitedLocation = tourGuideService
    			.getUserLocation(getUser(userName));

        logger.info("## visitedLocation {} "
        		+ " for user {} : ", visitedLocation, userName);

    	return JsonStream.serialize(visitedLocation.location);
    }




	//###############################################################
	// PORT 					: 8080
	// Endpoint					: GET http://localhost:8080/getNearbyAttractions?userName=internalUser1
	// POSTMAN Endpoint status	: 200 OK
	// POSTMAN Response body	: []
	// More info				:  !!! EMPTY ARRAY !!!  TODO Task
	//###############################################################

    //  TODO: Change this method to no longer return a List of Attractions.
 	//  Instead: Get the closest five tourist attractions to the user - no matter how far away they are.
 	//  Return a new JSON object that contains:
    	// Name of Tourist attraction, 
        // Tourist attractions lat/long, 
        // The user's location lat/long, 
        // The distance in miles between the user's location and each of the attractions.
        // The reward points for visiting each Attraction.
        //    Note: Attraction reward points can be gathered from RewardsCentral
    @RequestMapping("/getNearbyAttractions") 
    public String getNearbyAttractions(@RequestParam String userName) {

        logger.info("## getNearbyAttractions() page requested"
        		+ " for user {} : ", userName);

    	VisitedLocation visitedLocation = tourGuideService
    			.getUserLocation(getUser(userName));

        logger.info("## visitedLocation {} "
        		+ " for user {} : ", visitedLocation, userName);

    	return JsonStream.serialize(tourGuideService
    			.getNearByAttractions(visitedLocation));
    }




	//###############################################################
	// PORT 					: 8080
	// Endpoint					: GET http://localhost:8080/getRewards?userName=internalUser1
	// POSTMAN Endpoint status	: 200 OK
	// POSTMAN Response body	: []
	// More info				: !!! EMPTY ARRAY !!!
	//###############################################################

    @RequestMapping("/getRewards") 
    public String getRewards(@RequestParam String userName) {

        logger.info("## getRewards for user {} requested", userName);

    	return JsonStream.serialize(tourGuideService
    			.getUserRewards(getUser(userName)));
    }





	//###############################################################
	// PORT 					: 8080
	// Endpoint					: GET http://localhost:8080/getAllCurrentLocations
	// POSTMAN Endpoint status	: 200 OK
	// POSTMAN Response body	: ""
	// More info				: !!! EMPTY String !!! TODO Task
	//###############################################################

    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
    	// TODO: Get a list of every user's most recent location as JSON
    	//- Note: does not use gpsUtil to query for their current location, 
    	//        but rather gathers the user's current location from their stored location history.
    	//		SOLUTIONS: List<Location> userCurrentLocations, List<Location> userLocationHistory
    	// Return object should be the just a JSON mapping of userId to Locations similar to:
    	//     {
    	//        "019b04a9-067a-4c76-8817-ee75088c3822": {"longitude":-48.188821,"latitude":74.84371}
    	//        ...
    	//     }

        logger.info("## getAllCurrentLocations requested");
    	
//    	return JsonStream.serialize("");
        return JsonStream.serialize(tourGuideService.getAllUsersLastLocation());        
    }





	//###############################################################
	// PORT 					: 8080
	// Endpoint					: GET http://localhost:8080/getTripDeals?userName=internalUser1
	// POSTMAN Endpoint status	: 200 OK
	// POSTMAN Response body	: [{"name":"FlyAway Trips",
    //								"price":261.99,
    // 								"tripId":{
    // 										"mostSigBits":2020962664642464876,
    // 										"leastSigBits":-5580671602236548359,
    // 										"leastSignificantBits":-5580671602236548359,
    // 										"mostSignificantBits":2020962664642464876
    // 										}
    // 								},
    // 								{"name":"AdventureCo","price":337.99,"tripId":{"mostSigBits":2020962664642464876,"leastSigBits":-5580671602236548359,"leastSignificantBits":-5580671602236548359,"mostSignificantBits":2020962664642464876}},
    // 								{"name":"Enterprize Ventures Limited","price":306.99,"tripId":{"mostSigBits":2020962664642464876,"leastSigBits":-5580671602236548359,"leastSignificantBits":-5580671602236548359,"mostSignificantBits":2020962664642464876}},
    // 								{"name":"United Partners Vacations","price":493.99,"tripId":{"mostSigBits":2020962664642464876,"leastSigBits":-5580671602236548359,"leastSignificantBits":-5580671602236548359,"mostSignificantBits":2020962664642464876}},
    // 								{"name":"Holiday Travels","price":109.99,"tripId":{"mostSigBits":2020962664642464876,"leastSigBits":-5580671602236548359,"leastSignificantBits":-5580671602236548359,"mostSignificantBits":2020962664642464876}}]
	// More info				: 

    // 
	//###############################################################
    
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {

        logger.info("## getTripDeals"
        		+ " for user {} : ", userName);

    	List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));

        logger.info("## providers {} "
        		+ " for user {} : ", providers.size(), userName);

    	return JsonStream.serialize(providers);
    }





	//###############################################################
    // REQUEST MAPPING NEWLY ADDED FOR POSTMAN TEST PURPOSE ONLY
    // --------------------------------------------------------------
    // PORT 					: 8080
	// Endpoint					: GET http://localhost:8080/getUser?userName=internalUser1
	// POSTMAN Endpoint status	: 200 OK
	// POSTMAN Response body	: OUTPUT EXTRACT AS BELOW THE METHOD
	// More info				: UserRewards & TripDeals array are empty !!!
	//###############################################################    

    @RequestMapping("/getUser")
    private User getUser(String userName) {

        logger.info("## getUser "
        		+ " for user {} : ", userName);

    	return tourGuideService.getUser(userName);
    }
   
		    
		    /* {
		    "userId": "644960a4-4b33-4445-b8ce-4757c67ff6d9",
		    "userName": "internalUser1",
		    "phoneNumber": "000",
		    "emailAddress": "internalUser1@tourGuide.com",
		    "latestLocationTimestamp": null,

		    "visitedLocations": [
		        {
		            "userId": "644960a4-4b33-4445-b8ce-4757c67ff6d9",
		            "location": {
		                "longitude": 32.144604268919124,
		                "latitude": 47.38217151784272
		            },
		            "timeVisited": "2021-11-02T13:06:12.074+0000"
		        },
		        {
		            "userId": "644960a4-4b33-4445-b8ce-4757c67ff6d9",
		            "location": {
		                "longitude": -12.286082931615795,
		                "latitude": 19.835172057077244
		            },
		            "timeVisited": "2021-11-01T13:06:12.074+0000"
		        },
		        {
		            "userId": "644960a4-4b33-4445-b8ce-4757c67ff6d9",
		            "location": {
		                "longitude": 139.27310742909873,
		                "latitude": 31.727019127142498
		            },
		            "timeVisited": "2021-11-03T13:06:12.074+0000"
		        }
		    ],

		    "userRewards": [],
		    
		    "userPreferences": {
		        "attractionProximity": 2147483647,
		        "lowerPricePoint": {
		            "currency": {
		                "context": {
		                    "empty": false,
		                    "providerName": "java.util.Currency"
		                },
		                "currencyCode": "USD",
		                "defaultFractionDigits": 2,
		                "numericCode": 840
		            },
		            "number": 0,
		            "factory": {
		                "defaultMonetaryContext": {
		                    "precision": 0,
		                    "maxScale": 63,
		                    "amountType": "org.javamoney.moneta.Money",
		                    "fixedScale": false,
		                    "empty": false,
		                    "providerName": null
		                },
		                "maxNumber": null,
		                "minNumber": null,
		                "amountType": "org.javamoney.moneta.Money",
		                "maximalMonetaryContext": {
		                    "precision": 0,
		                    "maxScale": -1,
		                    "amountType": "org.javamoney.moneta.Money",
		                    "fixedScale": false,
		                    "empty": false,
		                    "providerName": null
		                }
		            },
		            "context": {
		                "precision": 256,
		                "maxScale": -1,
		                "amountType": "org.javamoney.moneta.Money",
		                "fixedScale": false,
		                "empty": false,
		                "providerName": null
		            },
		            "zero": true,
		            "positive": false,
		            "negative": false,
		            "negativeOrZero": true,
		            "positiveOrZero": true,
		            "numberStripped": 0
		        },

		        "highPricePoint": {
		            "currency": {
		                "context": {
		                    "empty": false,
		                    "providerName": "java.util.Currency"
		                },
		                "currencyCode": "USD",
		                "defaultFractionDigits": 2,
		                "numericCode": 840
		            },
		            "number": 2147483647,
		            "factory": {
		                "defaultMonetaryContext": {
		                    "precision": 0,
		                    "maxScale": 63,
		                    "amountType": "org.javamoney.moneta.Money",
		                    "fixedScale": false,
		                    "empty": false,
		                    "providerName": null
		                },
		                "maxNumber": null,
		                "minNumber": null,
		                "amountType": "org.javamoney.moneta.Money",
		                "maximalMonetaryContext": {
		                    "precision": 0,
		                    "maxScale": -1,
		                    "amountType": "org.javamoney.moneta.Money",
		                    "fixedScale": false,
		                    "empty": false,
		                    "providerName": null
		                }
		            },
		            "context": {
		                "precision": 256,
		                "maxScale": -1,
		                "amountType": "org.javamoney.moneta.Money",
		                "fixedScale": false,
		                "empty": false,
		                "providerName": null
		            },
		            "zero": false,
		            "positive": true,
		            "negative": false,
		            "negativeOrZero": false,
		            "positiveOrZero": true,
		            "numberStripped": 2147483647
		        },
		        "tripDuration": 1,
		        "ticketQuantity": 1,
		        "numberOfAdults": 1,
		        "numberOfChildren": 0
		    },

		    "tripDeals": [],

		    "lastVisitedLocation": {
		        "userId": "644960a4-4b33-4445-b8ce-4757c67ff6d9",
		        "location": {
		            "longitude": 139.27310742909873,
		            "latitude": 31.727019127142498
		        },
		        "timeVisited": "2021-11-03T13:06:12.074+0000"
		    }
		}
		*/

}