package com.retail.beans;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Customer
 *
 * This object represents data held in table customers
 * This is intended to be used together with associated CustomerDAO class.
 * This object is unit tested by CustomerTest class.
 *
 * This object uses the Jackson library to marshall / unmarshall the attributes
 * as JSON. This bean has been configured to ignore any JSON attributes it does
 * not recognise - this can be changed by altering the JsonIgnoreProperties annotation.
 *
 * This object assumes that there is a unique numeric (long) id for each object.
 * 
 * The source for this bean has been auto-generated.
 *  
 * @author auto-generated
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {
	
    /**
	 * Generated serialVersionUID 
	 */
	private static final long serialVersionUID = 1477353936111L;
		
	// Primary Key - customerId
    @JsonProperty("customerId")
	private int customerId;
	
	// forename
	@NotNull
	@NotBlank	
	@Length(min=0,max=50)
    @JsonProperty("forename")
	private String forename;
 
	// surname
	@NotNull
	@NotBlank	
	@Length(min=0,max=50)
    @JsonProperty("surname")
	private String surname;
 
	// phone
	@Length(min=0,max=20)
    @JsonProperty("phone")
	private String phone;
 
	// addressLine1
	@NotNull
	@NotBlank	
	@Length(min=0,max=50)
    @JsonProperty("addressLine1")
	private String addressLine1;
 
	// addressLine2
	@Length(min=0,max=50)
    @JsonProperty("addressLine2")
	private String addressLine2;
 
	// city
	@NotNull
	@NotBlank	
	@Length(min=0,max=25)
    @JsonProperty("city")
	private String city;
 
	// postCode
	@Length(min=0,max=15)
    @JsonProperty("postCode")
	private String postCode;
 
	// country
	@Length(min=0,max=30)
    @JsonProperty("country")
	private String country;
 
	// salesRepEmployeeId
    @JsonProperty("salesRepEmployeeId")
	private int salesRepEmployeeId;
 
	// creditLimit
    @JsonProperty("creditLimit")
	private int creditLimit;
 
	
	// Default constructor
	public Customer() {		
	};
	
	// Constructor taking an identifier
    public Customer (int customerId) {
		this.customerId = customerId;
    }

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
		

	/**
	 * @return the forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * @param forename the forename to set
	 */
	public void setForename( String  forename) {
		this.forename = forename;
	}
    
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname( String  surname) {
		this.surname = surname;
	}
    
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone( String  phone) {
		this.phone = phone;
	}
    
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1( String  addressLine1) {
		this.addressLine1 = addressLine1;
	}
    
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2( String  addressLine2) {
		this.addressLine2 = addressLine2;
	}
    
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity( String  city) {
		this.city = city;
	}
    
	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode( String  postCode) {
		this.postCode = postCode;
	}
    
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry( String  country) {
		this.country = country;
	}
    
	/**
	 * @return the salesRepEmployeeId
	 */
	public int getSalesRepEmployeeId() {
		return salesRepEmployeeId;
	}

	/**
	 * @param salesRepEmployeeId the salesRepEmployeeId to set
	 */
	public void setSalesRepEmployeeId( int  salesRepEmployeeId) {
		this.salesRepEmployeeId = salesRepEmployeeId;
	}
    
	/**
	 * @return the creditLimit
	 */
	public int getCreditLimit() {
		return creditLimit;
	}

	/**
	 * @param creditLimit the creditLimit to set
	 */
	public void setCreditLimit( int  creditLimit) {
		this.creditLimit = creditLimit;
	}
    

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeanDefinition [");
		builder.append("customerId=").append(customerId);
        if (forename != null)
			builder.append(", ").append("forename=").append(forename);
        if (surname != null)
			builder.append(", ").append("surname=").append(surname);
        if (phone != null)
			builder.append(", ").append("phone=").append(phone);
        if (addressLine1 != null)
			builder.append(", ").append("addressLine1=").append(addressLine1);
        if (addressLine2 != null)
			builder.append(", ").append("addressLine2=").append(addressLine2);
        if (city != null)
			builder.append(", ").append("city=").append(city);
        if (postCode != null)
			builder.append(", ").append("postCode=").append(postCode);
        if (country != null)
			builder.append(", ").append("country=").append(country);
		builder.append(", ").append("salesRepEmployeeId=").append(salesRepEmployeeId);
		builder.append(", ").append("creditLimit=").append(creditLimit);
		builder.append("]");
		return builder.toString();
	}
	
}
