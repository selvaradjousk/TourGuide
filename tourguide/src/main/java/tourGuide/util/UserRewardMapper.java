package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.dto.UserRewardDTO;
import tourGuide.model.UserReward;

@Component
public class UserRewardMapper {


	// ##############################################################


    public UserRewardDTO toUserRewardDTO(
    		final UserReward userReward) {

        return new UserRewardDTO(
        		userReward.visitedLocation,
        		userReward.attraction,
        		userReward.getRewardPoints());
    }


	// ##############################################################


}
