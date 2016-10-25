package ${bean.packageName}.beans;

import java.io.Serializable;
#foreach($attribute in $bean.attributes)
#if (${attribute.type} == "Date" || ${attribute.type} == "Timestamp")
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
#break
#end
#end
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

#foreach($attribute in $bean.attributes)
#if ($attribute.validated)
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
#break
#end
#end

/**
 * ${bean.name}
 *
 * This object represents data held in table ${bean.table}
 * This is intended to be used together with associated ${bean.name}DAO class.
 * This object is unit tested by ${bean.name}Test class.
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
public class ${bean.name} implements Serializable {
	
    /**
	 * Generated serialVersionUID 
	 */
	private static final long serialVersionUID = ${bean.serialVersionUID};
		
	// Primary Key - ${bean.primaryKey.name}
    @JsonProperty("${bean.primaryKey.name}")
	private ${bean.primaryKey.type} ${bean.primaryKey.name};
	
#foreach($attribute in $bean.attributes)
	// ${attribute.name}
#if ($attribute.columnType.toString().toLowerCase() == "timestamp")
	// Timestamp stored as java Date object and represented as an ISO8601 string in JSON 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.sssZ")
#elseif ($attribute.columnType.toString().toLowerCase() == "date")
	// Date represented as dd/MM/yyyy string in JSON
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
#end
#if($attribute.validated)
#if(${attribute.mandatory})
	@NotNull
	@NotBlank	
#end
#if($attribute.type == "String")
	@Length(min=0,max=$attribute.columnSize)
#end
#if($attribute.validationRegExp)
	//@Pattern(regexp ="$attribute.validationRegExp", message="Does not match a recognised pattern")
#end
#end
    @JsonProperty("${attribute.name}")
	private ${attribute.type} ${attribute.name};
 
#end
	
	// Default constructor
	public ${bean.name}() {		
	};
	
	// Constructor taking an identifier
    public ${bean.name} (${bean.primaryKey.type} ${bean.primaryKey.name}) {
		this.${bean.primaryKey.name} = ${bean.primaryKey.name};
    }

	/**
	 * @return the ${bean.primaryKey.name}
	 */
	public ${bean.primaryKey.type} get$stringUtils.capitalize(${bean.primaryKey.name})() {
		return ${bean.primaryKey.name};
	}

	/**
	 * @param ${bean.primaryKey.name} the ${bean.primaryKey.name} to set
	 */
	public void set$stringUtils.capitalize(${bean.primaryKey.name})(${bean.primaryKey.type} ${bean.primaryKey.name}) {
		this.${bean.primaryKey.name} = ${bean.primaryKey.name};
	}
		
#foreach($attribute in $bean.attributes)

	/**
	 * @return the ${attribute.name}
	 */
	public #if(${attribute.type} == "Timestamp")Date#else${attribute.type}#end get$stringUtils.capitalize(${attribute.name})() {
		return ${attribute.name};
	}

	/**
	 * @param ${attribute.name} the ${attribute.name} to set
	 */
	public void set$stringUtils.capitalize(${attribute.name})(#if (${attribute.type} == "Timestamp")Date #else ${attribute.type} #end ${attribute.name}) {
		this.${attribute.name} = ${attribute.name};
	}
    #if (${attribute.type}=="boolean" || ${attribute.type}=="Boolean")
     
	/**
	 * @return the ${attribute.name}
	 */
	public boolean is$stringUtils.capitalize(${attribute.name})() {
		return ${attribute.name};
	}
    #end
#end


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeanDefinition [");
		builder.append("${bean.primaryKey.name}=").append(${bean.primaryKey.name});
#foreach($attribute in $bean.attributes)
#if (${attribute.type}=="String" || ${attribute.type}=="Date" || ${attribute.type}=="Timestamp" )
        if (${attribute.name} != null)
			builder.append(", ").append("${attribute.name}=").append(${attribute.name});
#else
		builder.append(", ").append("${attribute.name}=").append(${attribute.name});
#end
#end                
		builder.append("]");
		return builder.toString();
	}
	
}
