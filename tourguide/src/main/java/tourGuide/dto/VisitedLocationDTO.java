package tourGuide.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.model.Location;

/**
 * The Class VisitedLocationDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitedLocationDTO {

    /** The user id. */
    private UUID userId;

    /** The location. */
    private Location location;

    /** The time visited. */
    private Date timeVisited;


}
