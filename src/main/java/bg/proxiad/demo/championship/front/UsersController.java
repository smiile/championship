package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.User;
import bg.proxiad.demo.championship.viewbeans.UserViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    UserFormValidator userFormValidator;

    @Autowired
    UserTransformerHelper userTransformerHelper;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listUsers(ModelMap model) {

        model.addAttribute("users", userService.listAllUsers());
        return "user/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdateUser(ModelMap model,
            @ModelAttribute("userForm") @Validated UserViewBean userViewBean,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "user/create-edit";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");

            if (userViewBean.isNew()) {
                redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
            }

            User user = userTransformerHelper.transformUserViewBeanToUser(userViewBean);
            userService.saveOrUpdateUser(user);

            // POST/REDIRECT/GET
            return "redirect:/app/users";
        }
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Long id, ModelMap model) {

        User user = userService.loadUser(id);
        UserViewBean userViewBean = userTransformerHelper.transformUserToUserViewBean(user);
        userViewBean.setScenario("update");
        
        model.addAttribute("userForm", userViewBean);

        return "user/create-edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {

        User user = userService.loadUser(id);

        userService.deleteUser(user);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is deleted!");

        return "redirect:/app/users";
    }

    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String changePasswordView(ModelMap model, @PathVariable("id") Long id) {
        User user = userService.loadUser(id);
        UserViewBean userViewBean = userTransformerHelper.transformUserToUserViewBean(user);
        userViewBean.setScenario("changePassword");

        model.addAttribute("userForm", userViewBean);
        return "user/change-password";
    }
    
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@ModelAttribute("userForm") @Validated UserViewBean userViewBean,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "user/change-password";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Password changed successfully!");

            User user = userService.loadUser(userViewBean.getId());
            user.setPassword(userViewBean.getNewPassword());
            userService.saveOrUpdateUser(user);

            // POST/REDIRECT/GET
            return "redirect:/app/users";
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createUser(ModelMap model) {
        UserViewBean userViewBean = new UserViewBean();
        userViewBean.setScenario("create");

        model.addAttribute("userForm", userViewBean);
        return "user/create-edit";
    }

}
