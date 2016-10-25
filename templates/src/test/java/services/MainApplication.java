package ${bean.packageName}.services;

import java.sql.SQLException;
import java.util.EnumSet;

import javax.naming.InitialContext;
import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import ${bean.packageName}.dao.ConnectionManager;

@SuppressWarnings("unused")
public class MainApplication {

	// The ddl for the table to create for test - will create new table if one does not already exist.
	private static final String SQLFILENAME = "sql/${bean.table}_HSQLDB.sql";

	public MainApplication(String db)  {

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
		
		Server server = new Server(8080);
		server.setHandler(context);

		// --------------------------------------------------------------------------------------------------------
		// Add a CORS filter to allow all traffic to the REST services
		// --------------------------------------------------------------------------------------------------------
		EnumSet<DispatcherType> all = EnumSet.of(DispatcherType.ASYNC, DispatcherType.ERROR, DispatcherType.FORWARD,
	            DispatcherType.INCLUDE, DispatcherType.REQUEST);
		
		// Add the filter, and then use the provided FilterHolder to configure it
		FilterHolder cors = context.addFilter(CrossOriginFilter.class, "/*", all);
		cors.setInitParameter("allowedMethods", "GET, POST, DELETE, PUT");
		
		// --------------------------------------------------------------------------------------------------------
		// Create the Jersey REST servlet container
		// --------------------------------------------------------------------------------------------------------
		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/services/*");
		
		// Tell Jersey where to pick up the resources
		jerseyServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "${bean.packageName}.services");
		
        
        //
        // Create a default servlet
        //
        
		ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
        holderPwd.setInitParameter("dirAllowed","true");
                
        holderPwd.setInitParameter("resourceBase","../${bean.project}/src/main/webapp");
        context.addServlet(holderPwd,"/");
		
        //
        // Create a connection to the database
        //
        
        if (db != null && !db.trim().isEmpty()) {
        	ConnectionManager.getInstance().useDatabase(db);
        } else {
        	ConnectionManager.getInstance().useDatabase(ConnectionManager.HSQLDB);
        	
        	// Create the database if it does not exist already.
        	ConnectionManager.getInstance().runScript(ConnectionManager.HSQLDB, SQLFILENAME);
        	
        }

		// --------------------------------------------------------------------------------------------------------
		// Start the server
		// --------------------------------------------------------------------------------------------------------
		try {
			server.start();
			server.join();
			
		} catch (Exception e) {
			// Not much to do here except print out the exception
			e.printStackTrace();
			
		} finally {
			server.destroy();
		}
		
		try {
			ConnectionManager.getInstance().shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	// Test Class - need to write a proper CLI interface for this.
	public static void main(String[] args) {			
		new MainApplication(ConnectionManager.HSQLDB);
	}

}
