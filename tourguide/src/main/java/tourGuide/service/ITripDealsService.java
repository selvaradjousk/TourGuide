package tourGuide.service;

import java.util.List;

import tourGuide.dto.ProviderDTO;

public interface ITripDealsService {

	/**
	 * Gets the trip deals.
	 *
	 * @param userName the user name
	 * @return the trip deals
	 */
	List<ProviderDTO> getUserTripDeals(String userName);

}