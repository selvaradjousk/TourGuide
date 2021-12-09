package tourGuide.proxy;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tourGuide.dto.AttractionDTO;
import tourGuide.dto.VisitedLocationDTO;


/**
 * The Interface MicroserviceGpsProxy.
 */
@Service
@FeignClient(value = "gps-microservice", url = "localhost:9091/gps")
public interface MicroserviceGpsProxy {




	// ##############################################################



    /**
	 * Gets the user location.
	 *
	 * @param userId the user id
	 * @return the user location
	 */
	@GetMapping("/userLocation/{userId}")
    VisitedLocationDTO getUserLocation(
    		@PathVariable("userId") final UUID userId);



	// ##############################################################



	/**
	 * Gets the attractions.
	 *
	 * @return the attractions
	 */
	@GetMapping("/attractions")
    List<AttractionDTO> getAttractions();




	// ##############################################################




}
