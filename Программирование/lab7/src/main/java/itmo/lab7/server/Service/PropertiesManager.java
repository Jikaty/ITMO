package itmo.lab7.server.Service;

import java.util.Properties;


public class PropertiesManager {
	private static final Properties PROPERTIES = new Properties();

	static {
		loadProperties();
	}
	private static void loadProperties() {
		try(var inputStream = PropertiesManager.class.getClassLoader().getResourceAsStream("db.properties")){
			PROPERTIES.load(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


	}

	public static String get(String key) {
		return null;
	}





}
