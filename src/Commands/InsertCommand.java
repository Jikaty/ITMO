package Commands;

import Model.Coordinates;
import Model.Person;
import Model.Ticket;
import Model.TicketType;
import Service.CommandManager;
import Service.TicketManager;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Scanner;

public class InsertCommand implements  Command{
	private final Scanner scanner = new Scanner(System.in);
	TicketManager tm = TicketManager.getInstance();

	@Override
	public void executeCommand(){
		try {
			System.out.println("\n=== Создание нового билета ===");

			System.out.print("Ключ для HashTable");
			int key = Integer.parseInt(scanner.nextLine().trim());
			// 1. Собираем данные у пользователя
			System.out.print("Введите ID билета: ");
			int id = Integer.parseInt(scanner.nextLine().trim());

			System.out.print("Введите название билета: ");
			String name = scanner.nextLine().trim();

			System.out.print("Введите координату X: ");
			int x = Integer.parseInt(scanner.nextLine().trim());

			System.out.print("Введите координату Y: ");
			int y = Integer.parseInt(scanner.nextLine().trim());

			System.out.print("Введите цену билета: ");
			long price = Long.parseLong(scanner.nextLine().trim());

			System.out.println("Выберите тип билета (VIP, USUAL, CHEAP): ");
			TicketType type = TicketType.valueOf(scanner.nextLine().trim().toUpperCase());

			// Данные персоны
			System.out.print("Введите вес персоны: ");
			int weight = Integer.parseInt(scanner.nextLine());

			System.out.print("Введите паспортный ID персоны: ");
			String passportID = scanner.nextLine().trim();

			System.out.print("Введите рост персоны: ");
			double height = Double.parseDouble(scanner.nextLine().trim());

			Coordinates coordinates = new Coordinates(x, y);
			Person person = new Person(new Date(), height , weight, passportID);

			Ticket ticket = new Ticket(id, name, coordinates, ZonedDateTime.now(), price, type, person);
			tm.ticketRegister(key,ticket);
			System.out.println("Complete");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}


	@Override
	public String describeCommand(){
		return " - добавляет новый элемент с заданным ключом";
	}
}
