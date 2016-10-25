package com.retail.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.retail.beans.Customer;

public class CustomerTest {

	private Customer model = null;

    @Before
    public void setUp() throws Exception {
		model = new Customer();
    }

    /**
     * Test the getters and setters on the a bean
     * @throws Exception
     */
	@Test
	public void getterAndSetterCorrectness() throws Exception {
		new BeanTester().testBean(Customer.class);
	}

	// Add validation against min, max & patterns here.

    
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testJson() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonString = mapper.writeValueAsString(model);
		assertNotNull(jsonString);
		assertEquals(jsonString.charAt(0), '{');
		assertEquals(jsonString.charAt(jsonString.length()-1), '}');
				
		Customer model = mapper.readValue(jsonString, Customer.class);
		assertNotNull(model);
		assertEquals(model.getCustomerId(), 0);
		
	}

	/**
	 * Make sure the bean has a sensible toString method.
	 */
	@Test
	public void beanHasToString() {
		assertNotNull("Expected a sensible toString result", model.toString());
		assertFalse(false);
	}
	
}
