package Service;

import Model.Coordinates;
import Model.Person;
import Model.Ticket;
import Model.TicketType;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class CreateTicket {
	private final Scanner scanner = new Scanner(System.in);

	public Ticket getTicket() {
		return new Ticket(generateTicketId(),registerTicketName(), new Coordinates(registerCoordinateX(),registerCoordinateY()),generationTicketTime(),registerTicketPrice(),registerTicketType(),registerPerson());
	}

	private int generateTicketId(){
		Random rand = new Random();
		return rand.nextInt(10000)+1;
	}

	private String registerTicketName(){
		while(true) {
			System.out.print("Имя билета задай ");
			try {
				String name = scanner.nextLine();
				if(name!=null && !name.isEmpty()) {
					return name;
				} else {
					System.out.println("Введена хуета");
				}
			} catch (Exception e) {
				System.out.println("Введена хуета");
			}
		}
	}

	private float registerCoordinateX(){
		System.out.println("Create coordinates");
		while(true) {
			System.out.println("Введи координату X");
			try {
				float x = Float.parseFloat(scanner.nextLine());
				if (x <= 882) {
					return x;
				} else {
					System.out.println("Ты ввел хуйню а не данные");
				}
			} catch (Exception e) {
				System.out.println("Ты ввел хуйню а не данные");
			}
		}
	}
	private double registerCoordinateY(){
		while(true) {
			System.out.println("Введи координату Y");
			try {
				float y = Float.parseFloat(scanner.nextLine());
				if (y <= 676) {
					return y;
				} else {
					System.out.println("Ты ввел хуйню а не данные");
				}
			} catch (Exception e) {
				System.out.println("Ты ввел хуйню а не данные");
			}
		}
	}

	private Date registerPersonBirthday(){
		System.out.println("Create person");
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		formatter.setLenient(false);
		while(true) {
			try {
				System.out.println("Введи дату рождения (дд.мм.гггг):");
				String stBirthday = scanner.nextLine();
				if (stBirthday.isEmpty()) {return null;}else {
					return formatter.parse(stBirthday);
				}
			} catch (Exception e){
				System.out.println("Хуйню ввел");
			}
		}
	}

	private double registerPersonHeight(){
		while(true) {
			System.out.println("Введи рост ");
			try{
				double height = Double.parseDouble(scanner.nextLine());
				if(height > 0){
					return height;
				} else {
					System.out.println("Хуйню ввел");
				}
			} catch (Exception e){
				System.out.println("Хуйню ввел");
			}

		}
	}

	private int registerPersonWeight(){
		while(true) {
			System.out.println("Введи вес");
			try{
				int weight = Integer.parseInt(scanner.nextLine());
				if(weight > 0){
					return weight;
				} else {
					System.out.println("Хуйню ввел");
				}
			} catch (Exception e){
				System.out.println("Хуйню ввел");
			}
		}
	}

	private String registerPersonPassportID(){
		while(true) {
			System.out.println("Введи PassportID ");
			try {
				String passportID = scanner.nextLine();
				if(passportID.length() >= 4 && passportID.length() <= 42) {
					return passportID;
				}
			} catch (Exception e){
				System.out.println("Хуйню ввел");
			}
		}
	}




	private ZonedDateTime generationTicketTime(){
		return  ZonedDateTime.now();
	}

	private long registerTicketPrice(){
		while(true) {
			System.out.print("Введи цену петух ");
			try {
				long price = Long.parseLong(scanner.nextLine());
				if(price>0) {
					return price;
				} else {
					System.out.println("Ты ввел хуйню");
				}
			} catch (Exception e) {
				System.out.println("Ты ввел хуйню");
			}
		}
	}

	private TicketType registerTicketType(){
		while(true) {
			System.out.println("Введи тип билета");
			System.out.println("Доступные типы билетов:\nVIP\nUSUAL\nBUDGETARY\nCHEAP");
			String ticketType = scanner.nextLine();
			if(ticketType.isEmpty()) return null;
			else{
				try {
					return TicketType.valueOf(ticketType);
				} catch (IllegalArgumentException e) {
					System.out.println("Это нихуя не один из доступых типов");
				}
			}
		}
	}

	private Person registerPerson(){
		System.out.println("Хочешь создать персонажа?\nP.s. ответишь \"No\" вернется null, иначе будет создание персонажа");
		String answer = scanner.nextLine().toLowerCase();
		if(Objects.equals(answer, "no")) return null;
		else{
			return new Person(registerPersonBirthday(),registerPersonHeight(),registerPersonWeight(),registerPersonPassportID());
		}
	}
}
