package tourGuide.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ProviderDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDTO {

    /** The name. */
    private String name;

    /** The price. */
    private double price;

    /** The trip id. */
    private UUID tripId;


}
