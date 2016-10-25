#set ($instanceName = $stringUtils.uncapitalize(${bean.name}))
package ${bean.packageName}.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

import org.junit.Before;
import org.junit.Test;

import ${bean.packageName}.beans.${bean.name};
import ${bean.packageName}.beans.${bean.name}Validator;

@SuppressWarnings("unused")
public class ${bean.name}ValidatorTest {
	
    @Before
    public void setUp() throws Exception {
    }
    
#foreach($attribute in $bean.attributes)
#if($attribute.validated)

    @Test
    public void test$stringUtils.capitalize(${attribute.name})() {

		${bean.name} model = new ${bean.name}();		
		List<ValidationMessage> results = null;
	
#if($attribute.mandatory)
		// Check to make sure that the mandatory check for this field is working
		model.set$stringUtils.capitalize(${attribute.name})(null);
    	results =${bean.name}Validator.validate(model, "${attribute.name}");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);

		// Check to make sure that the not empty validator is working
		model.set$stringUtils.capitalize(${attribute.name})("");
    	results =${bean.name}Validator.validate(model, "${attribute.name}");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
    	// may not be empty
    	
#end
#if($attribute.type == "String" && $attribute.columnSize > 0)
		// Check to make sure that the length constraint is okay
		model.set$stringUtils.capitalize(${attribute.name})(StringUtils.repeat("X", (${attribute.columnSize} + 1)));
    	results =${bean.name}Validator.validate(model, "${attribute.name}");
    	assertNotNull(results);
    	assertTrue(results.size() > 0);
    	//System.out.println("Results: " + results);
		    	
#end
#if($attribute.validationRegExp)
		// If the field has a regular expression check - create a valid test case here

		//Generate random String
		//	Generex generex = new Generex("$attribute.validationRegExp");
		// String randomStr = generex.random();
		
		//model.set$stringUtils.capitalize(${attribute.name})(randomStr);
		//results =${bean.name}Validator.validate(model, "${attribute.name}");
		
		//assertNotNull(results);
		//assertTrue(results.size() == 0);
		//System.out.println("Results: " + results);
	
#end

    }

	
#end
#end
    
    
	
}