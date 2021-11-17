package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPreferencesDTO {

    private Integer attractionProximity;
    private String currency;
    private Integer lowerPricePoint;
    private Integer highPricePoint;
    private Integer tripDuration;
    private Integer ticketQuantity;
    private Integer numberOfAdults;
    private Integer numberOfChildren;

}
