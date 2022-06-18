package pl.simple.finance.apiserver.model.expense;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinDateValidator implements ConstraintValidator<MinDate, LocalDate> {

    private LocalDate minDate;

    @Override
    public void initialize(MinDate constraint) {
        int[] dateElements = constraint.date();
        minDate = LocalDate.of(dateElements[0], dateElements[1], dateElements[2]);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value != null && value.isAfter(minDate);
    }
}
