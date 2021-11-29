package tourGuide.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class Provider.
 */
@Getter
@Setter
public class Provider {

    /** The name. */
    private String name;

    /** The price. */
    private double price;

    /** The trip id. */
    private UUID tripId;

    
    public Provider(UUID tripId, String name, double price) {
      this.name = name;
      this.tripId = tripId;
      this.price = price;
    }
}
