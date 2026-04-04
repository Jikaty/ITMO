package model;

import lombok.*;

import java.io.Serializable;

/**
 * The type Coordinates.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {})
public class Coordinates implements Serializable {
	private float x;
	private double y;
}
