package ${bean.packageName}.beans;

import javax.validation.ConstraintViolation;

/**
 * 
 * @author auto-generated
 *
 */
public class ValidationMessage {

	// The name of the model which has thrown the validation message
	private String modelName;
	
	// The name of the attribute which has thrown the validation message
	private String attributeName;

	// The value which was rejected
	private String valueRejected;
	
	// The vaildation message
	private String message;
	
	/**
	 * Generate a new validation message 
	 * 
	 * @param modelName
	 * @param attributeName
	 * @param valueRejected
	 * @param message
	 */
	public ValidationMessage(String modelName, String attributeName, String valueRejected, String message) {
		this.modelName = modelName;
		this.attributeName = attributeName;
		this.valueRejected = valueRejected;
		this.message = message;
	}

	/**
	 * Generate a new validation message from the constraint violation
	 * 
	 * @param constraintViolation
	 */
	public ValidationMessage (ConstraintViolation<?> constraintViolation) {

		if (constraintViolation.getRootBean() != null) 
			this.modelName = constraintViolation.getRootBean().getClass().getName();
		else
			this.modelName = "Undeclared Bean";

		if (constraintViolation.getPropertyPath() != null) 
			this.attributeName = constraintViolation.getPropertyPath().toString();
		else
			this.attributeName = "Unknown Property";			

		if (constraintViolation.getInvalidValue() != null) 
			this.valueRejected = constraintViolation.getInvalidValue().toString();
		else 
			this.valueRejected = "null";
			
		this.message = constraintViolation.getMessage();
		
	}
	
	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @return the valueRejected
	 */
	public String getValueRejected() {
		return valueRejected;
	}

	/**
	 * @param valueRejected the valueRejected to set
	 */
	public void setValueRejected(String valueRejected) {
		this.valueRejected = valueRejected;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValidationMessages [");
		if (modelName != null)
			builder.append("modelName=").append(modelName).append(", ");
		if (attributeName != null)
			builder.append("attributeName=").append(attributeName).append(", ");
		if (valueRejected != null)
			builder.append("valueRejected=").append(valueRejected).append(", ");
		if (message != null)
			builder.append("message=").append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
