package me.common.objs;

import jakarta.validation.*;
import java.util.Set;

public abstract class SelfValidating<T> {
    // jakarta.validation.NoProviderFoundException: Unable to create a Configuration, because no Bean Validation provider could be found. Add a provider like Hibernate Validator (RI) to your classpath.
    // fixbug: https://coderanch.com/t/732865/java/Bean-Validation-provider-Maven

  private Validator validator;

  protected SelfValidating() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Evaluates all Bean Validations on the attributes of this
   * instance.
   */
  protected void validateSelf() {
    Set<ConstraintViolation<T>> violations = validator.validate((T) this);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
