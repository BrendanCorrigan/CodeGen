package generator.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DatabaseDao {

	// Logger
	private static final Logger logger = LogManager.getLogger(DatabaseDao.class);

	// The connection to the database for this instance.
	protected Connection conn;


	public DatabaseDao(Connection conn) {
		this.conn = conn;
	}

	
	/**
	 * 
	 * @return
	 */
	public List<String> getTables() {
		logger.trace("getTables()");
		List<String>results = new ArrayList<String>();
		
		try {
			DatabaseMetaData md = conn.getMetaData();
			
			ResultSet rs = md.getTables(null, null, "%", null);

			while (rs.next()) {
		      String tableName = rs.getString("TABLE_NAME");
		      results.add(tableName);

			}
			
		} catch (SQLException e) {
			logger.error(e);
			
		}

		return results;
	}


	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public List<Map<String, String>> getColumns(String tableName) {
		logger.trace("getColumns()");
		
		List<Map<String, String>>results = new ArrayList<Map<String, String>>();

		try {

			//
			// Get the column information
			//
						
			DatabaseMetaData md = conn.getMetaData();			
			ResultSet rs = md.getColumns(null, null, tableName, "%");
			
			while (rs.next()) {
				Map<String, String> result = new HashMap<String, String>();
				
				result.put("TABLE_NAME", rs.getString("TABLE_NAME"));
				result.put("ORDINAL_POSITION", rs.getString("ORDINAL_POSITION"));
				result.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
				result.put("TYPE_NAME", rs.getString("TYPE_NAME"));
				result.put("COLUMN_SIZE", rs.getString("COLUMN_SIZE"));
				result.put("IS_NULLABLE", rs.getString("IS_NULLABLE"));
				result.put("IS_AUTOINCREMENT", rs.getString("IS_AUTOINCREMENT"));
				
				results.add(result);
				
			}

			//
			// Get the primary Keys
			//
			
			rs = md.getPrimaryKeys(null, null, tableName);
			
			while (rs.next()) {
				String primaryKey = rs.getString("COLUMN_NAME");
				
				for(Map<String, String> column : results ){
					
					if (column.get("COLUMN_NAME").equals(primaryKey)) {
						column.put("IS_PRIMARY_KEY", "TRUE");
						break;
					}
				}				
			}
	
			//
			// For when I am brave enough to tackle foreign keys.
			//

			rs = md.getImportedKeys(null, null, tableName);
			
			while (rs.next()) {
				String foreignKey = rs.getString("FKCOLUMN_NAME");
				
				for(Map<String, String> column : results ){
					
					if (column.get("COLUMN_NAME").equals(foreignKey)) {
						column.put("IS_FOREIGN_KEY", "TRUE");
						column.put("PKTABLE_NAME", rs.getString("PKTABLE_NAME"));
						column.put("PKCOLUMN_NAME", rs.getString("PKCOLUMN_NAME"));
						break;
					}
				}				
			}
						
			
		} catch (SQLException e) {
			logger.error(e);
		}
		
		
		return results;
	}

	
	
	
	@SuppressWarnings("unused")
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private String dumpResultSetString(ResultSet rs) throws SQLException {

		StringBuilder sb = new StringBuilder("[");
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		int count = 0;
		
		while (rs.next()) {
			if (count > 0) sb.append(", "); 			
			sb.append("{");
			
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) sb.append(",  ");
				String columnValue = rs.getString(i);
				sb.append('"').append(rsmd.getColumnName(i)).append("\":\"").append(columnValue).append('"');
			}
			sb.append("}");
			count++;
		}

		return sb.toString();
	}
	

	@SuppressWarnings("unused")
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, String>> dumpResultSetMap(ResultSet rs) throws SQLException {
	
		List<Map<String, String>>results = new ArrayList<Map<String, String>>();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		
		while (rs.next()) {		
			Map<String, String> result = new HashMap<String, String>();
						
			for (int i = 1; i <= columnsNumber; i++) {				
				result.put(rsmd.getColumnName(i), rs.getString(i));
			}
			results.add(result);
		}

		return results;
	}
		
	
	
}
