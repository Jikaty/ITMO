package model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Person.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {})
public class Person implements Serializable {
	private Date birthday;
	private double height;
	private int weight;
	private String passportID;
}
