package gps.unit.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.util.GpsMapper;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Unit Test - Mapper - Gps Microservice")
public class gpsMapper {

	private GpsMapper gpsMapper = new GpsMapper();
	

    @DisplayName("Check <testToVisitedLocation>"
    		+ " - Given DTO,"
    		+ " when ToVisitedLocation,"
    		+ " then return DO expected")
	@Test
	public void testToVisitedLocation() {

    	
    	UUID userID = UUID.randomUUID();
    	Date date = new Date();

        VisitedLocation testVisitedLocation = new VisitedLocation(
        		userID,
        		new Location(-117.149048,
        		33.817595),
        		date);
    	

    	VisitedLocationDTO result = gpsMapper
    			.toVisitedLocationDTO(testVisitedLocation);

        assertNotNull(result.getLocation());
    	assertEquals(userID, result.getUserId());
        assertEquals(testVisitedLocation.location.latitude, result.getLocation().getLatitude());
        assertEquals(testVisitedLocation.location.longitude, result.getLocation().getLongitude());
        assertEquals(date, result.getTimeVisited());
	}



	// ##############################################################


    @DisplayName("Check <testToVisitedLocation> null visited location"
    		+ " - Given DTO null,"
    		+ " when ToVisitedLocation,"
    		+ " then return DO expected")
	@Test
	public void testToVisitedLocationNullValueInput() {

    	
        assertThrows(NullPointerException.class, ()
        		-> gpsMapper.toVisitedLocationDTO(null));
	}


    
    

	// ##############################################################

    @DisplayName("Check <testToAttraction>"
    		+ " - Given DO,"
    		+ " when ToAttractionDTO,"
    		+ " then return DTO expected")
		@Test
		public void testToAttractionDTO() {
			
			

    	Attraction testAttraction = new Attraction(
	        		"San Diego Zoo",
	        		"San Diego",
	                "CA",
	                -117.149048,
	                33.817595);
			

	    	AttractionDTO result = gpsMapper.toAttractionDTO(testAttraction);

	        assertNotNull(result);
	        assertEquals(testAttraction.attractionId, result.getAttractionId());
	        assertEquals(testAttraction.attractionName, result.getAttractionName());
	        assertEquals(testAttraction.city, result.getCity());
	        assertEquals(testAttraction.state, result.getState());

		}
    
    

	// ##############################################################



    @DisplayName("Check <testToAttraction>"
    		+ " - Given DO,"
    		+ " when ToAttractionDTO,"
    		+ " then return DTO expected")
		@Test
		public void testToAttractionDTONullException() {
			

    	 assertThrows(NullPointerException.class, ()
         		->  gpsMapper.toAttractionDTO(null));

		}
    
     
    

	// ##############################################################

		
}
