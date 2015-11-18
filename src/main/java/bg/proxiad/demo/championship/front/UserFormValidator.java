package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.User;
import bg.proxiad.demo.championship.viewbeans.UserViewBean;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    @Qualifier("emailValidator")
    EmailValidator emailValidator;
    
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserViewBean.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserViewBean userViewBean = (UserViewBean) target;

        if ("create".equals(userViewBean.getScenario())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
            

            if (!emailValidator.valid(userViewBean.getEmail())) {
                errors.rejectValue("email", "Pattern.userForm.email");
            }
            
            if (!userViewBean.getEmail().isEmpty()) {
                User user = userService.loadUserByEmail(userViewBean.getEmail());
                boolean isRegistered = !Objects.equals(user, null);
                
                if(isRegistered) {
                    errors.rejectValue("email", "Incorrect.userForm.emailRegistered");
                }
            }
        }

        if ("update".equals(userViewBean.getScenario())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
            
            if (!emailValidator.valid(userViewBean.getEmail())) {
                errors.rejectValue("email", "Pattern.userForm.email");
            }
            
            if (!userViewBean.getEmail().isEmpty()) {
                User user = userService.loadUserByEmail(userViewBean.getEmail());
                boolean isRegistered = !Objects.equals(user, null);
                
                // In update: don't perform uniqueness check towards current entity
                if(isRegistered && !Objects.equals(user.getId(), userViewBean.getId())) {
                    errors.rejectValue("email", "Incorrect.userForm.emailRegistered");
                }
            }
        }

        if ("changePassword".equals(userViewBean.getScenario())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "NotEmpty.userForm.password");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotEmpty.userForm.password");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword", "NotEmpty.userForm.password");
            
            if(!Objects.equals(userViewBean.getNewPassword(), userViewBean.getRepeatPassword())) {
                errors.rejectValue("repeatPassword", "NotMatching.userForm.passwords");
            }
            
            User user = userService.loadUser(userViewBean.getId());
            
            if(!Objects.equals(user.getPassword(), userViewBean.getOldPassword())) {
                errors.rejectValue("oldPassword", "Incorrect.userForm.oldPassword");
            }
        }

    }

}
