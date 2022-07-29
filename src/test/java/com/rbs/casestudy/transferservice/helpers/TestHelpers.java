package com.rbs.casestudy.transferservice.helpers;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestHelpers<T> {

    public TestHelpers() {
    }

    public List<ValidationError> getValidationMessages(T model) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator.validate(model, new Class[0]).stream().map(validation ->
                new ValidationError(validation.getPropertyPath().toString(), validation.getMessage())
        ).collect(Collectors.toList());
    }
}
