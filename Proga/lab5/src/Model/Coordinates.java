package Model;

import lombok.*;

/**
 * The type Coordinates.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {})
public class Coordinates {
	private float x;
	private double y;
}
