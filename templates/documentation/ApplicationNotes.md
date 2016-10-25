
# Cross Origin Filter (CORS) #

[Jetty Manual : Setting up Cross Origin Filters](https://www.eclipse.org/jetty/documentation/9.4.x/cross-origin-filter.html}
[Example of setting up Cross Origin Filters](http://www.programcreek.com/java-api-examples/index.php?api=org.eclipse.jetty.servlet.FilterHolder)

## Example of setting up a Jetty server with CORS filter ##

[Embedding Cross Origin Filter in Jetty](http://stackoverflow.com/questions/28190198/cross-origin-filter-with-embedded-jetty)

	Server server = new Server(httpPort);
    
	// Setup the context for servlets
	ServletContextHandler context = new ServletContextHandler();
	// Set the context for all filters and servlets
	// Required for the internal servlet & filter ServletContext to be sane
	context.setContextPath("/");
	// The servlet context is what holds the welcome list 
	// (not the ResourceHandler or DefaultServlet)
	context.setWelcomeFiles(new String[] { "index.html" });
	 
	// Add a servlet
	context.addServlet(ServerPageRoot.class,"/servlet/*");
	 
	// Add the filter, and then use the provided FilterHolder to configure it
	FilterHolder cors = context.addFilter(CrossOriginFilter.class,"/*",EnumSet.of(DispatcherType.REQUEST));
	cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
	cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
	cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD");
	cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
	 
	// Use a DefaultServlet to serve static files.
	// Alternate Holder technique, prepare then add.
	// DefaultServlet should be named 'default'
	ServletHolder def = new ServletHolder("default", DefaultServlet.class);
	def.setInitParameter("resourceBase","./http/");
	def.setInitParameter("dirAllowed","false");
	context.addServlet(def,"/");
	 
	// Create the server level handler list.
	HandlerList handlers = new HandlerList();
	// Make sure DefaultHandler is last (for error handling reasons)
	handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
	
	server.setHandler(handlers);
	server.start();
	
##Bean Validation using Hibernate Validator classes

[Getting Started with Validation Classes](http://hibernate.org/validator/documentation/getting-started/)
[Reference Guide](http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/)


##Regular Expression Validations

Test Java Regular Expressions [here](http://www.regexplanet.com/advanced/java/index.html)  
8 Common Regular Expressions detailed [here](https://code.tutsplus.com/tutorials/8-regular-expressions-you-should-know--net-6149)

Phone number with 1-10 letter word for extension and 1-6 digit extension:

	\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?
	
Simple eMail regular expression

	/^[a-z0-9_\\-\\.]{2,}@[a-z0-9_\\-\\.]{2,}\\.[a-z]{2,}$/i	
	
	private static final String EMAIL_REGEXP = "[a-zA-Z0-9_\\-\\.]{2,}@[a-zA-Z0-9_\\-\\.]{2,}\\.[a-zA-Z]{2,}";
	private static final String PHONE_REGEXP = "\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?";
	private static final String USERNAME_REGEXP = "^[a-zA-Z0-9_-]{3,16}$";
	private static final String HEXVALUE_REGEXP =  "^#?([a-f0-9]{6}|[a-f0-9]{3})$"; //example - #a3c113
	private static final String URL_REGEXP = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
	
Create string from RegularExpression [documentation](https://github.com/mifmif/Generex)	




http://mycuteblog.com/log4j2-xml-configuration-example/




http://mycuteblog.com/log4j2-xml-configuration-example/
	