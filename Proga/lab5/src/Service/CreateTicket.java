package Service;

import Model.Coordinates;
import Model.Person;
import Model.Ticket;
import Model.TicketType;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;


/**
 * The type Create ticket.
 */
public class CreateTicket {
	private static Scanner scanner = new Scanner(System.in);
	private static boolean isScriptMode = false;

	/**
	 * Gets ticket.
	 *
	 * @return the ticket
	 */
	public Ticket getTicket() {
		return new Ticket(generateTicketId(),registerTicketName(), new Coordinates(registerCoordinateX(),registerCoordinateY()),generationTicketTime(),registerTicketPrice(),registerTicketType(),registerPerson());
	}

	/**
	 * Sets scanner.
	 *
	 * @param newScanner the new scanner
	 * @param scriptMode the script mode
	 */
	public static void setScanner(Scanner newScanner, boolean scriptMode) {
		scanner = newScanner;
		isScriptMode = scriptMode;
	}




	private int generateTicketId(){
		return UUID.randomUUID().hashCode();
	}

	private String registerTicketName(){
		int countWrongInputs = 0;
		while(true) {
			if (!isScriptMode)System.out.print("Ticket name: ");
			try {
				String name = scanner.nextLine();
				if(name!=null && !name.isEmpty()) {
					return name;
				} else {
					countWrongInputs = wrongInput(countWrongInputs);
					System.out.println("You entered bullshit");
				}
			} catch (Exception e) {
				countWrongInputs = wrongInput(countWrongInputs);
				System.out.println("You entered bullshit");
			}
		}
	}

	private float registerCoordinateX(){
		int countWrongInputs = 0;
		if (!isScriptMode)System.out.println("Create coordinates");
		while(true) {
			if (!isScriptMode)System.out.println("Enter X coordinate: ");
			try {
				float x = Float.parseFloat(scanner.nextLine());
				if (x <= 882) {
					return x;
				} else {
					countWrongInputs = wrongInput(countWrongInputs);
					System.out.println("You entered bullshit");
				}
			} catch (Exception e) {
				countWrongInputs = wrongInput(countWrongInputs);
				 System.out.println("You entered bullshit");
			}
		}
	}
	private double registerCoordinateY(){
		int countWrongInputs = 0;
		while(true) {
			if (!isScriptMode)System.out.println("Enter Y coordinate: ");
			try {
				float y = Float.parseFloat(scanner.nextLine());
				if (y <= 676) {
					return y;
				} else {
					countWrongInputs = wrongInput(countWrongInputs);
					System.out.println("You entered bullshit");
				}
			} catch (Exception e) {
				countWrongInputs = wrongInput(countWrongInputs);
				System.out.println("You entered bullshit");
			}
		}
	}

	private Date registerPersonBirthday(){
		int countWrongInputs =0;
		if (!isScriptMode)System.out.println("Create person");
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		formatter.setLenient(false);
		while(true) {
			try {
				if (!isScriptMode)System.out.println("Enter date(dd.MM.yyyy): ");
				String stBirthday = scanner.nextLine();
				if (stBirthday.isEmpty()) {return null;}else {
					return formatter.parse(stBirthday);
				}
			} catch (Exception e){
				countWrongInputs = wrongInput(countWrongInputs);
				System.out.println("You entered bullshit");
			}
		}
	}

	private double registerPersonHeight(){
		int countWrongInputs = 0;
		while(true) {
			if (!isScriptMode)System.out.println("Enter height: ");
			try{
				double height = Double.parseDouble(scanner.nextLine());
				if(height > 0){
					return height;
				} else {
					countWrongInputs = wrongInput(countWrongInputs);
					System.out.println("You entered bullshit");
				}
			} catch (Exception e){
				countWrongInputs = wrongInput(countWrongInputs);
				System.out.println("You entered bullshit");
			}

		}
	}

	private int registerPersonWeight(){
		int countWrongInputs = 0;
		while(true) {
			if (!isScriptMode)System.out.println("Enter weight: ");
			try{
				int weight = Integer.parseInt(scanner.nextLine());
				if(weight > 0){
					return weight;
				} else {
					countWrongInputs = wrongInput(countWrongInputs);
					System.out.println("You entered bullshit");
				}
			} catch (Exception e){
				countWrongInputs = wrongInput(countWrongInputs);
				System.out.println("You entered bullshit");
			}
		}
	}

	private String registerPersonPassportID(){
		int countWrongInputs = 0;
		while(true) {
			if (!isScriptMode)System.out.println("Enter passport ID: ");
			try {
				String passportID = scanner.nextLine();
				if(passportID.length() >= 4 && passportID.length() <= 42) {
					return passportID;
				}
			} catch (Exception e){
				countWrongInputs = wrongInput(countWrongInputs);
				System.out.println("You entered bullshit");
			}
		}
	}




	private ZonedDateTime generationTicketTime(){
		return  ZonedDateTime.now();
	}

	private long registerTicketPrice(){
		int countWrongInputs =0;
		while(true) {
			if (!isScriptMode)System.out.print("Enter ticket price: ");
			try {
				long price = Long.parseLong(scanner.nextLine());
				if(price>0) {
					return price;
				} else {
					countWrongInputs = wrongInput(countWrongInputs);
					System.out.println("You entered bullshit");
				}
			} catch (Exception e) {
				countWrongInputs = wrongInput(countWrongInputs);
				System.out.println("You entered bullshit");
			}
		}
	}

	private TicketType registerTicketType(){
		int countWrongInputs =0;
		while(true) {
			if (!isScriptMode)System.out.println("Enter ticket type: ");
			if (!isScriptMode)System.out.println("Available ticket types:\nVIP\nUSUAL\nBUDGETARY\nCHEAP");
			String ticketType = scanner.nextLine();
			if(ticketType.isEmpty()) return null;
			else{
				try {
					return TicketType.valueOf(ticketType);
				} catch (IllegalArgumentException e) {
					countWrongInputs = wrongInput(countWrongInputs);
					System.out.println("You entered bullshit");
				}
			}
		}
	}

	private Person registerPerson(){
		if (!isScriptMode)System.out.println("Do u want create person?\nP.s. if u answer \"No\" null will return, else u will create person");
		String answer = scanner.nextLine().toLowerCase();
		if(Objects.equals(answer, "no")) return null;
		else{
			return new Person(registerPersonBirthday(),registerPersonHeight(),registerPersonWeight(),registerPersonPassportID());
		}
	}

	private int wrongInput(int s){
		if(isScriptMode)s++;
		if(s>4) {
			System.exit(12);
			System.out.println("A lot of wrong types, your script is bullshit");
		}
		return s;
	}

}
