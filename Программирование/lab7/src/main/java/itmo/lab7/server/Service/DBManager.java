package itmo.lab7.server.Service;


import itmo.lab7.model.Coordinates;
import itmo.lab7.model.Person;
import itmo.lab7.model.Ticket;
import itmo.lab7.model.TicketType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DBManager {
	private static DBManager instance;
	TicketManager ticketManager = TicketManager.getInstance();
	private static String url;
	private static String username;
	private static String password ;

	static {
		Properties info = new Properties();
		try (var is = DBManager.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (is == null) {
				throw new RuntimeException("Файл db.properties не найден в resources!");
			}
			info.load(is);
			url = info.getProperty("db.url");
			username = info.getProperty("db.username");
			password = info.getProperty("db.password");
		} catch (IOException e) {
			throw new RuntimeException("Не удалось загрузить файл свойств", e);
		}
	}

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	public void createTables(){
		String sqlCreate = """
				
				CREATE TABLE IF NOT EXISTS users (
				                      id SERIAL PRIMARY KEY,
				                      login varchar(255) NOT NULL UNIQUE,
				                      password_hash TEXT NOT NULL
				);
				
				
				CREATE TABLE IF NOT EXISTS persons (
				                        id SERIAL PRIMARY KEY,
				                        birthday DATE,
				                        height double precision,
				                        weight int,
				                        passportID varchar(255)
				);
				CREATE TABLE IF NOT EXISTS tickets (
				                        id SERIAL PRIMARY KEY,
				                        tck_key int NOT NULL,
				                        name varchar(255) NOT NULL,
				                        x float NOT NULL,
				                        y double precision NOT NULL,
				                        price integer NOT NULL,
				                        type varchar(55),
				    					creation_date TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
				                        personID int REFERENCES persons(id) ON DELETE CASCADE,
				                        userLoginID int REFERENCES users(id) ON DELETE CASCADE
				
				);
			
				
				""";
		try (Connection con = DriverManager.getConnection(url,username,password);
		PreparedStatement stmt = con.prepareStatement(sqlCreate);){
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void saveToBd(Ticket value, int key, int userLoginID) {
		String sqlTicket = "INSERT INTO tickets (tck_key,name,x,y,price,type,personID,userLoginID) VALUES (?,?,?,?,?,?,?,?)";
		String sqlPerson = "INSERT INTO persons (birthday, height, weight, passportID) VALUES (?,?,?,?)";
		try(Connection con = DriverManager.getConnection(url,username,password);){
			con.setAutoCommit(false);
			try {
				int generatedPersonId = -1;
				if (value.getPerson() != null) {
					try (PreparedStatement psPerson = con.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS)) {
						psPerson.setDate(1, new java.sql.Date(value.getPerson().getBirthday().getTime()));
						psPerson.setDouble(2, value.getPerson().getHeight());
						psPerson.setInt(3, value.getPerson().getWeight());
						psPerson.setString(4, value.getPerson().getPassportID());
						psPerson.executeUpdate();
						try (ResultSet rs = psPerson.getGeneratedKeys()) {
							if (rs.next()) {
								generatedPersonId = rs.getInt(1);
							}
						}
					}
				}
				try(PreparedStatement psTicket = con.prepareStatement(sqlTicket, Statement.RETURN_GENERATED_KEYS)) {
					psTicket.setInt(1, key);
					psTicket.setString(2, value.getName());
					psTicket.setDouble(3, value.getCoordinates().getX());
					psTicket.setDouble(4, value.getCoordinates().getY());
					psTicket.setDouble(5, value.getPrice());
					if (value.getTicketType() != null) {
						psTicket.setString(6, value.getTicketType().name());
					} else {
						psTicket.setNull(6, Types.VARCHAR);
					}
					if (generatedPersonId != -1) {
						psTicket.setInt(7, generatedPersonId);
					} else {
						psTicket.setNull(7, Types.INTEGER);
					}
					psTicket.setInt(8, userLoginID);

					psTicket.executeUpdate();
					try (ResultSet rs = psTicket.getGeneratedKeys()) {
						if (rs.next()) {
							int serialID = rs.getInt(1);
							con.commit();
							value.setId(serialID);
							ticketManager.ticketRegister(key, value);
							System.out.println("Объект сохранен с ID: " + key);
						}
					}
				}
			} catch (SQLException e){
				con.rollback();
			}
		} catch (SQLException e){
			e.printStackTrace();
		}

	}

	public boolean registerUser(String login, String passwordHash) {
		String sql = "INSERT INTO users (login, password_hash) VALUES (?, ?)";

		try (Connection con = DriverManager.getConnection(url,username,password);
		     PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, login);
			ps.setString(2, passwordHash);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Ошибка при регистрации " + e.getMessage());
			return false;
		}
	}

	public boolean checkUser(String login, String passwordHash) {
		String sql = "SELECT password_hash FROM users WHERE login = ?";

		try (Connection con = DriverManager.getConnection(url,username,password);
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, login);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String storedHash = rs.getString("password_hash");
					return storedHash.equals(passwordHash);
				}
			}
		} catch (SQLException e) {
			System.err.println("Ошибка при проверке пользователя БД " + e.getMessage());
		}
		return false;
	}

	public int getUserId(String login) {
		String sql = "SELECT id FROM users WHERE login = ?";

		try (Connection con = DriverManager.getConnection(url,username,password);
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, login);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			System.err.println("Ошибка при получении ID пользователя: " + e.getMessage());
		}
		return -1;
	}

	public boolean removeTicket(int key, int userId) {
		String sql = "DELETE FROM tickets WHERE tck_key = ? AND userLoginID = ?";
		try (Connection con = DriverManager.getConnection(url,username,password);
		PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, key);
			ps.setInt(2, userId);

			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void loadTickets(TicketManager ticketManager) {
		String sql = "SELECT t.id, t.tck_key, t.name, t.x, t.y, t.price, t.type, t.creation_date, " +
				"p.birthday, p.height, p.weight, p.passportID " +
				"FROM tickets t " +
				"LEFT JOIN persons p ON t.personID = p.id";

		try (Connection connection = DriverManager.getConnection(url,username,password);
		     PreparedStatement statement = connection.prepareStatement(sql);
		     ResultSet rs = statement.executeQuery()) {


			while (rs.next()) {
				Coordinates coords = new Coordinates(
						rs.getFloat("x"),
						rs.getDouble("y")
				);
				Person person = null;
				String passportId = rs.getString("passportID");
				if (passportId != null) {
					java.sql.Date sqlDate = rs.getDate("birthday");
					java.util.Date utilDate = (sqlDate != null) ? new java.util.Date(sqlDate.getTime()) : null;

					person = new Person(
							utilDate,
							rs.getDouble("height"),
							rs.getInt("weight"),
							rs.getString("passportID")
					);
				}
				java.sql.Timestamp sqlTimestamp = rs.getTimestamp("creation_date");
				ZonedDateTime creationDate = sqlTimestamp.toInstant().atZone(ZoneId.systemDefault());
				int key = rs.getInt("tck_key");
				Ticket ticket = new Ticket(
						rs.getInt("id"),
						rs.getString("name"),
						coords,
						creationDate,
						rs.getLong("price"),
						TicketType.valueOf(rs.getString("type")),
						person
				);
				ticketManager.ticketRegister(key, ticket);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public boolean updateTicket(int key, Ticket value, int userId) {

		String sqlPerson = "UPDATE persons SET birthday = ?, height = ?, weight = ?, passportID = ? " +
				"WHERE id = (SELECT personID FROM tickets WHERE tck_key = ? AND userLoginID = ?)";


		String sqlTicket = "UPDATE tickets SET name = ?, x = ?, y = ?, price = ?, type = ? " +
				"WHERE tck_key = ? AND userLoginID = ?";

		try (Connection con = DriverManager.getConnection(url,username,password)) {
			con.setAutoCommit(false);
			try {

				if (value.getPerson() != null) {
					try (PreparedStatement psPerson = con.prepareStatement(sqlPerson)) {
						psPerson.setDate(1, value.getPerson().getBirthday() != null
								? new java.sql.Date(value.getPerson().getBirthday().getTime())
								: null);
						psPerson.setDouble(2, value.getPerson().getHeight());
						psPerson.setInt(3, value.getPerson().getWeight());
						psPerson.setString(4, value.getPerson().getPassportID());
						psPerson.setInt(5, key);
						psPerson.setInt(6, userId);
						psPerson.executeUpdate();
					}
				}
				try (PreparedStatement psTicket = con.prepareStatement(sqlTicket)) {
					psTicket.setString(1, value.getName());
					psTicket.setDouble(2, value.getCoordinates().getX());
					psTicket.setDouble(3, value.getCoordinates().getY());
					psTicket.setDouble(4, value.getPrice());

					if (value.getTicketType() != null) {
						psTicket.setString(5, value.getTicketType().name());
					} else {
						psTicket.setNull(5, Types.VARCHAR);
					}

					psTicket.setInt(6, key);
					psTicket.setInt(7, userId);

					int affectedRows = psTicket.executeUpdate();

					if (affectedRows > 0) {
						con.commit();
						return true;
					} else {
						con.rollback();
						return false;
					}
				}
			} catch (SQLException e) {
				con.rollback();
				e.printStackTrace();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int clearTickets(int userId) {
		String sql = "DELETE FROM tickets WHERE userLoginID = ?";
		try (Connection con = DriverManager.getConnection(url,username,password);
		     PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, userId);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	public List<Integer> getUserTicketIds(int userID) {
		List<Integer> ids = new ArrayList<>();
		String sql = "SELECT tck_key FROM tickets WHERE userLoginID = ?";
		try (Connection con = DriverManager.getConnection(url,username,password);
		PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ids.add(rs.getInt("tck_key"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}







}
