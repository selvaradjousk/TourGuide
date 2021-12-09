package tourGuide.proxy;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tourGuide.dto.ProviderDTO;


/**
 * The Interface MicroServiceTripDealsProxy.
 */
@Service
@FeignClient(value = "tripdeals-microservice", url = "localhost:9093/tripDeals")
public interface MicroServiceTripDealsProxy {



	// ##############################################################

    /**
     * Gets the providers.
     *
     * @param apiKey the api key
     * @param userId the user id
     * @param adults the adults
     * @param children the children
     * @param nightsStay the nights stay
     * @param rewardPoints the reward points
     * @return the providers
     */
    @GetMapping("/providers/{apiKey}/{userId}/{adults}/{children}/{nightsStay}/{rewardPoints}")
    List<ProviderDTO> getProviders(
    		@PathVariable("apiKey") final String apiKey,
    		@PathVariable("userId") final UUID userId,
            @PathVariable("adults") final int adults,
            @PathVariable("children") final int children,
            @PathVariable("nightsStay") final int nightsStay,
            @PathVariable("rewardPoints") final int rewardPoints);



	// ##############################################################

}

