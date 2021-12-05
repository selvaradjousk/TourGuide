package gps.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Location.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    /** The latitude. */
    private double latitude;

    /** The longitude. */
    private double longitude;
}
