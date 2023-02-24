package tripDeals.unit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tripDeals.dto.ProviderDTO;
import tripDeals.util.ProviderMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProviderMapperTest {


    private ProviderMapper attractionMapper = new ProviderMapper();

 

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
	        		1500);
	    	

	    	ProviderDTO result = attractionMapper.toProviderDTO(testProvider);

	    	assertThat(result).isEqualToComparingFieldByField(testProvider);

		}



		// ##############################################################

}
