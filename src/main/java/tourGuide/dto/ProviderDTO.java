package tourGuide.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ProviderDTO.
 */
@Getter
@Setter
public class ProviderDTO {

    /** The name. */
    private String name;

    /** The price. */
    private double price;

    /** The trip id. */
    private UUID tripId;

    
    public ProviderDTO(UUID tripId, String name, double price) {
      this.name = name;
      this.tripId = tripId;
      this.price = price;
    }
}