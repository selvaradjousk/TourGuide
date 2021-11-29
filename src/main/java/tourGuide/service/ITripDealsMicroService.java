package tourGuide.service;

import java.util.List;
import java.util.UUID;

import tourGuide.dto.ProviderDTO;

public interface ITripDealsMicroService {

    List<ProviderDTO> getProviders(final String apiKey, final UUID userId, final int adults, final int children,
            final int nightsStay, final int rewardPoints);
}
