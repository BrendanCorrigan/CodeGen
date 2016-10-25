#set ($instanceName = $stringUtils.uncapitalize(${bean.name}))
package ${bean.packageName}.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ${bean.packageName}.beans.${bean.name};
import ${bean.packageName}.beans.${bean.name}Validator;
import ${bean.packageName}.beans.ValidationMessage;
import ${bean.packageName}.dao.${bean.name}DAO;

// NOTE - THIS CLASS HAS BEEN ADDED AS A CONVENIENCE FOR TESTING PURPOSES.
import ${bean.packageName}.dao.ConnectionManager;


/**
 * Create a REST service on path "/$instanceName" which provides
 * basic SCRUD services:
 * 
 *    SEARCH
 *    CREATE
 *    READ
 *    UPDATE
 *    DELETE
 * 
 * @author auto-generated
 *
 */
@Path("/$instanceName")
public class ${bean.name}Resource {

	// The Data Access Object for database access
	private ${bean.name}DAO dao = null;

	// Logger
	private static final Logger logger = LogManager.getLogger(${bean.name}Resource.class);

	/**
	 * Constructor
	 */
	public ${bean.name}Resource() {

		logger.trace("${bean.name}Resource - New instance created.");
		
		// -------------------------------------------------------------------------
		// TODO Replace this with proper JNDI lookup for the resource 
		// -------------------------------------------------------------------------
		Connection conn = ConnectionManager.getInstance().getConnection();
		
		// Create a DAO which is used for the lifetime of this servlet.
		dao = new ${bean.name}DAO(conn);

	}

	/**
	 * Ping
	 * Convenience method to test end-to-end connection
	 * 
	 * @return Ping
	 */
	@GET
	@Path("/ping")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String ping() {

		//TODO - need to implement a PING on the DAO
		return "ping";
	}
	
	/**
	 * Return a list of all ${bean.name} items. 
	 *
	 * 
	 * @return a list of all ${bean.name} items
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<${bean.name}> getAll() {
		logger.trace("${bean.name}Resource - getAll()");

		List<${bean.name}> results = null;

		try {
			results = dao.getAll();

		} catch (SQLException e) {

			logger.error("SQL Error in dao.getAll() call", e);		
			throw new InternalServerErrorException("SQL error retrieving all instanced of ${bean.name}."); 
		}

		return results;
	};

	/**
	 * 
	 * 
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ${bean.name} getById(@PathParam("id") ${bean.primaryKey.type} id) {		
		logger.trace("${bean.name}Resource - getById (" + id + ")");
		
		${bean.name} result = null;

		if (id > 0) {
			try {
				result = dao.get(id);
	
			} catch ( ${bean.packageName}.dao.NotFoundException e) {
				logger.error("NotFoundException in dao.get() call with [id: " + id + "]", e);		
				throw new NotFoundException("No ${bean.name} with id: " + id + " found");
	
			} catch (SQLException e) {
				
				logger.error("SQL Error in dao.get() call with [id: " + id + "]", e);		
				throw new InternalServerErrorException(); 
	
			}
		} else {
			// the ID has not been set
			
			// Pass back a meaningful message to the service
			logger.error("No id passed to the getById method");
			throw new InternalServerErrorException("No id passed to the getById method");
		}
		
		return result;
	};

	
	
	/**
	 * 
	 * 
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public boolean deleteById(@PathParam("id") ${bean.primaryKey.type} id) {		
		logger.trace("${bean.name}Resource - deleteById (" + id + ")");
		
		boolean result = false;

		if (id > 0) {
			try {
				result = dao.delete(id);
                
                if (!result) {
                    logger.warn("Not deleted -  dao.delete() call with [id: " + id + "]");							
				}

			} catch (${bean.packageName}.dao.NotFoundException e) {
				logger.error("NotFoundException in dao.delete() call with [id: " + id + "]", e);		
				throw new NotFoundException("No  ${bean.name} with id: " + id + " found");
	
			} catch (SQLException e) {
				
				logger.error("SQL Error in dao.delete() call with [id: " + id + "]", e);		
				throw new InternalServerErrorException(); 
	
			}
		} else {
			// the ID has not been set
			
			// Pass back a meaningful message to the service
			logger.error("No id passed to the deleteById method");
			throw new InternalServerErrorException("No id passed to the deleteById method"); 
			
		}

		return result;
	};

	
	
	/**
	 * 
	 * @param model
	 * @return
	 */	
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public  ${bean.name} create( ${bean.name} model) {	
		logger.trace("${bean.name}Resource - create()");
		 ${bean.name} result = null;
		
		if (model != null) {
			logger.trace("${bean.name}Resource - create (" + model.toString() + ")");
			
			List<ValidationMessage> validation =  ${bean.name}Validator.validate(model);
			if (validation == null || validation.isEmpty()) {
				
				if (model.get$stringUtils.capitalize(${bean.primaryKey.name})() > 0) {
					
					// This model already has an Id - this should be an Update rather than a create
					String errorMessage = "This model already has an Id [" + model.get$stringUtils.capitalize(${bean.primaryKey.name})()+ "] - this should be an update(PUT) rather than a create(POST)";
					logger.error(errorMessage);
					throw new BadRequestException(errorMessage);					
				}
								
				try {
					
					dao.create(model);
					
					// maybe dao create should explicitely return a model?
					result = model;
					
				} catch (SQLException e) {
					
					logger.error("SQL Error in dao.get() call model [id: " + model.toString(), e);		
					throw new InternalServerErrorException(); 
				}

			} else {
				// the model has not been validated
				
				// shoudl pass back a meaningful message to the service - need to work out how to do this
				logger.error("This object was not validated successfully by ${bean.name}Validator: " + validation.toString());
				throw new InternalServerErrorException("This object was not validated successfully by ${bean.name}Validator."); 
			
			}
			
		} else {
			// the model is null - there has most likely been a problem parsing the JSON object
			
			// shoud pass back a meaningful message to the service - need to work out how to do this
			logger.error("No ${bean.name} object was created from the data passed to the Create method");
			throw new InternalServerErrorException(); 
			
		}
		
		return result;
				
	}
	

	/**
	 * 
	 * @param model - ${bean.name}
	 * @return ${bean.name}
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public  ${bean.name} update( ${bean.name} model) {
		
		logger.trace("${bean.name}Resource - update()");
		${bean.name} result = null;
		
		if (model != null) {
			logger.trace("${bean.name}Resource - update (" + model.toString() + ")");
			
			List<ValidationMessage> validation = ${bean.name}Validator.validate(model);
			if (validation == null || validation.isEmpty()) {
				
				if (model.get$stringUtils.capitalize(${bean.primaryKey.name})() < 1) {
					// This model already has no Id - this should be a create rather than an update
					logger.error("This model does not have an id - this should be a create(PUSH) rather than an update(POST");
					throw new BadRequestException("This model does not have an id - this should be a create(POST) rather than an update(PUT)");					
				}
								
				try {
					
					dao.update(model);
					
					// maybe dao create should explicitely return a model?
					result = model;
					
				} catch (SQLException e) {
					
					logger.error("SQL Error in dao.get() call model [id: " + model.toString(), e);		
					throw new InternalServerErrorException(); 
				}

			} else {
				// the model has not been validated
				
				// Pass back a meaningful message to the service.
				logger.error("This object was not validated successfully by ${bean.name}Validator: " + validation.toString());
				throw new InternalServerErrorException("This object was not validated successfully by ${bean.name}Validator."); 
							
			}
			
		} else {
			// the model is null - there has most likely been a problem parsing the JSON object
			
			// shoud pass back a meaningful message to the service - need to work out how to do this
			String errorMessage = "No ${bean.name} object was created from the data passed to the Update method";
			logger.error(errorMessage);
			throw new InternalServerErrorException(errorMessage); 
			
		}
		
		return result;
				
	}
	
		
	
	
}
