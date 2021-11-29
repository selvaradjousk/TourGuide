package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;

/**
 * The Class UserRewardDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserRewardDTO {

    /** The visited location. */
    private VisitedLocation visitedLocation;

    /** The attraction. */
    private Attraction attraction;

    /** The reward points. */
    private int rewardPoints;
}