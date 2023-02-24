package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.dto.LocationDTO;
import tourGuide.model.Location;

@Component
public class LocationMapper {


	// ##############################################################
    public LocationDTO toLocationDTO(final Location location) {

        return new LocationDTO(
        		location.getLatitude(),
        		location.getLongitude());
    }
    

	// ##############################################################

    public Location toLocation(final LocationDTO locationDTO) {

        return new Location(
        		locationDTO.getLatitude(),
        		locationDTO.getLongitude());
    } 

	// ##############################################################

}
