package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class) //class that implements validation
@Target({ElementType.METHOD, ElementType.FIELD}) //applied in methods and fields
@Retention(RetentionPolicy.RUNTIME) //annotation will be executed in runtime application
public @interface UsernameConstraint {
    String message() default "Invalid username";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    //in java the symbol ? is called wildcard
}
