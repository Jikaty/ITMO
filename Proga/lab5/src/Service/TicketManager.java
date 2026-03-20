package Service;

import Commands.LoadFromFile;
import Commands.SaveToFile;
import Model.Person;
import Model.Ticket;
import Model.TicketType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import lombok.Setter;
import lombok.ToString;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Math.abs;


/**
 * The type Ticket manager.
 */
@ToString(exclude = "initTime" )
public class TicketManager implements SaveToFile, LoadFromFile {
	private Map<Integer, Ticket> ticketsCollection = new Hashtable<>() ;
	private final LocalDateTime initTime = LocalDateTime.now();
	private static final XStream xstream = new XStream(new DomDriver());
    @Setter
    private boolean saveFlag = true;

	/**
	 * Instantiates a new Ticket manager.
	 */
	public TicketManager(){
		xstream.addPermission(AnyTypePermission.ANY);
	}

	private static class TicketManagerStatic {
		private static final TicketManager INSTANCE = new TicketManager();
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static TicketManager getInstance() {
		return TicketManagerStatic.INSTANCE;
	}

	/**
	 * Get init time string.
	 *
	 * @return the string
	 */
	public String getInitTime(){
		return initTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * Get collection time string.
	 *
	 * @return the string
	 */
	public String getCollectionTime(){
		return ticketsCollection.getClass().getName();
	}

	/**
	 * Get collection size int.
	 *
	 * @return the int
	 */
	public int getCollectionSize(){
		return ticketsCollection.size();
	}

	/**
	 * Get collection hash code int.
	 *
	 * @return the int
	 */
	public int getCollectionHashCode(){
		return ticketsCollection.hashCode();
	}

	/**
	 * Ticket register.
	 *
	 * @param key    the key
	 * @param ticket the ticket
	 */
	public void ticketRegister (int key,Ticket ticket){
		ticketsCollection.put(key,ticket);
	}

	/**
	 * Get ticket collection map.
	 *
	 * @return the map
	 */
	public Map<Integer, Ticket> getTicketCollection(){
		return ticketsCollection;
	}

	/**
	 * Get ticket type ticket type.
	 *
	 * @param key the key
	 * @return the ticket type
	 */
	public TicketType getTicketType(Integer key){
		return ticketsCollection.get(key).getType();
	}

	/**
	 * Get ticket power long.
	 *
	 * @param ticket the ticket
	 * @return the long
	 */
	public long getTicketPower(Ticket  ticket){
		long ticketPower = 0;
		ticketPower -= ticket.getPrice();
		ticketPower += ticket.getType().getTicketTypeCode() * 300L;
		ticketPower -= abs((long) ticket.getCoordinates().getX());
		ticketPower -= abs((long) ticket.getCoordinates().getY());
		ticketPower += getPersonPower(ticket);
		return ticketPower;
	}

	/**
	 * Get person power int.
	 *
	 * @param ticket the ticket
	 * @return the int
	 */
	public int getPersonPower(Ticket ticket){
		if (ticket.getPerson() == null){
			return 0;
		}
		int personPower = 0;
		personPower += ticket.getPerson().getWeight();
		personPower += (int)ticket.getPerson().getHeight();
		return personPower;
	}


	@Override
	public void saveToFile(String fileName) {
		if (saveFlag) {
			try (FileOutputStream recordToFile = new FileOutputStream(fileName)) {
				recordToFile.write(xstream.toXML(ticketsCollection).getBytes("UTF-8"));
				System.out.println("Save to " + fileName);
			} catch (Exception e) {
				System.out.println("Save error");
			}
		}
	}

	@Override
	public void loadFromFile(String  fileName) {
		try (FileInputStream fis = new FileInputStream(fileName)) {
			ticketsCollection = (Map<Integer, Ticket>) xstream.fromXML(fis);
		} catch (Exception e) {
			System.out.println("Ошибка загрузки");
			ticketsCollection = new Hashtable<>();
		}
	}


}

