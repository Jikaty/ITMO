package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@ToString(exclude = {})
public class Person {
	private Date birthday;
	private double height;
	private int weight;
	private String passportID;
}
