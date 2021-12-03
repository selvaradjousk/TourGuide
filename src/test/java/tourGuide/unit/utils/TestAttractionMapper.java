package tourGuide.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.dto.AttractionDTO;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.util.AttractionMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAttractionMapper {

    private AttractionMapper attractionMapper = new AttractionMapper();

   

    
	@Test
	public void testToAttraction() {

		UUID attractionID = UUID.randomUUID();
		
		Location location = new Location();
		location.setLatitude(-117.149048);
		location.setLongitude(33.817595);
		

        Attraction testAttraction = new Attraction(
          		attractionID,
        		"San Diego Zoo",
        		"San Diego",
                "CA",
                location);
    	
        AttractionDTO testAttractionDTO = new AttractionDTO(
        		attractionID,
        		"San Diego Zoo",
        		"San Diego",
                "CA",
                location);

    	Attraction result = attractionMapper.toAttraction(testAttractionDTO);

    	assertThat(result).isEqualToComparingFieldByField(testAttraction);
	}



	// ##############################################################

	  
		@Test
		public void testToAttractionDTO() {
			
			Location location = new Location();
			location.setLatitude(-117.149048);
			location.setLongitude(33.817595);
			

			gpsUtil.location.Attraction testAttraction = new gpsUtil.location.Attraction(
	        		"San Diego Zoo",
	        		"San Diego",
	                "CA",
	                -117.149048,
	                33.817595);
			

	    	AttractionDTO result = attractionMapper.toAttractionDTO(testAttraction);

	        assertNotNull(result);
	        assertEquals(testAttraction.attractionId, result.getAttractionId());
	        assertEquals(testAttraction.attractionName, result.getAttractionName());
	        assertEquals(testAttraction.city, result.getCity());
	        assertEquals(testAttraction.state, result.getState());

		}



		// ##############################################################


}
