#Notes on presentation layer

Single page application which makes extensive use of Javascript libraries. 


#Page Layout
Bootstrap

#JavaScript Library
JQuery
AJAX calls to a REST service

#Table
JQuery Boot-grid used to display the table in a CRUD friendly way, documentation for the library available [here.](http://www.jquery-bootgrid.com/Documentation)

#Time and Date related function
MomentJS used to provide time and date functionality, documentation [here](http://momentjs.com/docs/)

Format Dates:

	moment().format();                        // 2016-09-29T21:38:36+01:00
	moment().format('dd/MM/yyyy');            // 19/09/2016  

Relative Time:
  
	moment("20120620", "YYYYMMDD").fromNow(); // 4 years ago  
	moment().startOf('day').fromNow();        // a day ago  
	moment().endOf('day').fromNow();          // in 2 hours  
	moment().startOf('hour').fromNow();       // 39 minutes ago  

Calendar Time:

	moment().subtract(1, 'days').calendar();  // Yesterday at 9:38 PM
	moment().calendar();                      // Today at 9:38 PM
	moment().add(1, 'days').calendar();       // Tomorrow at 9:38 PM


