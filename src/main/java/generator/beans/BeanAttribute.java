package generator.beans;

public class BeanAttribute {
	
	private String name;
	private String type;
	private String columnName;
	private String columnType;
	private int columnSize;
	private int minValue = -1;
	private int maxValue = -1;
	private String validationRegExp;
	private boolean primaryKey = false;
	private boolean foreignKey = false;
	private boolean indexed = false;
	private boolean searchable = false;
	private boolean mandatory = false;
	private boolean autoIncrement = false;

	private boolean visible= true;

	private String fk_table;
	private String fk_key;
	
	private boolean validated = false;
	private boolean object = false;

//  <dbColumn PK="false" colType="String" colName="firstname" varName="firstname" 
//  automatic="false" valueSource="" indexed="false" colLength="255" orderLevel="0" orderType="ASC" />	
	
	public BeanAttribute() {
		
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}
	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	/**
	 * @return the validationRegExp
	 */
	public String getValidationRegExp() {
		return validationRegExp;
	}
	/**
	 * @param validationRegExp the validationRegExp to set
	 */
	public void setValidationRegExp(String validationRegExp) {
		this.validationRegExp = validationRegExp;
	}
	/**
	 * @return the primaryKey
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	/**
	 * @return the foreignKey
	 */
	public boolean isForeignKey() {
		return foreignKey;
	}
	/**
	 * @param foreignKey the foreignKey to set
	 */
	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}
	/**
	 * @return the indexed
	 */
	public boolean isIndexed() {
		return indexed;
	}
	/**
	 * @param indexed the indexed to set
	 */
	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}
	/**
	 * @return the searchable
	 */
	public boolean isSearchable() {
		return searchable;
	}
	/**
	 * @param searchable the searchable to set
	 */
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	/**
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}
	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	


	/**
	 * @return the minValue
	 */
	public int getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public int getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the isValidated
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * @param isValidated the isValidated to set
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	/**
	 * @return the isObject
	 */
	public boolean isObject() {
		return object;
	}

	/**
	 * @param isObject the isObject to set
	 */
	public void setObject(boolean object) {
		this.object = object;
	}

	/**
	 * @return the columnSize
	 */
	public int getColumnSize() {
		return columnSize;
	}

	/**
	 * @param columnSize the columnSize to set
	 */
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	

	public String getFk_table() {
		return fk_table;
	}

	public void setFk_table(String fk_table) {
		this.fk_table = fk_table;
	}

	public String getFk_key() {
		return fk_key;
	}

	public void setFk_key(String fk_key) {
		this.fk_key = fk_key;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	
	
	/**
	 * @return the autoIncrement
	 */
	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	/**
	 * @param autoIncrement the autoIncrement to set
	 */
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeanAttribute [");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (type != null)
			builder.append("type=").append(type).append(", ");
		if (columnName != null)
			builder.append("columnName=").append(columnName).append(", ");
		if (columnType != null)
			builder.append("columnType=").append(columnType).append(", ");
		builder.append("columnSize=").append(columnSize).append(", minValue=").append(minValue).append(", maxValue=")
				.append(maxValue).append(", ");
		if (validationRegExp != null)
			builder.append("validationRegExp=").append(validationRegExp).append(", ");
		builder.append("primaryKey=").append(primaryKey).append(", foreignKey=").append(foreignKey).append(", indexed=")
				.append(indexed).append(", searchable=").append(searchable).append(", mandatory=").append(mandatory)
				.append(", autoIncrement=").append(autoIncrement).append(", visible=").append(visible).append(", ");
		if (fk_table != null)
			builder.append("fk_table=").append(fk_table).append(", ");
		if (fk_key != null)
			builder.append("fk_key=").append(fk_key).append(", ");
		builder.append("validated=").append(validated).append(", object=").append(object).append("]");
		return builder.toString();
	}




	
	
}
