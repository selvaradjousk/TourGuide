package tourGuide.model;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class VisitedLocation.
 */
@Getter
@Setter
@AllArgsConstructor
public class VisitedLocation {

    /** The user id. */
    private UUID userId;

    /** The location. */
    private Location location;

    /** The time visited. */
    private Date timeVisited;

}
