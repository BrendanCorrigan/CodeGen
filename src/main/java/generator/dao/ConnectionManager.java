package generator.dao;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




public class ConnectionManager {
    
    // Logger
 	private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
	
    // Default location for the database configuration file
	private static final String DEFAULT_CONFIG = "sql/databases.json";
	
	public static final String HSQLDB = "HSQLDB";
	public static final String MYSQL = "MySQL";
	
	public static final String DATABASE_BINDING = "jdbc/project";
		
	private static Map<String, DatabaseConfiguration>databases = new HashMap<String, DatabaseConfiguration>();
	
	private static String databaseToUse = HSQLDB;


	/**
	 * 
	 */
	private ConnectionManager()  {
		try {
			loadConfiguration(DEFAULT_CONFIG);
			
		} catch (IOException e) {
			logger.error("IOExcepion found ", e);
		}
	}
	

	/**
	 * 
	 * @author Brendan
	 *
	 */
	private static class SingletonHelper {
		private static final ConnectionManager INSTANCE = new ConnectionManager();
	}
	
	/**
	 * 
	 * @return
	 */
	public static ConnectionManager getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	private void loadConfiguration(String configFile) throws IOException {
		logger.trace("Loading configuration from file: " + configFile);
		
		Gson gson = new Gson();		
		String json = readFile(configFile);
		
		// Deserialization
		Type collectionType = new TypeToken<List<DatabaseConfiguration>>(){}.getType();
		List<DatabaseConfiguration> configs = gson.fromJson(json, collectionType);		
				
		logger.debug(configs);
	
		if (configs != null && !configs.isEmpty()) {
			for (DatabaseConfiguration config : configs) {			
				databases.put(config.name, config);				
			}
		}
		
		logger.debug(databases);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return getConnection(databaseToUse);		
	}
	
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public Connection getConnection(String db) {
		
		DatabaseConfiguration config = databases.get(db);
		Connection conn = null;
				
		if (config != null) {
			try {
				conn = DriverManager.getConnection(config.url, config.user, config.passwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static String readFile(String path) throws IOException {
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded);
	}

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ConnectionManager.getInstance().getConnection(MYSQL);
	}
	
	
}
