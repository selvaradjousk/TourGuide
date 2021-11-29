package tourGuide.service;

import java.util.List;
import java.util.UUID;

import tourGuide.dto.ProviderDTO;

/**
 * The Interface ITripDealsMicroService.
 */
public interface ITripDealsMicroService {

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
    List<ProviderDTO> getProviders(final String apiKey, final UUID userId, final int adults, final int children,
            final int nightsStay, final int rewardPoints);
}
