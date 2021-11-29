package tourGuide.model;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class VisitedLocation.
 */
@Getter
@Setter
public class VisitedLocation {

    /** The user id. */
    private UUID userId;

    /** The location. */
    private Location location;

    /** The time visited. */
    private Date timeVisited;

    /**
     * Instantiates a new visited location.
     *
     * @param userId the user id
     * @param location the location
     * @param timeVisited the time visited
     */
    public VisitedLocation(UUID userId, Location location, Date timeVisited) {
        this.userId = userId;
        this.location = location;
        this.timeVisited = timeVisited;
      }
}
