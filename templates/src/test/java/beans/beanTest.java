#set ($instanceName = $stringUtils.uncapitalize(${bean.name}))
package ${bean.packageName}.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import ${bean.packageName}.beans.${bean.name};

public class ${bean.name}Test {

	private ${bean.name} model = null;

    @Before
    public void setUp() throws Exception {
		model = new ${bean.name}();
    }

    /**
     * Test the getters and setters on the a bean
     * @throws Exception
     */
	@Test
	public void getterAndSetterCorrectness() throws Exception {
		new BeanTester().testBean(${bean.name}.class);
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
				
		${bean.name} model = mapper.readValue(jsonString, ${bean.name}.class);
		assertNotNull(model);
		assertEquals(model.get$stringUtils.capitalize(${bean.primaryKey.name})(), 0);
		
	}

	/**
	 * Make sure the bean has a sensible toString method.
	 */
	@Test
	public void beanHasToString() {
		assertNotNull("Expected a sensible toString result", model.toString());
		assertFalse($instanceName.toString().isEmpty());
	}
	
}
