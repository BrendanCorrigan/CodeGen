package generator.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import generator.beans.BeanAttribute;
import generator.beans.BeanDefinition;
import generator.dao.ConnectionManager;
import generator.dao.DatabaseDao;
import generator.dao.DatabaseDaoUtils;

public class Main {

	/** Logger */
	private static final Logger logger = LogManager.getLogger("Main");

	/** fileName */
	private String fileName = "";

	/** Options */
	private static Options options = null;
	
	
	private String dbConnection = ConnectionManager.MYSQL;

	/**
	 *
	 */
	public Main() {
		displayHelp(options);
		processRequest();
		
	}

	/**
	 *
	 */
	public Main(CommandLine cmd) {
		processCommandLine(cmd);
		processRequest();
		
	}

	/**
	 *
	 */
	public void processRequest() {
		logger.debug("enter processRequest");
		
		logger.debug("exit processRequest");
	}

	
	/**
	 *
	 */
	public void processCommandLine(CommandLine line) {
		logger.debug("enter processCommandLine");

		if (line.hasOption("dataFile")) {
			this.fileName = line.getOptionValue("dataFile");
			logger.debug("dataFile: " + this.fileName);
		}

		if (line.hasOption("property")) {
			logger.debug("property: ");
		}

		if (line.hasOption("help")) {
			displayHelp(options);
		}

		if (line.hasOption("database")) {
			
			String db = line.getOptionValue("database");
			
			if (db.equalsIgnoreCase(ConnectionManager.MYSQL)) {
				dbConnection = ConnectionManager.MYSQL;
				
			} else if (db.equalsIgnoreCase(ConnectionManager.HSQLDB)) {
				dbConnection = ConnectionManager.HSQLDB;
			}

		}
		
		if (line.hasOption("test")) {
			
			Connection conn = ConnectionManager.getInstance().getConnection(dbConnection);
			DatabaseDao dao = new DatabaseDao(conn);
			List<String> tables = dao.getTables();
			
			logger.info("Have made connection to database - tables:");
			logger.info(tables);

//			Gson gson = new Gson();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			for (String table : tables ) {			
				List<Map<String, String>> columns = dao.getColumns(table);
								
				logger.info("Creating entry: logs/" + table + ".json");
				writeFile(new File("logs/" + table + ".json"), gson.toJson(columns));
				
			}			
		}
		
		
		if (line.hasOption("error")  || line.hasOption("trace") || line.hasOption("debug")) {
			
			LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
			Configuration config = ctx.getConfiguration();
			LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
			
			if (line.hasOption("error"))  
					loggerConfig.setLevel(Level.ERROR);

			if (line.hasOption("trace"))  
				loggerConfig.setLevel(Level.TRACE);

			if (line.hasOption("debug"))  
				loggerConfig.setLevel(Level.DEBUG);

			logger.info("Changing the root logging level: " + loggerConfig.getLevel().toString());
			
			ctx.updateLoggers(); 			
			
		}


		if (line.hasOption("table")) {
			
			String table = line.getOptionValue("table");
			if (table != null && !table.trim().isEmpty()) {
				BeanDefinition result = createDefinition (table);
				
				System.out.println(result);
			}
			
			
		}

		if (line.hasOption("def")) {
			
			String definition = line.getOptionValue("def");
			if (definition != null && !definition.trim().isEmpty()) {

				new GenerateBean(definition);
								
			}
			
			
		}

		
		logger.debug("exit processCommandLine");
	}

	/**
	 *
	 */
//	@SuppressWarnings("static-access")
	public static Options createCommandLineOptions() {

		// create Options object
		Options options = new Options();

		// Boolean options....
		
		Option help = new Option( "h", "help", false, "print this message" );
		options.addOption(help);

		Option error = new Option("error", false, "set the logging level to error");
		options.addOption(error);

		Option trace = new Option("trace", false, "set the logging level to trace");
		options.addOption(trace);

		Option debug = new Option("debug", false, "set the logging level to debug");
		options.addOption(debug);

		Option test = new Option("test", false, "test the database connection");
		options.addOption(test);		
		
		// Options which need a parameter

		Option databaseConfig = Option.builder("dbConfig").argName("file").hasArg()
				.desc("use given file for DB connections").build();
		options.addOption(databaseConfig);

		Option databaseToUse = Option.builder("db").longOpt("database").argName("databse").hasArg()
				.desc("use the specified type of database (MYSQL, HSQLDB)").build();
		
		options.addOption(databaseToUse);
		
		Option tableToUse = Option.builder("t").longOpt("table").argName("table name").hasArg()
				.desc("use the specified database table").build();
		options.addOption(tableToUse);

		Option definitionToUse = Option.builder("def").longOpt("definition").argName("defintion name").hasArg()
				.desc("use the specified definition (JSON)").build();
		options.addOption(definitionToUse);
		
		return options;

	}

	
	private BeanDefinition createDefinition (String table) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Map<String, String>> columns = null;
				
//		Connection conn = ConnectionManager.getInstance().getConnection(dbConnection);
//		DatabaseDao dao = new DatabaseDao(conn);
//
//		List<Map<String, String>> columns = dao.getColumns(table);
				
		try {
			String json = new String(Files.readAllBytes(Paths.get("logs/" + table + ".json")));
			// Deserialization
			Type collectionType = new TypeToken<List<Map<String, String>>>(){}.getType();
			columns = gson.fromJson(json, collectionType);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		List<BeanAttribute>attributes = DatabaseDaoUtils.getBeanAttributes(columns);
		BeanDefinition result = new BeanDefinition();
		
		result.setAttributes(attributes);
		result.setTable(table);
		

		writeFile(new File("temp/" + table + ".json"), gson.toJson(result));

		return result;
		
	}
	
	
	
	private void writeFile (File outputFile, String content) {
		
		try {
			FileWriter fw = new FileWriter(outputFile);			
			fw.write(content);
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	
	/**
	 *
	 */
	public static void displayHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("main", options);
	}

	
	
	/**
	 * Main with a command line parser
	 */
	public static void main(String[] args) {
		options = createCommandLineOptions();

		if (args != null && args.length > 0) {
			// create the parser
			CommandLineParser parser = new DefaultParser();
			try {
				// parse the command line arguments
				CommandLine cmd = parser.parse(options, args);
				new Main(cmd);

			} catch (ParseException exp) {
				// oops, something went wrong
				displayHelp(options);
				logger.error("Parsing command line failed.  Reason: " + exp.getMessage(), exp);
			}

		} else {
			new Main();
		}

	}

}