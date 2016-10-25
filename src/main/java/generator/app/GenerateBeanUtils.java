package generator.app;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenerateBeanUtils {
	
	
	 private static final Logger logger = LogManager.getLogger("GenerateBeanUtils");

	
//	private static final String EMAIL_REGEXP = "(?i)[a-z0-9_\\-\\.]{2,}@[a-z0-9_\\-\\.]{2,}\\.[a-z]{2,}";	
	private static final String EMAIL_REGEXP = "[a-zA-Z0-9_\\-\\.]{2,}@[a-zA-Z0-9_\\-\\.]{2,}\\.[a-zA-Z]{2,}";
	private static final String PHONE_REGEXP = "\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?";
	private static final String USERNAME_REGEXP = "^[a-zA-Z0-9_-]{3,16}$";
	private static final String HEXVALUE_REGEXP =  "^#?([a-f0-9]{6}|[a-f0-9]{3})$"; //example - #a3c113
	private static final String WEBSITE_REGEXP = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
//	private static final String STAFF_NUMBER_REGEXP = "/^[a-zA-Z0-9_-]{3,16}$/";
//	private static final String NATIONAL_INSURANCE_NO_REGEXP = "/^[a-zA-Z0-9_-]{3,16}$/";
	
	
	public static String removeUnsigned(String columnType) {
		String result = columnType;

		if (columnType != null && !columnType.trim().isEmpty()) {
			if (columnType.toLowerCase().contains("unsigned")) {
				String[] results = columnType.toLowerCase().split("unsigned");
				if (results != null && results.length > 0) { 
					result = results[0];
				}
			}			
		}
		return result.trim();
	}
	
	public static String convertColumnTypeToJavaType(String columnType) {

		String result = null;

		if (columnType != null && !columnType.trim().isEmpty()) {

			switch (columnType.toLowerCase()) {
			case "date":
			case "timestamp":
				result = "Date";
				break;

			case "bit":
			case "binary":
			case "boolean":
				result = "boolean";
				break;

			case "tinyint":
			case "byte":
				result = "byte";
				break;

			case "short":
			case "smallint":
				result = "short";
				break;

			case "int":
			case "int unsigned":
			case "integer":
				result = "int";
				break;

			case "long":
			case "bigint":
				result = "long";
				break;

			case "float":
			case "decimal":
			case "real":
			case "double":
				result = "double";
				break;

			case "varchar":
			case "longvarchar":
			case "clob":
			case "char":
				result = "String";
				break;

			default:
				result = "String";
				break;
			}
		}

		return result;

	}

	public static boolean isValidated(String regExp, int min, int max, boolean mandatory) {
		boolean result = false;

		if ((regExp != null && !regExp.trim().isEmpty()) || min > 0 || max > 0 || mandatory)
			result = true;

		return result;
	}

	public static boolean isObject(String type) {
		boolean result = false;
		if (type.equals("Date") || type.equals("String"))
			result = true;

		return result;
	}

	public static String convertJavaTypeToColumnType(String javaType) {

		String result = null;

		if (javaType != null && !javaType.trim().isEmpty()) {

			switch (javaType.toLowerCase()) {
			case "date":
			case "timestamp":
				result = "timestamp";
				break;

			case "boolean":
				result = "bit";
				break;

			case "byte":
				result = "tinyint";
				break;

			case "short":
				result = "smallint";
				break;

			case "int":
			case "integer":
				result = "integer";
				break;

			case "long":
				result = "bigint";
				break;

			case "float":
				result = "float";
				break;

			case "double":
				result = "double";
				break;

			default:
				result = "varchar";
				break;
			}
		}
		return result;
	}

	public static String validateRegularExpression(String testString) {
		
		String result = null;		
		String regularExpression = null;;

		switch (testString.trim().toLowerCase()) {
		case "email":
			regularExpression = EMAIL_REGEXP;
			break;
			
		case "phone":
			regularExpression = PHONE_REGEXP;
			break;
			
		case "website":
			regularExpression = WEBSITE_REGEXP;
			break;

		case "hex":
			regularExpression = HEXVALUE_REGEXP;
			break;

		case "username":
			regularExpression = USERNAME_REGEXP;
			break;

		default:
			regularExpression = testString.trim(); 
		}
		
		try {
			logger.debug("Using REGEXP: " + regularExpression);
			
     //       result = regularExpression.replaceAll("\\", "\\\\");
			
            Pattern.compile(regularExpression);
            
            // Replace all single slash with a double slash
    //        result = regularExpression.replaceAll("\\", "\\\\");
            
           result = regularExpression; 
            		
        } catch (PatternSyntaxException exception) {
        	logger.error(exception.getDescription());
        }
				
		return result;
		
	}
	
	
	public static void main(String[] args) {
		System.out.println("'" + removeUnsigned("int unsigned") + "'");
		System.out.println("'" + removeUnsigned("long UNsigned") + "'");
		System.out.println("'" + removeUnsigned("unsigned") + "'");
		System.out.println("'" + removeUnsigned("int") + "'");
		System.out.println("'" + removeUnsigned("LONG") + "'");
	}
	
}
