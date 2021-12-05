package tourGuide.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.UserRewardDTO;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.util.UserRewardMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestUserRewardMapper {

    private UserRewardMapper userRewardMapper = new UserRewardMapper();

   


	// ##############################################################

	  
		@Test
		public void testToUserRewardDTO() {

		       VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(-120.149048, 33.735317), new Date());
		        Attraction attraction = new Attraction(UUID.randomUUID(), "San Diego Zoo" , "San Diego" ,
		                "CA" , new Location(-117.149048, 32.735317));
		        UserRewardDTO testUserRewardDTO = new UserRewardDTO(visitedLocation, attraction, 100);

		        UserRewardDTO result = userRewardMapper.toUserRewardDTO(new UserReward(visitedLocation, attraction, 100));
	    	

	    	assertThat(result).isEqualToComparingFieldByField(testUserRewardDTO);

		}



		// ##############################################################


}
