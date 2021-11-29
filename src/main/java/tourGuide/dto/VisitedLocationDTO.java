package tourGuide.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import tourGuide.model.Location;

/**
 * The Class VisitedLocationDTO.
 */
@Getter
@Setter
public class VisitedLocationDTO {

    /** The user id. */
    private UUID userId;

    /** The location. */
    private Location location;

    /** The time visited. */
    private Date timeVisited;

    /**
     * Instantiates a new visited location DTO.
     *
     * @param userId the user id
     * @param location the location
     * @param timeVisited the time visited
     */
    public VisitedLocationDTO(UUID userId, Location location, Date timeVisited) {
        this.userId = userId;
        this.location = location;
        this.timeVisited = timeVisited;
      }
}