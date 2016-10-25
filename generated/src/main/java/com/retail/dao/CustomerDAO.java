package com.retail.dao;

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

import com.retail.beans.Customer;
import com.retail.beans.ValidationMessage;

/**
 * Customer Data Access Object (DAO).
 * 
 * This class contains all database handling that is needed to permanently store
 * and retrieve Person Customer instances from table customers
 * 
 * This source code has been automatically generated.
 * 
 *  @author auto-generated
 */
@SuppressWarnings("unused")
public class CustomerDAO {

	// Logger
	private static final Logger logger = LogManager.getLogger(CustomerDAO.class);

	// The connection to the database for this instance.
	protected Connection conn;

	// SQL to select by primary key - customerId.
	private static final String SELECT_BY_ID = "SELECT * FROM customers WHERE (customerId = ? )";

	// SQL to select all records from the table
	private static final String SELECT_ALL = "SELECT * FROM customers ORDER BY customerId ASC";

	// SQL to count rows on the table.
	private static final String COUNT_ALL = "SELECT count(*) FROM customers";

	// SQL to delete a specific row from the table using the primary key - customerId.
	private static final String DELETE_BY_ID = "DELETE FROM customers WHERE (customerId = ? )";

	// SQL to delete all rows from the table.
	private static final String DELETE_ALL = "DELETE FROM customers";
	
	// SQL to update an entry on the table
	private static final String UPDATE_RECORD = "UPDATE customers "
			+ "SET forename=?,surname=?,phone=?,addressLine1=?,addressLine2=?,city=?,postCode=?,country=?,salesRepEmployeeId=?,creditLimit=?"
			+ " WHERE (customerId = ?)";

	// SQL to insert a new Customer on to the table
	private static final String INSERT_RECORD = "INSERT INTO customers "
			+ "(forename, surname, phone, addressLine1, addressLine2, city, postCode, country, salesRepEmployeeId, creditLimit)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	/**
	 * Create a new DAO with instance scope connection.
	 * 
	 * @param conn
	 */
	public CustomerDAO(Connection conn) {
		this.conn = conn;
	}

	/////////////////////////////////////////////////////////////////////////

	/**
	 * Returns a Customer from the database with the given primary key or throws a
	 * NotFoundException if the object does not exist. Throws an SQLException if
	 * there are any other errors.
	 * 
	 * @param customerId
	 *            the primary key of the Customer to return
	 * @return the Customer if this exists on the database, otherwise it throws a
	 *         NotFoundException
	 * @throws NotFoundException
	 * @throws SQLException
	 */
	public Customer get(int customerId) throws NotFoundException, SQLException {
		logger.trace("Enter get method with customerId: " + customerId);

		PreparedStatement stmt = null;
		Customer model = new Customer();

		try {
			stmt = conn.prepareStatement(SELECT_BY_ID);
			stmt.setInt(1, customerId);

			execute(stmt, model);

		} finally {
			if (stmt != null)
				stmt.close();
		}

		logger.trace("Exit get method");
		return model;
	}

	/**
	 * Deletes a Customer from the database with the given primary key or throws a
	 * NotFoundException if the object does not exist. Throws an SQLException if
	 * there are any other errors.
	 * 
	 * @param customerId
	 *            the primary key of the Customer to delete
	 * @return true if the object has been deleted, false otherwise.
	 * @throws NotFoundException
	 * @throws SQLException
	 */
	public boolean delete(int customerId) throws NotFoundException, SQLException {

		logger.trace("Enter delete method with customerId: " + customerId);

		PreparedStatement stmt = null;
		int deleted = 0;
		boolean successfulDelete = false;

		try {
			stmt = conn.prepareStatement(DELETE_BY_ID);
			stmt.setInt(1, customerId);
			
		    deleted = stmt.executeUpdate();
			logger.debug("Deleted: " + deleted + " entries from the customers table");

			if (deleted == 0) {
				logger.warn("Object could not be deleted - PrimaryKey not found:" + customerId);
				throw new NotFoundException("Object could not be deleted! PrimaryKey not found:" + customerId);

			} else if (deleted > 1) {
				logger.warn("PrimaryKey Error when updating DB! (Many objects were deleted!)");
				throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");

			} else {
				logger.debug("Customer entry deleted with primary key: " + customerId);
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
	 * Create a new Customer on the customers table
	 * 
	 * @param model
	 *            - the Customer to be added to the customers table
	 */
	public void create(Customer model) throws SQLException {

		logger.trace("Enter create method");
		logger.debug("Customer = " + model.toString());

		// Should validate the model here....
		
//		List<ValidationMessage> validation = CustomerValidator.validate(model);
//		if (validation == null || validation.isEmpty()) {
//		}
		
		// Create the prepared statement for the create...
		PreparedStatement stmt = null;
		ResultSet result = null;

        try {
            stmt = conn.prepareStatement(INSERT_RECORD, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, model.getForename());
			stmt.setString(2, model.getSurname());
			stmt.setString(3, model.getPhone());
			stmt.setString(4, model.getAddressLine1());
			stmt.setString(5, model.getAddressLine2());
			stmt.setString(6, model.getCity());
			stmt.setString(7, model.getPostCode());
			stmt.setString(8, model.getCountry());
			stmt.setInt(9, model.getSalesRepEmployeeId());
			stmt.setInt(10, model.getCreditLimit());

			int insertedCount = stmt.executeUpdate();
            
            if (insertedCount != 1) {
				logger.warn("PrimaryKey Error when updating DB! - insert count: " + insertedCount);
				throw new SQLException("PrimaryKey Error when updating DB! - insert count " + insertedCount);
				
			} else {

				result = stmt.getGeneratedKeys();
        		if (result.next()) {
					model.setCustomerId((int) result.getInt(1));
					
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
	 * Update an existing  Customer on the customers table
	 * 
	 * @param model
	 *            - the Customer to be added to the customers table
	 */
	public void update(Customer model) throws SQLException {

		logger.trace("Enter update method");
		logger.debug("Customer = " + model.toString());

		// Should validate the model here....
		
//		List<ValidationMessage> validation = CustomerValidator.validate(model);
//		if (validation == null || validation.isEmpty()) {
//		}
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(UPDATE_RECORD);

			stmt.setString(1, model.getForename());
			stmt.setString(2, model.getSurname());
			stmt.setString(3, model.getPhone());
			stmt.setString(4, model.getAddressLine1());
			stmt.setString(5, model.getAddressLine2());
			stmt.setString(6, model.getCity());
			stmt.setString(7, model.getPostCode());
			stmt.setString(8, model.getCountry());
			stmt.setInt(9, model.getSalesRepEmployeeId());
			stmt.setInt(10, model.getCreditLimit());

			// Index
			stmt.setInt(11, model.getCustomerId());

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
				logger.debug("Found: " + count + " entries on the customers table");
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
	 * Retrieve all Customer from the customers table.
	 * 
	 * WARNING
	 * 
	 * THIS MAY RETRIEVE A LARGE NUMBER OF OBJECT. YOU MIGHT WANT TO 
	 * COUNT THE NUMBER OF OBJECTS TO BE RETRIEVED BEFORE CALLING THIS
	 * METHOD.
	 * 
	 * @return List<Customer>
	 * @throws SQLException
	 */
	public List<Customer> getAll() throws SQLException {
		logger.trace("Enter getAll method");

		PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
		List<Customer> searchResults = executeList(stmt);

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
			logger.debug("Deleted: " + deleted + " entries from the customers table");

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
	 *            - the Customer object to populate
	 * @throws NotFoundException
	 *             if there is no data to retrieve
	 * @throws SQLException
	 *             if there is an other SQL problems
	 */
	protected void execute(PreparedStatement stmt, Customer model) throws NotFoundException, SQLException {

		logger.trace("Enter execute method");

		ResultSet result = null;

		try {
			result = stmt.executeQuery();

			if (result.next()) {

				// Add the fields to the Customer object
				mapResult(result, model);
				logger.debug("Retrieved Object: " + model.toString());

			} else {
				logger.debug("Did not find any results after executing prepared statement: ");
				logger.debug(stmt.toString());

				// Rule is to throw a "NotFoundException" if there are no results
				throw new NotFoundException("Customer Object Not Found!");
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
	protected List<Customer> executeList(PreparedStatement stmt) throws SQLException {

		logger.trace("Enter execute list method");

		List<Customer> searchResults = new ArrayList<Customer>();
		ResultSet result = null;

		try {
			result = stmt.executeQuery();

			while (result.next()) {
				Customer model = new Customer();
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
     private Customer mapResult(ResultSet result, Customer model) throws SQLException {
     
        // Add the fields to the Customer object

    	// map the primary key
		model.setCustomerId((int) result.getInt("customerId"));
        
		// map the remaining attributes...
        model.setForename(result.getString("forename"));
        model.setSurname(result.getString("surname"));
        model.setPhone(result.getString("phone"));
        model.setAddressLine1(result.getString("addressLine1"));
        model.setAddressLine2(result.getString("addressLine2"));
        model.setCity(result.getString("city"));
        model.setPostCode(result.getString("postCode"));
        model.setCountry(result.getString("country"));
        model.setSalesRepEmployeeId(result.getInt("salesRepEmployeeId"));
        model.setCreditLimit(result.getInt("creditLimit"));

        return model;
     }

}
