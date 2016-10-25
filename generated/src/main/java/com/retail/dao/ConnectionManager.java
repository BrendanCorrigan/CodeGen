package com.retail.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.MysqlDataSource;

/**
 *                                                W A R N I N G
 * 
 * THIS CLASS IMPLEMENTS A SIMPLE CONNECTION MANAGER AND
 * SHOULD NOT BE USED IN A PRODUCTION ENVIRONMENT.
 * 
 * CONNECTIONS TO DATABASES SHOULD BE RETRIEVED FROM THE 
 * UNDERLYING DATABASE CONNECTION MANAGER SUPPLIED WITH THE 
 * APPLICAION SERVER
 * 
 * THIS CLASS HAS BEEN INCLUDED AS A CONVENIENCE FOR TESTING ONLY
 * 
 * @author auto-generated
 *
 */
public class ConnectionManager {
    
    // Logger
	private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
	
    // Default location for the database configuration file
	private static final String DEFAULT_CONFIG = "sql/databases.json";
	
	// HSQLDB - used for testing
	public static final String HSQLDB = "HSQLDB";
	
	// MySQL database
	public static final String MYSQL = "MySQL";
	
	// Used for JNDI binding - needs to be configured through an application server. EXPERIMENTAL
	public static final String DATABASE_BINDING = "jdbc/project";

	// Creates a list of databases which have been configured
	private static Map<String, DatabaseConfiguration>databases = new HashMap<String, DatabaseConfiguration>();
	
	// The default database - as this is a convenience class for testing the default is HSQLDB.
	private static String databaseToUse = HSQLDB;
	
	/**
	 * This is a simple implementation of a connection manager. In a server based application
	 * the database connection should me managed the server. This convenience class is included
	 * for testing purposes only
	 */
	private ConnectionManager()  {
		try {
			loadConfiguration(DEFAULT_CONFIG);
			
		} catch (IOException e) {
			logger.error("Error creating an instance of the ConnectionManager", e);
		}
	}
	
	
	/**
	 * Follow the singleton pattern
	 *
	 */
	private static class SingletonHelper {
		private static final ConnectionManager INSTANCE = new ConnectionManager();
	}
	

	/**
	 * Usage: ConnectionManager.getInstance().getConnection()
	 * 
	 * @return
	 */
	public static ConnectionManager getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	private void loadConfiguration(String configFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = readFile(configFile);

		logger.debug("Configuration - JSON: " + json);
		
		// Get the list of objects from database.json file
		List<DatabaseConfiguration> configs = mapper.readValue(json, new TypeReference<List<DatabaseConfiguration>>(){});
		
		System.out.println(configs);
	
		if (configs != null && !configs.isEmpty()) {
			for (DatabaseConfiguration config : configs) {			
				databases.put(config.name, config);				
			}
		}

		logger.debug("List of database drivers configured: " + databases);
	}
	
	/**
	 * Get a connection to the default configured database
	 * 
	 * @return database connection to the default database
	 */
	public Connection getConnection() {
		return getConnection(databaseToUse);		
	}
	
	/**
	 * Get a connection to the database specified in the parameter
	 * 
	 * @param db the database to get a connection to: ConnectionManager.HSQLDB, ConnectionManager.MYSQL
	 * @return database connection to the specified database
	 */
	public Connection getConnection(String db) {
		logger.trace("getConnection(db)");
		
		DatabaseConfiguration config = null;
		Connection conn = null;
		
		if (db != null && !db.trim().isEmpty()) {
			logger.debug("getConnection(" + db + ")");
			
			config = databases.get(db);
					
			if (config != null) {
				try {
					conn = DriverManager.getConnection(config.url, config.user, config.passwd);
				} catch (SQLException e) {
					logger.error("There has been a problem getting a databse connection for database: " + db, e);
				}
			}
			
		} else {
			logger.error("No database information passed to getConnection(db)" );
			
		}
		return conn;
		
	}

	/**
	 * Convenience method to set the default database configuration
	 * 
	 * @param db the database to set as default: ConnectionManager.HSQLDB, ConnectionManager.MYSQL
	 * @return A DataSource object of the database to use.
	 */
	public DataSource useDatabase(String db) {

		DataSource datasource = null;
		if (db!=null && !db.trim().isEmpty()) {
						
			datasource = getDataSource(db);
			if (datasource != null) {
				databaseToUse = db;
			}
		}
		
		return datasource;
	}
	
	/**
	 * WARNING - EXPERIMENTAL - DO NOT USE
	 * @param db
	 * @throws NamingException
	 */
	public void bindDatabase(String db) throws NamingException {

		DataSource datasource = null;
		if (db!=null && !db.trim().isEmpty()) {
	
			datasource = getDataSource(db);		
			if (datasource != null) {
				Context ctx = new InitialContext();
				ctx.bind(DATABASE_BINDING, datasource);
			}		
		}
	}
	
	/**
	 * WARNING - EXPERIMENTAL - DO NOT USE
	 * @param db
	 * @return
	 */
	private DataSource getDataSource(String db) {
		DataSource datasource = null;
		DatabaseConfiguration config = null; 
		
		switch (db) {
		case MYSQL:
			MysqlDataSource mysql_ds = new MysqlDataSource();
			config = databases.get(MYSQL);
			
			if (config != null) {			
				mysql_ds.setUrl(config.url);
				mysql_ds.setUser(config.passwd);
				mysql_ds.setPassword(config.user);
				
				datasource = mysql_ds;
			}
			break;

		case HSQLDB:
			JDBCDataSource hsqldb_ds = new JDBCDataSource();
			config = databases.get(HSQLDB);
			
			if (config != null) {			
				hsqldb_ds.setUrl(config.url);
				hsqldb_ds.setUser(config.passwd);
				hsqldb_ds.setPassword(config.user);
				
				datasource = hsqldb_ds;
			}	
			break;
						
		default:
			// Do Nothing - driver has not been set up correctly - could try to create a generic datasource here?		
			break;
		}
		
	
		return datasource;
	}
	
	/**
	 * 
	 * @param sqlFileName
	 */
	public void runScript(String sqlFileName) {
		runScript(databaseToUse, sqlFileName);
	}
	
	/**
	 * 
	 * @param db
	 * @param sqlFileName
	 */
	public void runScript(String db, String sqlFileName) {
				
		try {
			String sqlToExecute = readFile(sqlFileName);
			String[] sqlCommands = sqlToExecute.split(";"); 
			
			for (String sqlCommand : sqlCommands) {
			
				Connection connection = getConnection(db);
				Statement statement = connection.createStatement();
				statement.executeUpdate(sqlCommand);
				
				connection.commit();
				connection.close();
				
			}
						
		} catch (IOException e) {
			logger.error(e );
			
		} catch (SQLException e) {
			logger.error(e );
		}

	}	
	

    public void shutdown() throws SQLException {

    	Connection connection = getConnection(); 
    	if (databaseToUse.equals(HSQLDB)) {
    		
	        Statement st = connection.createStatement();
	        st.execute("SHUTDOWN");
	        
	        System.out.println("Shutdown");
	        
	        
    	}
        connection.close();    // if there are no other open connection
    }
	
    public void shutdown(Connection connection) throws SQLException {

    	if (databaseToUse.equals(HSQLDB)) {    	
	        Statement st = connection.createStatement();
	        st.execute("SHUTDOWN");
	        
	        System.out.println("Shutdown");
    	}
        connection.close();    // if there are no other open connection
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

}
