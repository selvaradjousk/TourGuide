package gps.dto;

import java.util.UUID;

import gps.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class AttractionDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttractionDTO {

     /** The attraction id. */
     private UUID attractionId;

    /** The attraction name. */
    private String attractionName;

    /** The city. */
    private String city;

    /** The state. */
    private String state;

    /** The location. */
    private Location location;
}
