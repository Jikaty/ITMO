package Model;

import lombok.*;

import java.util.Date;

/**
 * The type Person.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {})
public class Person {
	private Date birthday;
	private double height;
	private int weight;
	private String passportID;
}
