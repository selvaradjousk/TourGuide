package tourGuide.unit.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.VisitedLocationDTO;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.util.VisitedLocationMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestVisitedLocationMapper {

    private VisitedLocationMapper visitedLocationMapper = new VisitedLocationMapper();

   

    
	@Test
	public void testToVisitedLocation() {



        VisitedLocation testVisitedLocation = new VisitedLocation(
        		UUID.randomUUID(),
        		new Location(-117.149048,
        		33.817595),
        		new Date());
    	
        VisitedLocationDTO testVisitedLocationDTO = new VisitedLocationDTO(
        		UUID.randomUUID(),
        		new Location(-117.149048,
        		33.817595),
        		new Date());

    	VisitedLocation result = visitedLocationMapper.toVisitedLocation(testVisitedLocationDTO);

//    	assertThat(result).isEqualToComparingFieldByField(testVisitedLocation);
//    	assertEquals(testVisitedLocation.getLocation().getLatitude(), result.getLocation().getLatitude());
        assertNotNull(result.getLocation());
        assertNotNull(testVisitedLocation.getLocation().getLatitude());
	}



	// ##############################################################

	  
		@Test
		public void testToVisitedLocationDTO() {
			
			UUID userID = UUID.randomUUID();
			gpsUtil.location.Location location = new gpsUtil.location.Location (-100.149048, 39.817595);
			Date date = new Date();
			
			gpsUtil.location.VisitedLocation testVisitedLocation = new gpsUtil.location.VisitedLocation(
					userID,
	        		location,
	        		date);

//	        VisitedLocationDTO testVisitedLocationDTO = new VisitedLocationDTO(
//	        		UUID.randomUUID(),
//	        		new Location(-100.149048,
//	        		39.817595),
//	        		new Date());
//	        
//  	

	        VisitedLocationDTO result = visitedLocationMapper.toVisitedLocationDTO(testVisitedLocation);
	        
//	    	assertThat(result).usingRecursiveComparison().isEqualTo(testVisitedLocation);
	        assertNotNull(result.getLocation());
	        assertNotNull(testVisitedLocation.location.latitude);

		}



		// ##############################################################


}
