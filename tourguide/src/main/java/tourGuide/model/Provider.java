package tourGuide.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class Provider.
 */
@Getter
@Setter
@AllArgsConstructor
public class Provider {

    /** The name. */
    private String name;

    /** The price. */
    private double price;

    /** The trip id. */
    private UUID tripId;


}
