package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.dto.ProviderDTO;
import tourGuide.model.Provider;

@Component
public class ProviderMapper {



	// ##############################################################


    public ProviderDTO toProviderDTO(final tripPricer.Provider provider) {

      return new ProviderDTO(
    		  provider.name,
    		  provider.price,
    		  provider.tripId);
  }
  


	// ##############################################################


  public Provider toProvider(final ProviderDTO providerDTO) {

      return new Provider(
    		  providerDTO.getName(),
    		  providerDTO.getPrice(),
    		  providerDTO.getTripId());
  } 


	// ##############################################################


}
