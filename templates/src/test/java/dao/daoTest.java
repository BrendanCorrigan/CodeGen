#set ($instanceName = $stringUtils.uncapitalize(${bean.name}))
package ${bean.packageName}.dao;

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

import ${bean.packageName}.beans.${bean.name};

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
public class ${bean.name}DAOTest {

	// Logger
	private static final Logger logger = LogManager.getLogger(${bean.name}DAOTest.class);

	// The ddl for the table to create for test - will create new table if one does not already exist.
	private static final String SQLFILENAME = "sql/${bean.table}_HSQLDB.sql";
    
    // Database connection
	private static Connection conn = null;

	// The DAO to test...
	private static ${bean.name}DAO dao = null;

    @BeforeClass
    public static void setUp() throws Exception {
    	
    	logger.trace("${bean.name}DAOTest setUp()");
    	
    	// As this is test - create a connection directly to the HSQLDB database
    	conn = ConnectionManager.getInstance().getConnection(ConnectionManager.HSQLDB);
    	
    	// Create the database if it does not exist already.
    	ConnectionManager.getInstance().runScript(ConnectionManager.HSQLDB, SQLFILENAME);
    	
    	// Create a new Data access object for the object being tested.
    	dao = new ${bean.name}DAO(conn);

    }
    
    
    @Test
    public void countRecords() {
    	logger.trace("${bean.name}DAOTest countRecord()");
    	
    	try {    		
    		
    		int count = dao.count();
			logger.debug("--> Number of records before insert = " + count);

			${bean.name} model = insertRecord();
        	
    		int newCount = dao.count();
			logger.debug("--> Number of records after insert = " + newCount);
			
    		assertFalse(count == newCount);

    		boolean deleted = dao.delete(model.get$stringUtils.capitalize(${bean.primaryKey.name})());
    		assertTrue(deleted);
    					
			
		} catch (SQLException e) {
			logger.error(e);
			
		} catch (NotFoundException e) {
			logger.error(e);
			
		}
    	
    }

    
    
    
    
    @Test
    public void  testInsertRecord() {

    	${bean.name} model = insertRecord();    	    	
		assertFalse(model.get$stringUtils.capitalize(${bean.primaryKey.name})() == 0);
				
    	
    }
    
    
    public ${bean.name}  insertRecord() {
    	logger.trace("${bean.name}DAOTest insertRecord()");

    	${bean.name} model = new ${bean.name}();

#foreach($attribute in $bean.attributes)
#if($attribute.mandatory)
#if($attribute.type == "String")
		model.set$stringUtils.capitalize(${attribute.name})("temp");
#else
		//model.set$stringUtils.capitalize(${attribute.name})(); <-- THIS IS A MANDATORY $attribute.type
#end
#else
		//model.set$stringUtils.capitalize(${attribute.name})(); <-- create a random $attribute.type
#end
#end
    	
    	try {
			dao.create(model);			
			
		} catch (SQLException e) {
			logger.error(e);
		}

		return model;

    }

    
    @Test
    public void testUpdateRecord() {
    	logger.trace("${bean.name}DAOTest testUpdateRecord()");
    	
    	try {    		
    		
    		${bean.name} model = insertRecord();

	#foreach($attribute in $bean.attributes)
	#if($attribute.mandatory)
	#if($attribute.type == "String")
			model.set$stringUtils.capitalize(${attribute.name})("new value");
	#else
			//model.set$stringUtils.capitalize(${attribute.name})(); <-- THIS IS A MANDATORY $attribute.type
	#end
	#else
			//model.set$stringUtils.capitalize(${attribute.name})(); <-- create a random $attribute.type
	#end
	#end
    		
        	dao.update(model);

			${bean.name} updatedModel = dao.get(model.get$stringUtils.capitalize(${bean.primaryKey.name})());
			assertNotNull(updatedModel);
			
        	
    		boolean deleted = dao.delete(model.get$stringUtils.capitalize(${bean.primaryKey.name})());
    		assertTrue(deleted);
    					
			
		} catch (SQLException e) {
			logger.error(e);
			
		} catch (NotFoundException e) {
			logger.error(e);
			
		}
    	
    }
    
    
    
    @AfterClass
    public static void closeConnection() throws SQLException {
    	logger.trace("${bean.name}DAOTest closeConnection()");

    	ConnectionManager.getInstance().shutdown(conn);
    	if (!conn.isClosed()) {
    		conn.close();
    	}
    }
    
    
	
}
