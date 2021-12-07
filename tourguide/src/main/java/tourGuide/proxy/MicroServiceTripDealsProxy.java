package tourGuide.proxy;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tourGuide.dto.ProviderDTO;

@FeignClient(value = "tripdeals-microservice", url = "localhost:9093/tripDeals")
public interface MicroServiceTripDealsProxy {

    @GetMapping("/providers/{apiKey}/{userId}/{adults}/{children}/{nightsStay}/{rewardPoints}")
    List<ProviderDTO> getProviders(
    		@PathVariable("apiKey") final String apiKey,
    		@PathVariable("userId") final UUID userId,
            @PathVariable("adults") final int adults,
            @PathVariable("children") final int children,
            @PathVariable("nightsStay") final int nightsStay,
            @PathVariable("rewardPoints") final int rewardPoints);
}

