package tourGuide.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * The Interface MicroserviceRewardsProxy.
 */
//@FeignClient(value = "rewards-microservice", url = "localhost:9092/rewards") // this URL FeignClient is for host run (eclipse or gradle commandline)
@FeignClient(value = "rewards-microservice", url = "${CLIENT_REWARDS_BASE_URL:http://localhost:9092/rewards}") // this URL FeignClient is for docker
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
