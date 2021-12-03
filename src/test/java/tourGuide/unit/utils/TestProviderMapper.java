package tourGuide.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.dto.ProviderDTO;
import tourGuide.model.Provider;
import tourGuide.util.ProviderMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProviderMapper {

    private ProviderMapper attractionMapper = new ProviderMapper();

   

    
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
