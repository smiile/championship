package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.viewbeans.MatchViewBean;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MatchFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MatchViewBean.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MatchViewBean matchViewBean = (MatchViewBean) target;
        
        Long p1GamesWon = matchViewBean.getP1GamesWon();
        Long p2GamesWon = matchViewBean.getP2GamesWon();
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "p1GamesWon", "NotEmpty.matchForm.p1GamesWon");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "p2GamesWon", "NotEmpty.matchForm.p2GamesWon");

        if(!Objects.equals(p1GamesWon, null) && (p1GamesWon < 0 || p1GamesWon > 2)) {
            errors.rejectValue("p1GamesWon", "Invalid.matchForm.p1GamesWon");
        }
        
        if(!Objects.equals(p2GamesWon, null) && (p2GamesWon < 0 || p2GamesWon > 2)) {
            errors.rejectValue("p2GamesWon", "Invalid.matchForm.p2GamesWon");
        }
        
    }

}
