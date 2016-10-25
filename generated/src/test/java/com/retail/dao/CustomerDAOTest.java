package com.retail.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.retail.beans.Customer;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
public class CustomerDAOTest {

	// Logger
	private static final Logger logger = LogManager.getLogger(CustomerDAOTest.class);

	// The ddl for the table to create for test - will create new table if one does not already exist.
	private static final String SQLFILENAME = "sql/customers_HSQLDB.sql";
    
    // Database connection
	private static Connection conn = null;

	// The DAO to test...
	private static CustomerDAO dao = null;

    @BeforeClass
    public static void setUp() throws Exception {
    	
    	logger.trace("CustomerDAOTest setUp()");
    	
    	// As this is test - create a connection directly to the HSQLDB database
    	conn = ConnectionManager.getInstance().getConnection(ConnectionManager.HSQLDB);
    	
    	// Create the database if it does not exist already.
    	ConnectionManager.getInstance().runScript(ConnectionManager.HSQLDB, SQLFILENAME);
    	
    	// Create a new Data access object for the object being tested.
    	dao = new CustomerDAO(conn);

    }
    
    
    @Test
    public void countRecords() {
    	logger.trace("CustomerDAOTest countRecord()");
    	
    	try {    		
    		
    		int count = dao.count();
			logger.debug("--> Number of records before insert = " + count);

			Customer model = insertRecord();
        	
    		int newCount = dao.count();
			logger.debug("--> Number of records after insert = " + newCount);
			
    		assertFalse(count == newCount);

    		boolean deleted = dao.delete(model.getCustomerId());
    		assertTrue(deleted);
    					
			
		} catch (SQLException e) {
			logger.error(e);
			
		} catch (NotFoundException e) {
			logger.error(e);
			
		}
    	
    }

    
    
    
    
    @Test
    public void  testInsertRecord() {

    	Customer model = insertRecord();    	    	
		assertFalse(model.getCustomerId() == 0);
				
    	
    }
    
    
    public Customer  insertRecord() {
    	logger.trace("CustomerDAOTest insertRecord()");

    	Customer model = new Customer();

		model.setForename("temp");
		model.setSurname("temp");
		//model.setPhone(); <-- create a random String
		model.setAddressLine1("temp");
		//model.setAddressLine2(); <-- create a random String
		model.setCity("temp");
		//model.setPostCode(); <-- create a random String
		//model.setCountry(); <-- create a random String
		//model.setSalesRepEmployeeId(); <-- create a random int
		//model.setCreditLimit(); <-- create a random int
    	
    	try {
			dao.create(model);			
			
		} catch (SQLException e) {
			logger.error(e);
		}

		return model;

    }

    
    @Test
    public void testUpdateRecord() {
    	logger.trace("CustomerDAOTest testUpdateRecord()");
    	
    	try {    		
    		
    		Customer model = insertRecord();

						model.setForename("new value");
								model.setSurname("new value");
							//model.setPhone(); <-- create a random String
							model.setAddressLine1("new value");
							//model.setAddressLine2(); <-- create a random String
							model.setCity("new value");
							//model.setPostCode(); <-- create a random String
						//model.setCountry(); <-- create a random String
						//model.setSalesRepEmployeeId(); <-- create a random int
						//model.setCreditLimit(); <-- create a random int
		    		
        	dao.update(model);

			Customer updatedModel = dao.get(model.getCustomerId());
			assertNotNull(updatedModel);
			
        	
    		boolean deleted = dao.delete(model.getCustomerId());
    		assertTrue(deleted);
    					
			
		} catch (SQLException e) {
			logger.error(e);
			
		} catch (NotFoundException e) {
			logger.error(e);
			
		}
    	
    }
    
    
    
    @AfterClass
    public static void closeConnection() throws SQLException {
    	logger.trace("CustomerDAOTest closeConnection()");

    	ConnectionManager.getInstance().shutdown(conn);
    	if (!conn.isClosed()) {
    		conn.close();
    	}
    }
    
    
	
}
