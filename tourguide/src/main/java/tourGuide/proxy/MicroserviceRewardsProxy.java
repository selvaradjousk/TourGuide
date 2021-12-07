package tourGuide.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


/**
 * The Interface MicroserviceRewardsProxy.
 */
@FeignClient(value = "rewards-microservice", url = "localhost:9092/rewards")
public interface MicroserviceRewardsProxy {



	// ##############################################################


    /**
	 * Gets the reward points.
	 *
	 * @param attractionId the attraction id
	 * @param userId the user id
	 * @return the reward points
	 */
	@GetMapping("/points/{attractionId}/{userId}")
    int getRewardPoints(
    		@PathVariable("attractionId") final UUID attractionId,
    		@PathVariable("userId")  final UUID userId);


	// ##############################################################



}
