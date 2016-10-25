package generator.beans;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {

	private String project;
	private String name;
	private String table;	
	private String packageName;
	private String description;
	private String serialVersionUID;
	
	private BeanAttribute primaryKey;
	private List<BeanAttribute>attributes = new ArrayList<BeanAttribute>();

	
	public BeanDefinition () {
		
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the serialVersionUID
	 */
	public String getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @param serialVersionUID the serialVersionUID to set
	 */
	public void setSerialVersionUID(String serialVersionUID) {
		this.serialVersionUID = serialVersionUID;
	}

	/**
	 * @return the attributes
	 */
	public List<BeanAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<BeanAttribute> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * @param attribute the attribute to add
	 */
	public void addAttribute(BeanAttribute attribute) {
		this.attributes.add(attribute);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
		
	/**
	 * @return the primaryKey
	 */
	public BeanAttribute getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(BeanAttribute primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeanDefinition [");
		if (project != null)
			builder.append("project=").append(project).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (table != null)
			builder.append("table=").append(table).append(", ");
		if (packageName != null)
			builder.append("packageName=").append(packageName).append(", ");
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (serialVersionUID != null)
			builder.append("serialVersionUID=").append(serialVersionUID).append(", ");
		if (primaryKey != null)
			builder.append("primaryKey=").append(primaryKey).append(", ");
		if (attributes != null)
			builder.append("attributes=").append(attributes);
		builder.append("]");
		return builder.toString();
	}
	

	
	
}
