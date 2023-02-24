package tourGuide.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.LocationDTO;
import tourGuide.model.Location;
import tourGuide.util.LocationMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLocationMapper {

    private LocationMapper attractionMapper = new LocationMapper();

   

    @DisplayName("Check <testToLocation>"
    		+ " - Given DTO,"
    		+ " when ToVisitedLocation,"
    		+ " then return DO expected")
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

    @DisplayName("Check <testToLocationDTO>"
    		+ " - Given DO,"
    		+ " when ToVisitedLocation,"
    		+ " then return DTO expected")
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
