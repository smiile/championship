package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.viewbeans.GroupingViewBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GroupingFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return GroupingViewBean.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.groupingForm.name");

    }

}
