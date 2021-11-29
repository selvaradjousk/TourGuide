package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.dto.ProviderDTO;
import tourGuide.model.Provider;

@Component
public class ProviderMapper {



	// ##############################################################


    public ProviderDTO toProviderDTO(final tripPricer.Provider provider) {

      return new ProviderDTO(
    		  provider.tripId,
    		  provider.name,
    		  provider.price );
  }
  


	// ##############################################################


  public Provider toProvider(final ProviderDTO providerDTO) {

      return new Provider(
    		  providerDTO.getTripId(),
    		  providerDTO.getName(),
    		  providerDTO.getPrice() );
  } 


	// ##############################################################


}
