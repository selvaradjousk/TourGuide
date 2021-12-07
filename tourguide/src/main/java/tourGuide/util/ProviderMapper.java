package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.dto.ProviderDTO;
import tourGuide.model.Provider;

@Component
public class ProviderMapper {




	// ##############################################################


  public Provider toProvider(final ProviderDTO providerDTO) {

      return new Provider(
    		  providerDTO.getName(),
    		  providerDTO.getPrice(),
    		  providerDTO.getTripId());
  } 


	// ##############################################################


}
