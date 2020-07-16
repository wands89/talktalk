package com.study.service.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)//运行的时候的注解
@Constraint(validatedBy = MyConstraintValidator.class)//执行逻辑类
public @interface MyConstraint {
    String message() default "{hello, my validator is not pass!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
