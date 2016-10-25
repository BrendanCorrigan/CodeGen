package cli;

import org.apache.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

	/** Logger */
	private static Logger logger = Logger.getLogger(Main.class);

	/** fileName */
	private String fileName = "";
	
	/** Options */
	private static Options options = null;
		
	/**
	 *
	 */	
	public Main () {
		processRequest();
	}
	
	
	/**
	 *
	 */
	public Main (CommandLine line) {
		processCommandLine(line);
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
		
		if( line.hasOption( "dataFile" ) ) {
    		this.fileName = line.getOptionValue( "dataFile" );
    		logger.debug("dataFile: " + this.fileName );
		}

		if( line.hasOption( "property" ) ) {
    		logger.debug("property: " );
		}
		
		if( line.hasOption( "help" ) ) {
			displayHelp(options);
		}
		
		logger.debug("exit processCommandLine");
	}
	
	
	/**
	 *
	 */
	@SuppressWarnings("static-access")
	public static Options createCommandLineOptions() {
		
		Option help = new Option( "h", "help", false, "print this message" );
		Option dataFile = OptionBuilder.withArgName( "file" ).hasArg().withDescription(  "use this data file" ).create( "dataFile");		
		Option property  = OptionBuilder.withArgName( "property=value" ).hasArgs(2).withValueSeparator().withDescription( "use value for given property" ).create( "D" );		
		Options options = new Options();

		options.addOption( help );
		options.addOption( dataFile );		
		options.addOption( property );		
		
		return options;
		
	}
	

	/**
	 *
	 */
	public static void displayHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "main", options );		
	}

	
	/**
	 * Main with a command line parser
	 */
    public static void main(String[] args) {
    	
    	if (args != null && args.length > 0) {
		    // create the parser
		    CommandLineParser parser = new GnuParser();
		    options = createCommandLineOptions();
		    try {
		        // parse the command line arguments
		        CommandLine line = parser.parse( options, args );
		        new Main(line);
		        
		    } catch( ParseException exp ) {
		        // oops, something went wrong
		        displayHelp(options);
		        System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
		    }    	
    	
    	} else {
			new Main();
    	}
    	
    }	
	
	
}