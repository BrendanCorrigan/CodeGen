CREATE TABLE IF NOT EXISTS customers (
	customerId int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
    forename VARCHAR(50) NOT NULL,        
    surname VARCHAR(50) NOT NULL,        
    phone VARCHAR(20),        
    addressLine1 VARCHAR(50) NOT NULL,        
    addressLine2 VARCHAR(50),        
    city VARCHAR(25) NOT NULL,        
    postCode VARCHAR(15),        
    country VARCHAR(30),        
    salesRepEmployeeId int,        
    creditLimit int,        
PRIMARY KEY(customerId));
CREATE INDEX IF NOT EXISTS customers_customerId_INDEX ON customers(customerId);