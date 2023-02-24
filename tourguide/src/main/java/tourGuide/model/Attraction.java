package tourGuide.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class Attraction.
 */
@Getter
@Setter
@AllArgsConstructor
public class Attraction {

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
