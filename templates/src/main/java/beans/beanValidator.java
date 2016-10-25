package ${bean.packageName}.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *  ${bean.name} Validator
 *
 * This helper class will provide the validation for a Contact bean. This class
 * provides the validation service for individual fields and can be extended to
 * include cross field validation.
 * 
 * The source for this bean has been auto-generated.
 * 
 * @author auto-generated
 */
public class ${bean.name}Validator {

	// The validation engine
	private static Validator validator;

	/**
	 * Validate a model object against the constraints contained within the
	 * class and perform any cross field validation necessary.
	 * 
	 * @param model
	 *            The model object to be validated
	 * @return a list of ValidationMessages for any violations on this model, or
	 *         an empty list if no violations found
	 */
	public static List<ValidationMessage> validate(${bean.name} model) {
		List<ValidationMessage> results = new ArrayList<ValidationMessage>();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		Set<ConstraintViolation<${bean.name}>> constraintViolations = validator.validate(model);

		if (constraintViolations.size() != 0) {
			for (ConstraintViolation<${bean.name}> constraintViolation : constraintViolations) {
				ValidationMessage violation = new ValidationMessage(constraintViolation);
				results.add(violation);
			}
		}

		// Set up calls to cross field validations here

		return results;
	}

	/**
	 * Validate a single attribute.
	 * 
	 * @param model
	 *            The model object to be validated
	 * @param attribute
	 *            The name of the attribute to be validated
	 * @return a list of ValidationMessages for any violations on this
	 *         attribute, or an empty list if no violations found
	 */
	public static List<ValidationMessage> validate(${bean.name} model, String attribute) {
		List<ValidationMessage> results = new ArrayList<ValidationMessage>();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		Set<ConstraintViolation<${bean.name}>> constraintViolations = validator.validateProperty(model, attribute);

		if (constraintViolations.size() != 0) {
			for (ConstraintViolation<${bean.name}> constraintViolation : constraintViolations) {
				ValidationMessage violation = new ValidationMessage(constraintViolation);
				results.add(violation);
			}
		}
		return results;
	}

}
