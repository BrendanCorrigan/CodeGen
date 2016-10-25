package generator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import generator.beans.BeanAttribute;

public class DatabaseDaoUtils {
	
		
	/**
	 * 
	 * @param columnDefinitions
	 * @return
	 */
	public static List<BeanAttribute> getBeanAttributes(List<Map<String, String>> columnDefinitions) {
		
		List<BeanAttribute>attributes = new ArrayList<BeanAttribute>();
		

		for (Map<String, String>columnDefinition : columnDefinitions) {
		
			BeanAttribute attribute = new BeanAttribute();
			
			for (String parameter : columnDefinition.keySet()) {
				
				String value = columnDefinition.get(parameter).trim();
				
				switch (parameter) {
				
					case "COLUMN_NAME":
						if (value != null && !value.isEmpty()) 
							attribute.setColumnName(value);
						break;
								
					case "IS_PRIMARY_KEY":
						if (value != null && !value.isEmpty()) 
							attribute.setPrimaryKey(Boolean.parseBoolean(value));						
						break;

					case "IS_AUTOINCREMENT":
						if (value != null && !value.isEmpty() && value.equalsIgnoreCase("yes")) 
							attribute.setAutoIncrement(true);						
						break;
						
					case "IS_NULLABLE":
						
						if (value != null && !value.isEmpty() && value.equalsIgnoreCase("no")) 
							attribute.setMandatory(true);
						else 
							attribute.setMandatory(false);
							
						break;
	
					case "TYPE_NAME":
						
						if (value != null && !value.isEmpty()) 
							attribute.setColumnType(value);
						
						break;

					case "COLUMN_SIZE":
						
						if (value != null && !value.isEmpty()) 
							attribute.setColumnSize(Integer.parseInt(value));
						
						break;
						
					case "IS_FOREIGN_KEY":
						if (value != null && !value.isEmpty()) 
							attribute.setForeignKey(Boolean.parseBoolean(value));						
						break;

					case "PKTABLE_NAME":
						if (value != null && !value.isEmpty()) 
							attribute.setFk_table(value);				
						break;
					
					case "PKCOLUMN_NAME":
						if (value != null && !value.isEmpty()) 
							attribute.setFk_key(value);				
						break;
						
					default:
						break;
				}				
			}
			
			attributes.add(attribute);
			
		}
		return attributes;		
	}
	
	
}
