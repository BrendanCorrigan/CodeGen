package com.retail.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

import org.junit.Before;
import org.junit.Test;

import com.retail.beans.Customer;
import com.retail.beans.CustomerValidator;

@SuppressWarnings("unused")
public class CustomerValidatorTest {
	
    @Before
    public void setUp() throws Exception {
    }
    

    @Test
    public void testForename() {

		Customer model = new Customer();		
		List<ValidationMessage> results = null;
	
		// Check to make sure that the mandatory check for this field is working
		model.setForename(null);
    	results =CustomerValidator.validate(model, "forename");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);

		// Check to make sure that the not empty validator is working
		model.setForename("");
    	results =CustomerValidator.validate(model, "forename");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
    	// may not be empty
    	
		// Check to make sure that the length constraint is okay
		model.setForename(StringUtils.repeat("X", (50 + 1)));
    	results =CustomerValidator.validate(model, "forename");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	

    }

	

    @Test
    public void testSurname() {

		Customer model = new Customer();		
		List<ValidationMessage> results = null;
	
		// Check to make sure that the mandatory check for this field is working
		model.setSurname(null);
    	results =CustomerValidator.validate(model, "surname");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);

		// Check to make sure that the not empty validator is working
		model.setSurname("");
    	results =CustomerValidator.validate(model, "surname");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
    	// may not be empty
    	
		// Check to make sure that the length constraint is okay
		model.setSurname(StringUtils.repeat("X", (50 + 1)));
    	results =CustomerValidator.validate(model, "surname");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	

    }

	

    @Test
    public void testPhone() {

		Customer model = new Customer();		
		List<ValidationMessage> results = null;
	
		// Check to make sure that the length constraint is okay
		model.setPhone(StringUtils.repeat("X", (20 + 1)));
    	results =CustomerValidator.validate(model, "phone");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	

    }

	

    @Test
    public void testAddressLine1() {

		Customer model = new Customer();		
		List<ValidationMessage> results = null;
	
		// Check to make sure that the mandatory check for this field is working
		model.setAddressLine1(null);
    	results =CustomerValidator.validate(model, "addressLine1");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);

		// Check to make sure that the not empty validator is working
		model.setAddressLine1("");
    	results =CustomerValidator.validate(model, "addressLine1");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
    	// may not be empty
    	
		// Check to make sure that the length constraint is okay
		model.setAddressLine1(StringUtils.repeat("X", (50 + 1)));
    	results =CustomerValidator.validate(model, "addressLine1");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	

    }

	

    @Test
    public void testAddressLine2() {

		Customer model = new Customer();		
		List<ValidationMessage> results = null;
	
		// Check to make sure that the length constraint is okay
		model.setAddressLine2(StringUtils.repeat("X", (50 + 1)));
    	results =CustomerValidator.validate(model, "addressLine2");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	

    }

	

    @Test
    public void testCity() {

		Customer model = new Customer();		
		List<ValidationMessage> results = null;
	
		// Check to make sure that the mandatory check for this field is working
		model.setCity(null);
    	results =CustomerValidator.validate(model, "city");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);

		// Check to make sure that the not empty validator is working
		model.setCity("");
    	results =CustomerValidator.validate(model, "city");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
    	// may not be empty
    	
		// Check to make sure that the length constraint is okay
		model.setCity(StringUtils.repeat("X", (25 + 1)));
    	results =CustomerValidator.validate(model, "city");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	

    }

	

    @Test
    public void testPostCode() {

		Customer model = new Customer();		
		List<ValidationMessage> results = null;
	
		// Check to make sure that the length constraint is okay
		model.setPostCode(StringUtils.repeat("X", (15 + 1)));
    	results =CustomerValidator.validate(model, "postCode");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	

    }

	

    @Test
    public void testCountry() {

		Customer model = new Customer();		
		List<ValidationMessage> results = null;
	
		// Check to make sure that the length constraint is okay
		model.setCountry(StringUtils.repeat("X", (30 + 1)));
    	results =CustomerValidator.validate(model, "country");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	

    }

	
    
    
	
}