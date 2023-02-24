package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class LocationDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    /** The latitude. */
    private double latitude;

    /** The longitude. */
    private double longitude;
}
