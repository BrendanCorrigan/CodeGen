package generator.app;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import generator.beans.BeanAttribute;
import generator.beans.BeanDefinition;

public class GenerateBean {

	 private static final Logger logger = LogManager.getLogger("GenerateBean");
	
	private static final String BEAN_TEMPLATE = "templates/src/main/java/beans/bean.java";
	private static final String BEAN_TEST_TEMPLATE = "templates/src/test/java/beans/beanTest.java";
	private static final String BEAN_VALIDATOR_TEMPLATE = "templates/src/main/java/beans/beanValidator.java";
	private static final String BEAN_VALIDATOR_TEST_TEMPLATE = "templates/src/test/java/beans/beanValidatorTest.java";
	private static final String VALIDATION_MESSAGE= "templates/src/main/java/beans/ValidationMessage.java";
	
	
	private static final String DAO_TEMPLATE = "templates/src/main/java/dao/dao.java";
	private static final String NOT_FOUND_EXCEPTION =  "templates/src/main/java/dao/NotFoundException.java";
	private static final String DAO_TEST_TEMPLATE = "templates/src/test/java/dao/daoTest.java";
	private static final String DATABASE_MANAGER = "templates/src/main/java/dao/DatabaseManager.java";
	private static final String CONNECTION_MANAGER = "templates/src/main/java/dao/ConnectionManager.java";
	private static final String DB_CONFIG = "templates/src/main/java/dao/DatabaseConfiguration.java";
	
	private static final String DDL_MYSQL_TEMPLATE = "templates/sql/MySQL.sql";
	private static final String DDL_HSQLDB_TEMPLATE = "templates/sql/HSQLDB.sql";
	private static final String DATABASES_TEMPLATE = "templates/sql/databases.json";
	
	private static final String RESOURCE_TEMPLATE = "templates/src/main/java/services/resource.java";
	private static final String RESOURCE_TEST_TEMPLATE = "templates/src/test/java/services/resourceTest.java";
	private static final String MAIN_APPLICATION = "templates/src/test/java/services/MainApplication.java";
	
    
	private static final String INDEX_TEMPLATE = "templates/src/main/webapp/index.html";
	private static final String HTML_TEMPLATE = "templates/src/main/webapp/main.html";
	private static final String JS_TEMPLATE = "templates/src/main/webapp/js/main.js";
	private static final String CSS_TEMPLATE = "templates/src/main/webapp/css/main.css";
    
	
	private static final String POM_TEMPLATE = "templates/pom.xml";
	private static final String LOG4J_TEMPLATE = "templates/src/main/resources/log4j2.xml";
	
	private static final String OUTPUT_DESTINATION = "generated/";
	private static final String MAIN_DESTINATION = "generated/src/main/java/";
	private static final String TEST_DESTINATION = "generated/src/test/java/";

	
	private static final String WEBSITE_FRAMEWORK = "templates/web";
	
    private static final String WEB_DESTINATION = "generated/src/main/webapp/";

	private static final String DEFINITION = "definitions/";

    
    
	
	public GenerateBean(String definitionFile) {
		
		logger.trace("New instance created");

		// Populate the bean using the given filename...
//		BeanDefinition beanDefinition = createBeanDefinition("Contact.json");	
		BeanDefinition beanDefinition = createBeanDefinition(definitionFile);	
		
		// Change the package name into a directory path...
		String packageDir = beanDefinition.getPackageName().replaceAll("\\.", "/");

		//---------------------------------------------------------------------------------------------------------------------------------------
		// Create the model / bean for the project
		//---------------------------------------------------------------------------------------------------------------------------------------
		
		File outputFile = new File(MAIN_DESTINATION + packageDir + "/beans/" + beanDefinition.getName() + ".java" );
		outputFile.getParentFile().mkdirs();
		populateTemplate(BEAN_TEMPLATE, beanDefinition, outputFile);
		
		outputFile = new File(MAIN_DESTINATION + packageDir + "/beans/" + beanDefinition.getName() + "Validator.java" );
		outputFile.getParentFile().mkdirs();
		populateTemplate(BEAN_VALIDATOR_TEMPLATE, beanDefinition, outputFile);

		outputFile = new File(MAIN_DESTINATION + packageDir + "/beans/ValidationMessage.java" );
		outputFile.getParentFile().mkdirs();
		populateTemplate(VALIDATION_MESSAGE, beanDefinition, outputFile);
		
		outputFile = new File(TEST_DESTINATION + packageDir + "/beans/" + beanDefinition.getName() + "Test.java" );
		outputFile.getParentFile().mkdirs();
		populateTemplate(BEAN_TEST_TEMPLATE, beanDefinition, outputFile);

		outputFile = new File(TEST_DESTINATION + packageDir + "/beans/" + beanDefinition.getName() + "ValidatorTest.java" );
		outputFile.getParentFile().mkdirs();
		populateTemplate(BEAN_VALIDATOR_TEST_TEMPLATE, beanDefinition, outputFile);

		
		//---------------------------------------------------------------------------------------------------------------------------------------
		// Create the database layer for the project based on DAO pattern
		//---------------------------------------------------------------------------------------------------------------------------------------

		
		outputFile = new File(MAIN_DESTINATION + packageDir + "/dao/" + beanDefinition.getName() + "DAO.java" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(DAO_TEMPLATE, beanDefinition, outputFile);

		outputFile = new File(MAIN_DESTINATION + packageDir + "/dao/NotFoundException.java" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(NOT_FOUND_EXCEPTION, beanDefinition, outputFile);

		outputFile = new File(MAIN_DESTINATION + packageDir + "/dao/DatabaseManager.java" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(DATABASE_MANAGER, beanDefinition, outputFile);

		outputFile = new File(MAIN_DESTINATION + packageDir + "/dao/DatabaseManager.java" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(DATABASE_MANAGER, beanDefinition, outputFile);

		outputFile = new File(MAIN_DESTINATION + packageDir + "/dao/ConnectionManager.java" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(CONNECTION_MANAGER, beanDefinition, outputFile);

		outputFile = new File(MAIN_DESTINATION + packageDir + "/dao/DatabaseConfiguration.java" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(DB_CONFIG, beanDefinition, outputFile);
        
		outputFile = new File(TEST_DESTINATION + packageDir + "/dao/" + beanDefinition.getName() + "DAOTest.java" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(DAO_TEST_TEMPLATE, beanDefinition, outputFile);

		
		//---------------------------------------------------------------------------------------------------------------------------------------
		// Create the REST services for the project
		//---------------------------------------------------------------------------------------------------------------------------------------

		
		outputFile = new File(MAIN_DESTINATION + packageDir + "/services/" + beanDefinition.getName() + "Resource.java" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(RESOURCE_TEMPLATE, beanDefinition, outputFile);

		outputFile = new File(TEST_DESTINATION + packageDir + "/services/" + beanDefinition.getName() + "ResourceTest.java" );			
		outputFile.getParentFile().mkdirs();
		populateTemplate(RESOURCE_TEST_TEMPLATE, beanDefinition, outputFile);
		
		outputFile = new File(TEST_DESTINATION + packageDir + "/services/MainApplication.java" );			
		outputFile.getParentFile().mkdirs();
		populateTemplate(MAIN_APPLICATION, beanDefinition, outputFile);
		
		
		//---------------------------------------------------------------------------------------------------------------------------------------
		// Create the database resources for the project
		//---------------------------------------------------------------------------------------------------------------------------------------

		
		outputFile = new File(OUTPUT_DESTINATION + "sql/" + beanDefinition.getTable() + "_HSQLDB.sql" );
		outputFile.getParentFile().mkdirs();
		populateTemplate(DDL_HSQLDB_TEMPLATE, beanDefinition, outputFile);

		outputFile = new File(OUTPUT_DESTINATION + "sql/" + beanDefinition.getTable() + "_MySQL.sql" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(DDL_MYSQL_TEMPLATE, beanDefinition, outputFile);

		outputFile = new File(OUTPUT_DESTINATION + "sql/databases.json" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(DATABASES_TEMPLATE, beanDefinition, outputFile);
		
		//---------------------------------------------------------------------------------------------------------------------------------------
		// Create supporting resources for the project
		//---------------------------------------------------------------------------------------------------------------------------------------
		
		outputFile = new File(OUTPUT_DESTINATION + "src/main/resources/log4j2.xml" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(LOG4J_TEMPLATE, beanDefinition, outputFile);
		
		outputFile = new File(OUTPUT_DESTINATION + "pom.xml" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(POM_TEMPLATE, beanDefinition, outputFile);
        
        
		//---------------------------------------------------------------------------------------------------------------------------------------
		// Create a web front-end for the REST service
		//---------------------------------------------------------------------------------------------------------------------------------------

		outputFile = new File(WEB_DESTINATION + "index.html" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(INDEX_TEMPLATE, beanDefinition, outputFile);
        
		outputFile = new File(WEB_DESTINATION + beanDefinition.getName() + ".html" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(HTML_TEMPLATE, beanDefinition, outputFile);

		outputFile = new File(WEB_DESTINATION + "js/" + beanDefinition.getName() + ".js" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(JS_TEMPLATE, beanDefinition, outputFile);

		outputFile = new File(WEB_DESTINATION + "css/" + beanDefinition.getName() + ".css" );		
		outputFile.getParentFile().mkdirs();
		populateTemplate(CSS_TEMPLATE, beanDefinition, outputFile);

		
		//---------------------------------------------------------------------------------------------------------------------------------------
		// Copy over the supporting web libraries etc
		//---------------------------------------------------------------------------------------------------------------------------------------
		
        try {
            FileUtils.copyDirectory(new File(WEBSITE_FRAMEWORK), new File(WEB_DESTINATION));
          
        } catch (IOException e) {
			logger.error(e);
        }

		//---------------------------------------------------------------------------------------------------------------------------------------
		// Completed - the new application should be in the generated directory!
		//---------------------------------------------------------------------------------------------------------------------------------------
       
        logger.info("New application based on " + beanDefinition.getName() + " has been created in " + OUTPUT_DESTINATION );
        
		
	}
	
	
    
    
    
    
    
    /**
     * 
     * 
     * 
     * @param definitionFile
     * @return
     */
	private BeanDefinition createBeanDefinition(String definitionFile) {

//		Gson gson = new Gson();
//		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		BeanDefinition beanDefinition = null;
		String json = null;

		try {
			json = readFile(DEFINITION + definitionFile);
			beanDefinition = gson.fromJson(json, BeanDefinition.class);

			// set the serial version UID if one has not been specified.
			if (beanDefinition.getSerialVersionUID() == null || beanDefinition.getSerialVersionUID().isEmpty()) {
				beanDefinition.setSerialVersionUID(new Date().getTime() + "L");
			}

			
			if (beanDefinition.getPrimaryKey() == null) {
				
				BeanAttribute pk = new BeanAttribute();
				pk.setColumnName("id");
				pk.setColumnType("BIGINT");
				pk.setIndexed(true);
				pk.setMandatory(true);
				pk.setPrimaryKey(true);
				
				beanDefinition.setPrimaryKey(pk);
			}
			
			processBeanAttribute(beanDefinition.getPrimaryKey());
			
			for (BeanAttribute attribute : beanDefinition.getAttributes()) {			
				processBeanAttribute(attribute);				
			}
						
		} catch (IOException e) {
			logger.error(e);
		}
								
		logger.debug(beanDefinition.toString());
		
		return beanDefinition;
	};


	
	
	
	
	private void processBeanAttribute (BeanAttribute attribute) {
	
		// -------------------------------------------------------------------------------
		// HACK TO GET RID OF UNSIGNED FROM MYSQL DEFINITIONS
		// -------------------------------------------------------------------------------
		if (attribute.getColumnType() != null && !attribute.getColumnType().isEmpty()) {
			attribute.setColumnType(GenerateBeanUtils.removeUnsigned(attribute.getColumnType()));
		}
		
		// -------------------------------------------------------------------------------
		// Use the Name as the Java attribute name - this is the master.
		// -------------------------------------------------------------------------------
	
		if (attribute.getName() == null || attribute.getName().trim().isEmpty()) {
			// No Java Name given - create a name from the Column Name:
			
			if (attribute.getColumnName() != null && !attribute.getColumnName().trim().isEmpty()) {
				if (StringUtils.isAllUpperCase(attribute.getColumnName())) {
					attribute.setName(attribute.getColumnName().toLowerCase());
				} else {
					attribute.setName(StringUtils.uncapitalize(attribute.getColumnName()));
				}
				
			} else {
				logger.error("An attribute has been passed without a column name or a java name - something has gone wrong!!!!");
			}
			
		} else {
			// Give the Java name sensible options
			if (StringUtils.isAllUpperCase(attribute.getName())) {
				attribute.setName(attribute.getName().toLowerCase());
			}
		}

		// -------------------------------------------------------------------------------
		// If the column name has not been set - create a name based on the Java name
		// -------------------------------------------------------------------------------

		if (attribute.getColumnName() == null || attribute.getColumnName().trim().isEmpty()) {
			// No Column Name given - create a name from the Java Name:
			attribute.setColumnName(StringUtils.capitalize(attribute.getName()));
		}

		// -------------------------------------------------------------------------------
		// If the java type has not been set - generate one from the column type 
		// -------------------------------------------------------------------------------
		
		// should check if the type is valid!
		if (attribute.getType()==null || attribute.getType().isEmpty()) {					
			attribute.setType(GenerateBeanUtils.convertColumnTypeToJavaType(attribute.getColumnType()));					
		} 
		
		// Deal with issue of time stamps.
		if (attribute.getType().toLowerCase().equals("timestamp")) {
			attribute.setType("Date");
		}
		
		// -------------------------------------------------------------------------------
		// If the column type has not been set - generate one from the java type 
		// -------------------------------------------------------------------------------
		
		// should check if the type is valid!
		if (attribute.getColumnType()==null || attribute.getColumnType().isEmpty()) {					
			attribute.setColumnType(GenerateBeanUtils.convertJavaTypeToColumnType(attribute.getType()));					
		} 
		
		// check to see if this is a varchar - need to set the max size.
		if (attribute.getColumnType().toLowerCase().equals("varchar") ) {
			if (attribute.getColumnSize() < 1) {
				if(attribute.getMaxValue() < 1) {
					attribute.setColumnSize(255);						
				} else {
					attribute.setColumnSize(attribute.getMaxValue());													
				}
			}
			
			// Set the validation max size to column size
			if (attribute.getMaxValue() < 1 ) {
				attribute.setMaxValue(attribute.getColumnSize());
			}					
		}

		// -------------------------------------------------------------------------------
		// Helpers
		// -------------------------------------------------------------------------------
		
		attribute.setObject(GenerateBeanUtils.isObject(attribute.getType()));				

		// Check to see if a regular expression has been set and if it is valid...
		if (attribute.getValidationRegExp()!= null && !attribute.getValidationRegExp().trim().isEmpty()) { 
				String result = GenerateBeanUtils.validateRegularExpression(attribute.getValidationRegExp());
				attribute.setValidationRegExp(result);
				
				if (result == null) {
					logger.warn("Error parsing supplied regular expression - ignoring"); 
				}
				attribute.setValidationRegExp(result);
		}
		
		attribute.setValidated(GenerateBeanUtils.isValidated(attribute.getValidationRegExp(), attribute.getMinValue(), attribute.getMaxValue(),attribute.isMandatory()));
		
		logger.trace(attribute);

		
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
	
	
	
	
	/**
	 * Where the magic happens
	 * 
	 * Populate the given velocity template with the project data and write a file using the given 
	 * specification - this is the main workhorse of the application, taking empty velocity templates
	 * and creating populated files.
	 * 
	 * @param templateFile A String which contains the location of the velocity template file to be populated. The file location is relative to the root of the project.
	 * @param beanDefinition A container for the data used to populate the velocity template
	 * @param outputFile A String which contains the name and location of the file to be created from the template. The file location is relative to the root of the project.  
	 */
	private void populateTemplate(String templateFile, BeanDefinition beanDefinition, File outputFile) {
		
		logger.trace("enterCreateBean");
		
		// set the serial version UID if one has not been specified.
		if (beanDefinition.getSerialVersionUID() == null || beanDefinition.getSerialVersionUID().isEmpty()) {
			beanDefinition.setSerialVersionUID(new Date().getTime() + "L");
		}
		
		VelocityContext context = createContext(beanDefinition);
		String populatedTemplate =populateVelocityTemplate (templateFile, context); 
		
		try {
			FileWriter fw = new FileWriter(outputFile);			
			fw.write(populatedTemplate);
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			logger.error(e);
		}
		
		logger.trace("exitCreateBean");

	}
	
	
	private VelocityContext createContext(BeanDefinition beanDefinition) {
		
		VelocityContext context = new VelocityContext();
		
//		context.put("date", new DateTool());		
		context.put("bean", beanDefinition);
		context.put("stringUtils", new StringUtils());
		
		List<String>fieldList = new ArrayList<String>();
		List<String>paramList = new ArrayList<String>();
		List<String>updateList = new ArrayList<String>();

		List<String>columnNameList = new ArrayList<String>();
		List<String>attributeNameList = new ArrayList<String>();
		
		for (BeanAttribute attribute : beanDefinition.getAttributes()) {
			fieldList.add(attribute.getColumnName());
			paramList.add("?");
			updateList.add( attribute.getColumnName() + "=?");
			columnNameList.add(attribute.getColumnName());
			attributeNameList.add(attribute.getName());
		}
		
		String fields = StringUtils.join(fieldList, ", ");
		String params = StringUtils.join(paramList, ", ");
		String updates = StringUtils.join(updateList, ",");
		String columnNames = StringUtils.join(columnNameList, ","); 
		String attributeNames = StringUtils.join(attributeNameList, ","); 
		

		context.put("fields", fields);
		context.put("params", params);
		context.put("updates", updates);
		context.put("columnNames", columnNames);
		context.put("attributeNames", attributeNames);
		

//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return context;				
	}

	
	/**
	 * 
	 * @param templateFile
	 * @param context
	 * @return
	 */
	private String populateVelocityTemplate(String templateFile, VelocityContext context) {
		
		logger.info("enter populateVelocityTemplate");
		String result = null;
		
		try {

			File f = new File(templateFile);
			if (f.exists()) {
				logger.info("File Exists: " + templateFile) ;
			} else {
				logger.info("File DOES NOT Exist: " + templateFile) ;
				
			}

			// set up Velocity			
			Velocity.init("velocity.properties");
		
			// Read the template		
			Template template =  null;

	        try {
	            template = Velocity.getTemplate(templateFile);
	            
	        } catch( ResourceNotFoundException rnfe ) {
	            logger.error("Error : cannot find template " + templateFile );
	            
	        } catch( ParseErrorException pee ) {
	        	logger.error("Syntax error in template " + templateFile + ":" + pee );
	        }

		
	        /*
	         *  Now have the template engine process your template using the
	         *  data placed into the context.  Think of it as a  'merge'
	         *  of the template and the data to produce the output stream.
	         */

			CharArrayWriter content = new CharArrayWriter();
	
	        BufferedWriter writer = new BufferedWriter(content);
	
	        if ( template != null) {
	            template.merge(context, writer);
	          }
	
	        //
			// flush and cleanup
	        //
	       writer.flush();
	       writer.close();

	       result = content.toString();
	       
		} catch (IOException e) {
			logger.error(e);
		}
		
		logger.info("exit populateVelocityTemplate");
		return result;
	}

		
	
}
