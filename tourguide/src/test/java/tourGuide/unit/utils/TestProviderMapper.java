package tourGuide.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.ProviderDTO;
import tourGuide.model.Provider;
import tourGuide.util.ProviderMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestProviderMapper {

    private ProviderMapper attractionMapper = new ProviderMapper();

   

    @DisplayName("Check <testToProvider"
    		+ " - Given DTO,"
    		+ " when ToProvider,"
    		+ " then return DO expected")
	@Test
	public void testToProvider() {

		UUID tripID = UUID.randomUUID();
		
        Provider testProvider = new Provider(
        		"testProvider",
        		100,
        		tripID);
    	
        ProviderDTO testProviderDTO = new ProviderDTO(
        		"testProvider",
        		100,
        		tripID);
    	

    	Provider result = attractionMapper.toProvider(testProviderDTO);

    	assertThat(result).isEqualToComparingFieldByField(testProvider);
	}



	// ##############################################################

    @DisplayName("Check <testToProvider>"
    		+ " - Given DO,"
    		+ " when ToProviderDTO,"
    		+ " then return DO expected")
		@Test
		public void testToProviderDTO() {

			UUID tripID = UUID.randomUUID();


			tripPricer.Provider testProvider = new tripPricer.Provider(
	        		tripID,
	        		"testProvider",
	        		100);
	    	

	    	ProviderDTO result = attractionMapper.toProviderDTO(testProvider);

	    	assertThat(result).isEqualToComparingFieldByField(testProvider);

		}



		// ##############################################################


}
