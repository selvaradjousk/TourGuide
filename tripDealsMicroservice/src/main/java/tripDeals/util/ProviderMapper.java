package tripDeals.util;

import org.springframework.stereotype.Component;

import tripDeals.dto.ProviderDTO;

/**
 * The Class ProviderMapper.
 */
@Component
public class ProviderMapper {



	// ##############################################################


    /**
	 * To provider DTO.
	 *
	 * @param provider the provider
	 * @return the provider DTO
	 */
	public ProviderDTO toProviderDTO(final tripPricer.Provider provider) {

      return new ProviderDTO(
    		  provider.name,
    		  provider.price,
    		  provider.tripId);
  }
  


	// ##############################################################


}
