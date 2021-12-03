package tourGuide.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.dto.LocationDTO;
import tourGuide.model.Location;
import tourGuide.util.LocationMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLocationMapper {

    private LocationMapper attractionMapper = new LocationMapper();

   

    
	@Test
	public void testToLocation() {

	
		Location location = new Location();
		location.setLatitude(-117.149048);
		location.setLongitude(33.817595);
		

        Location testLocation = new Location(
        		-117.149048,
        		33.817595);
    	
        LocationDTO testLocationDTO = new LocationDTO(
        		-117.149048,
        		33.817595);

    	Location result = attractionMapper.toLocation(testLocationDTO);

    	assertThat(result).isEqualToComparingFieldByField(testLocation);
	}



	// ##############################################################

	  
		@Test
		public void testToLocationDTO() {

		
			Location location = new Location();
			location.setLatitude(-117.149048);
			location.setLongitude(33.817595);
			

	        Location testLocation = new Location(
	        		-117.149048,
	        		33.817595);
	    	

	    	LocationDTO result = attractionMapper.toLocationDTO(testLocation);

	    	assertThat(result).isEqualToComparingFieldByField(testLocation);

		}



		// ##############################################################


}
