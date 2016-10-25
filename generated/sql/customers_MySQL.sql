CREATE TABLE customers IF NOT EXISTS (
	customerId int AUTO_INCREMENT NOT NULL,    
    forename VARCHAR(50) NOT NULL,        
    surname VARCHAR(50) NOT NULL,        
    phone VARCHAR(20),        
    addressLine1 VARCHAR(50) NOT NULL,        
    addressLine2 VARCHAR(50),        
    city VARCHAR(25) NOT NULL,        
    postCode VARCHAR(15),        
    country VARCHAR(30),        
    salesRepEmployeeId int(10),        
    creditLimit int(10),        
PRIMARY KEY(customerId),
INDEX customers_customerId_INDEX (customerId));