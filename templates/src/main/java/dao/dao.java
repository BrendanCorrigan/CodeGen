#set ($instanceName = $stringUtils.uncapitalize(${bean.name}))
package ${bean.packageName}.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ${bean.packageName}.beans.${bean.name};
import ${bean.packageName}.beans.ValidationMessage;

/**
 * ${bean.name} Data Access Object (DAO).
 * 
 * This class contains all database handling that is needed to permanently store
 * and retrieve Person ${bean.name} instances from table ${bean.table}
 * 
 * This source code has been automatically generated.
 * 
 *  @author auto-generated
 */
@SuppressWarnings("unused")
public class ${bean.name}DAO {

	// Logger
	private static final Logger logger = LogManager.getLogger(${bean.name}DAO.class);

	// The connection to the database for this instance.
	protected Connection conn;

	// SQL to select by primary key - ${bean.primaryKey.columnName}.
	private static final String SELECT_BY_ID = "SELECT * FROM ${bean.table} WHERE (${bean.primaryKey.columnName} = ? )";

	// SQL to select all records from the table
	private static final String SELECT_ALL = "SELECT * FROM ${bean.table} ORDER BY ${bean.primaryKey.columnName} ASC";

	// SQL to count rows on the table.
	private static final String COUNT_ALL = "SELECT count(*) FROM ${bean.table}";

	// SQL to delete a specific row from the table using the primary key - ${bean.primaryKey.name}.
	private static final String DELETE_BY_ID = "DELETE FROM ${bean.table} WHERE (${bean.primaryKey.columnName} = ? )";

	// SQL to delete all rows from the table.
	private static final String DELETE_ALL = "DELETE FROM ${bean.table}";
	
	// SQL to update an entry on the table
	private static final String UPDATE_RECORD = "UPDATE ${bean.table} "
			+ "SET ${updates}"
			+ " WHERE (${bean.primaryKey.columnName} = ?)";

	// SQL to insert a new ${bean.name} on to the table
	private static final String INSERT_RECORD = "INSERT INTO ${bean.table} "
			+ "(${fields})"
			+ "VALUES (${params}) ";

	/**
	 * Create a new DAO with instance scope connection.
	 * 
	 * @param conn
	 */
	public ${bean.name}DAO(Connection conn) {
		this.conn = conn;
	}

	/////////////////////////////////////////////////////////////////////////

	/**
	 * Returns a ${bean.name} from the database with the given primary key or throws a
	 * NotFoundException if the object does not exist. Throws an SQLException if
	 * there are any other errors.
	 * 
	 * @param ${bean.primaryKey.name}
	 *            the primary key of the ${bean.name} to return
	 * @return the ${bean.name} if this exists on the database, otherwise it throws a
	 *         NotFoundException
	 * @throws NotFoundException
	 * @throws SQLException
	 */
	public ${bean.name} get(${bean.primaryKey.type} ${bean.primaryKey.name}) throws NotFoundException, SQLException {
		logger.trace("Enter get method with ${bean.primaryKey.name}: " + ${bean.primaryKey.name});

		PreparedStatement stmt = null;
		${bean.name} model = new ${bean.name}();

		try {
			stmt = conn.prepareStatement(SELECT_BY_ID);
			stmt.set$stringUtils.capitalize(${bean.primaryKey.type})(1, ${bean.primaryKey.name});

			execute(stmt, model);

		} finally {
			if (stmt != null)
				stmt.close();
		}

		logger.trace("Exit get method");
		return model;
	}

	/**
	 * Deletes a ${bean.name} from the database with the given primary key or throws a
	 * NotFoundException if the object does not exist. Throws an SQLException if
	 * there are any other errors.
	 * 
	 * @param ${bean.primaryKey.name}
	 *            the primary key of the ${bean.name} to delete
	 * @return true if the object has been deleted, false otherwise.
	 * @throws NotFoundException
	 * @throws SQLException
	 */
	public boolean delete(${bean.primaryKey.type} ${bean.primaryKey.name}) throws NotFoundException, SQLException {

		logger.trace("Enter delete method with ${bean.primaryKey.name}: " + ${bean.primaryKey.name});

		PreparedStatement stmt = null;
		int deleted = 0;
		boolean successfulDelete = false;

		try {
			stmt = conn.prepareStatement(DELETE_BY_ID);
			stmt.set$stringUtils.capitalize(${bean.primaryKey.type})(1, ${bean.primaryKey.name});
			
		    deleted = stmt.executeUpdate();
			logger.debug("Deleted: " + deleted + " entries from the ${bean.table} table");

			if (deleted == 0) {
				logger.warn("Object could not be deleted - PrimaryKey not found:" + ${bean.primaryKey.name});
				throw new NotFoundException("Object could not be deleted! PrimaryKey not found:" + ${bean.primaryKey.name});

			} else if (deleted > 1) {
				logger.warn("PrimaryKey Error when updating DB! (Many objects were deleted!)");
				throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");

			} else {
				logger.debug("${bean.name} entry deleted with primary key: " + ${bean.primaryKey.name});
				successfulDelete = true;
			}

		} finally {

			// Close the prepared statement
			if (stmt != null) {
				stmt.close();
			}
		}

		logger.trace("Exit delete method");
		return successfulDelete;
	}

	/**
	 * Create a new ${bean.name} on the ${bean.table} table
	 * 
	 * @param model
	 *            - the ${bean.name} to be added to the ${bean.table} table
	 */
	public void create(${bean.name} model) throws SQLException {

		logger.trace("Enter create method");
		logger.debug("${bean.name} = " + model.toString());

		// Should validate the model here....
		
//		List<ValidationMessage> validation = ${bean.name}Validator.validate(model);
//		if (validation == null || validation.isEmpty()) {
//		}
		
		// Create the prepared statement for the create...
		PreparedStatement stmt = null;
		ResultSet result = null;

        try {
            stmt = conn.prepareStatement(INSERT_RECORD, Statement.RETURN_GENERATED_KEYS);

#foreach($attribute in $bean.attributes)
#if (${attribute.type} == "Date" || ${attribute.type} == "Timestamp" )
			// Need to handle the case if the date being passed is null
			if (model.get$stringUtils.capitalize(${attribute.name})() != null) {
				stmt.setTimestamp($velocityCount, new java.sql.Timestamp(model.get$stringUtils.capitalize(${attribute.name})().getTime()));
			} else {
				stmt.setNull($velocityCount, Types.TIMESTAMP);
			}
#else
			stmt.set$stringUtils.capitalize(${attribute.type})($velocityCount, model.get$stringUtils.capitalize(${attribute.name})());
#end
#end   

			int insertedCount = stmt.executeUpdate();
            
            if (insertedCount != 1) {
				logger.warn("PrimaryKey Error when updating DB! - insert count: " + insertedCount);
				throw new SQLException("PrimaryKey Error when updating DB! - insert count " + insertedCount);
				
			} else {

				result = stmt.getGeneratedKeys();
        		if (result.next()) {
					model.set$stringUtils.capitalize(${bean.primaryKey.name})((${bean.primaryKey.type}) result.get$stringUtils.capitalize(${bean.primaryKey.type})(1));
					
        		} else {
        			// No key was generated - this should cause an error
    				logger.error("Could not retrieve primary key after insert");
    				throw new SQLException("Could not retrieve primary key after insert");
        		}			
			}

		} finally {

			// Close the prepared statement
			if (stmt != null)
				stmt.close();
		}
	
		logger.trace("Exit create method");
	}

	
	
	/**
	 * Update an existing  ${bean.name} on the ${bean.table} table
	 * 
	 * @param model
	 *            - the ${bean.name} to be added to the ${bean.table} table
	 */
	public void update(${bean.name} model) throws SQLException {

		logger.trace("Enter update method");
		logger.debug("${bean.name} = " + model.toString());

		// Should validate the model here....
		
//		List<ValidationMessage> validation = ${bean.name}Validator.validate(model);
//		if (validation == null || validation.isEmpty()) {
//		}
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(UPDATE_RECORD);

#foreach($attribute in $bean.attributes)
#if (${attribute.type} == "Date" || ${attribute.type} == "Timestamp" )
			// Need to handle the case if the date being passed is null
			if (model.get$stringUtils.capitalize(${attribute.name})() != null) {
				stmt.setTimestamp($velocityCount, new java.sql.Timestamp(model.get$stringUtils.capitalize(${attribute.name})().getTime()));
			} else {
				stmt.setNull($velocityCount, Types.TIMESTAMP);
			}
#else
			stmt.set$stringUtils.capitalize(${attribute.type})($velocityCount, model.get$stringUtils.capitalize(${attribute.name})());
#end
#end   

			// Index
#set($id_index = $bean.attributes.size() + 1)
			stmt.set$stringUtils.capitalize(${bean.primaryKey.type})($id_index, model.get$stringUtils.capitalize(${bean.primaryKey.name})());

			int updatedCount = stmt.executeUpdate();
           
			if (updatedCount != 1) {
				logger.warn("PrimaryKey Error when updating DB! - updated count: " + updatedCount);
				throw new SQLException("PrimaryKey Error when updating DB! - update count " + updatedCount);	
			}
		} finally {

			// Close the prepared statement
			if (stmt != null)
				stmt.close();
		}
	
		logger.trace("Exit update method");
	}
	
	
	/////////////////////////////////////////////////////////////////////////

	/**
	 * Helper class which counts the number of records on the table. This should
	 * be called before the "getAll" function to evaluate whether getting all of
	 * the records is appropriate.
	 * 
	 * @return a count of all the records in the table
	 * @throws SQLException
	 */
	public int count() throws SQLException {
		logger.trace("Enter count method");

		PreparedStatement stmt = null;
		ResultSet result = null;
		int count = 0;

		try {
			stmt = conn.prepareStatement(COUNT_ALL);
			result = stmt.executeQuery();

			if (result.next()) {
				count = result.getInt(1);
				logger.debug("Found: " + count + " entries on the ${bean.table} table");
			}

		} finally {
			// Close off the results object.
			if (result != null) {
				result.close();
			}

			// Close the prepared statement
			if (stmt != null) {
				stmt.close();
			}
		}

		logger.trace("Exit count method");
		return count;

	}

	/**
	 * Retrieve all ${bean.name} from the ${bean.table} table.
	 * 
	 * WARNING
	 * 
	 * THIS MAY RETRIEVE A LARGE NUMBER OF OBJECT. YOU MIGHT WANT TO 
	 * COUNT THE NUMBER OF OBJECTS TO BE RETRIEVED BEFORE CALLING THIS
	 * METHOD.
	 * 
	 * @return List<${bean.name}>
	 * @throws SQLException
	 */
	public List<${bean.name}> getAll() throws SQLException {
		logger.trace("Enter getAll method");

		PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
		List<${bean.name}> searchResults = executeList(stmt);

		logger.trace("Exit getAll method");
		return searchResults;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	/////// REMOVE THIS METHOD IF YOU DON'T REQUIRE TO CLEAR THE TABLE ////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Delete all entries from the table.
	 * 
	 * WARNING - THIS WILL CLEAR ALL ENTRIES FROM THE TABLE
	 * 
	 * @return the number of entries deleted from the table
	 * @throws SQLException
	 */
	public int deleteAll() throws SQLException {
		logger.trace("Enter deleteAll method");

		PreparedStatement stmt = null;
		int deleted = 0;

		try {
			stmt = conn.prepareStatement(DELETE_ALL);
			deleted = stmt.executeUpdate();
			logger.debug("Deleted: " + deleted + " entries from the ${bean.table} table");

		} finally {

			// Close the prepared statement
			if (stmt != null) {
				stmt.close();
			}
		}

		logger.trace("Exit deleteAll method");
		return deleted;

	}

	/////////////////////////////////////////////////////////////////////////

	/**
	 * Execute the prepared statement and return an object of the correct type
	 * rather than a result object. This method should re-map any SQL objects to
	 * their native Java Objects.
	 * 
	 * @param stmt
	 *            - the prepared statement to execute
	 * @param model
	 *            - the ${bean.name} object to populate
	 * @throws NotFoundException
	 *             if there is no data to retrieve
	 * @throws SQLException
	 *             if there is an other SQL problems
	 */
	protected void execute(PreparedStatement stmt, ${bean.name} model) throws NotFoundException, SQLException {

		logger.trace("Enter execute method");

		ResultSet result = null;

		try {
			result = stmt.executeQuery();

			if (result.next()) {

				// Add the fields to the ${bean.name} object
				mapResult(result, model);
				logger.debug("Retrieved Object: " + model.toString());

			} else {
				logger.debug("Did not find any results after executing prepared statement: ");
				logger.debug(stmt.toString());

				// Rule is to throw a "NotFoundException" if there are no results
				throw new NotFoundException("${bean.name} Object Not Found!");
			}

		} finally {

			// Close off the results object.
			if (result != null) {
				result.close();
			}

			// Close the prepared statement
			if (stmt != null) {
				stmt.close();
			}
		}

		logger.trace("Exit execute method");

	}

	/**
	 * Execute the prepared statement and return a list of objects of the
	 * correct type rather than a result object.
	 * 
	 * @param stmt
	 *            - the prepared statement to execute
	 * @return
	 * @throws SQLException
	 *             if there is a SQL problem
	 */
	protected List<${bean.name}> executeList(PreparedStatement stmt) throws SQLException {

		logger.trace("Enter execute list method");

		List<${bean.name}> searchResults = new ArrayList<${bean.name}>();
		ResultSet result = null;

		try {
			result = stmt.executeQuery();

			while (result.next()) {
				${bean.name} model = new ${bean.name}();
				mapResult(result, model);

				searchResults.add(model);
			}

			if (searchResults != null && searchResults.size() > 0) {
				logger.debug("Search results contains: " + searchResults.size() + " entries");

			} else {
				logger.debug("Did not return any results after executing prepared statement: ");
			}

		} finally {
			// Close off the results object.
			if (result != null) {
				result.close();
			}

			// Close the prepared statement
			if (stmt != null) {
				stmt.close();
			}
		}

		logger.trace("Exit execute list method");
		return searchResults;
	}

	/**
	 * Utility class for mapping the results set to the object.
	 * 
	 * @param result
	 * @param model
	 * @return
	 * @throws SQLException
	 *             if there is a SQL problem
	 */
     private ${bean.name} mapResult(ResultSet result, ${bean.name} model) throws SQLException {
     
        // Add the fields to the ${bean.name} object

    	// map the primary key
		model.set$stringUtils.capitalize(${bean.primaryKey.name})((${bean.primaryKey.type}) result.get$stringUtils.capitalize(${bean.primaryKey.type})("${bean.primaryKey.columnName}"));
        
		// map the remaining attributes...
#foreach($attribute in $bean.attributes)
#if (${attribute.type} == "Date") 
        model.set$stringUtils.capitalize(${attribute.name})(result.getTimestamp("${attribute.columnName}"));
#else
        model.set$stringUtils.capitalize(${attribute.name})(result.get$stringUtils.capitalize(${attribute.type})("${attribute.columnName}"));
#end
#end

        return model;
     }

}
