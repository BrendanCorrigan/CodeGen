package com.retail.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	
	public enum Database {
		
		HSQLDB("org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:file:db/", "sa", ""),
		MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/", "testuser", "Gprcph8LE4WPwVVC");
		
		private final String jdbcString;
		private final String driverName;
		private final String user;
		private final String passwd;
		
		Database(String driverName, String jdbcString, String user, String passwd) {
			this.jdbcString = jdbcString;
			this.driverName = driverName;
			this.user = user;
			this.passwd = passwd;			
		};		
		
		public String getDriverName() {
			return driverName;
		};
		
		public String getJdbcString() {
			return jdbcString;
		};
		
		public String getUser() {
			return user;
		};

		public String getPassword() {
			return passwd;
		};
		
	};
	
	
	private Database db; 
	
	public DatabaseManager( Database db) {
		this.db = db;
	}
	
	/**
	 * 
	 * @param db
	 * @param schema
	 * @return
	 */
	public Connection getConnection(String schema)  {

		Connection conn = null;

		try {
						
			Class.forName(db.driverName);			
			conn = DriverManager.getConnection(db.jdbcString+schema, db.user, db.passwd);
						
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return conn;
	}

	
	/**
	 * 
	 * @param connection
	 * @param sqlFileName
	 */
	public void createDatabase (Connection connection, String sqlFileName) {
				
		try {
			String sqlToExecute = readFile(sqlFileName);
			String[] sqlCommands = sqlToExecute.split(";"); 
			
			for (String sqlCommand : sqlCommands) {
			
				Statement statement = connection.createStatement();
				statement.executeUpdate(sqlCommand);
				
			}
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
    public void shutdown(Connection connection) throws SQLException {

    	if (db.equals(Database.HSQLDB)) {    	
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
	static String readFile(String path) throws IOException {
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded);
	}
	
	
}
