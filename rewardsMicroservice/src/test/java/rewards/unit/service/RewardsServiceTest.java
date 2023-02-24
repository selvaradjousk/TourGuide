package rewards.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import rewardCentral.RewardCentral;
import rewards.service.RewardsService;

@DisplayName("Unit Test - Service - Rewards Microservice")
@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {


    @InjectMocks
    private RewardsService rewardsService;

    @Mock
    private RewardCentral rewardCentral;




	//###############################################################


    

    @Test
    @DisplayName("Check (getAttractionRewardPoints) "
    		+ " - Given an user & attraction id,"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - reward points as expected")
    public void testGetAttractionRewardPoints() {

    	UUID userID = UUID.randomUUID();
        UUID attractionID = UUID.randomUUID();
        int rewardsPoints = 500;

        when(rewardCentral
        		.getAttractionRewardPoints(
        				userID,
        				attractionID))
        .thenReturn(rewardsPoints);

        int result = rewardsService
        		.getAttractionRewardPoints(
        				userID,
        				attractionID);

        assertEquals(rewardsPoints, result);
        verify(rewardCentral).getAttractionRewardPoints(userID, attractionID);
    }



	//###############################################################


    
}
