package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.viewbeans.ParticipantViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ParticipantFormValidator implements Validator {
    
    EmailValidator emailValidator;
    
    @Autowired
    public ParticipantFormValidator(EmailValidator emailValidator) {
        this.emailValidator = emailValidator;
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return ParticipantViewBean.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ParticipantViewBean participantViewBean = (ParticipantViewBean) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.participantForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.participantForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.participantForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "photoFileName", "NotEmpty.participantForm.photoFileName");

        if (!emailValidator.valid(participantViewBean.getEmail())) {
            errors.rejectValue("email", "Pattern.participantForm.email");
        }
    }

}
