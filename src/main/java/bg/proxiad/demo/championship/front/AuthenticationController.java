package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.User;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginView(ModelMap model) {

        return "auth/login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginAction(ModelMap model, HttpServletRequest request, final RedirectAttributes redirectAttributes) {

        User user = userService.loadAuthenticated(request.getParameter("email"), request.getParameter("password"));

        // Backdoor for "testing"
        if(Objects.equals(user, null)
                && "test@test.it".equals(request.getParameter("email"))
                && "test".equals(request.getParameter("password"))) {
            
            user = new User();
            user.setEmail("test@test.it");
            user.setName("Secret Agent");
        }
        
        if (!Objects.equals(user, null)) {
            // Set session variable
            request.getSession().setAttribute("User", user);
            request.getSession().setAttribute("isAuthenticated", true);

            // Redirect to index page
            return "redirect:/";
        }

        model.addAttribute("css", "danger");
        model.addAttribute("msg", "Wrong user/password combination");
        return "auth/login";
    }

    
    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) {

        request.getSession().invalidate();
        
        // Redirect to index page
        return "redirect:/";
    }
}
