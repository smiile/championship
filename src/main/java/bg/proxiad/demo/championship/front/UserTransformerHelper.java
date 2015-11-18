package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.User;
import bg.proxiad.demo.championship.viewbeans.UserViewBean;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTransformerHelper {
    
    @Autowired
    private UserService userService;
    
    public User transformUserViewBeanToUser(UserViewBean userViewBean) {
        User user;
        
        if(userViewBean.isNew()) {
            user = new User();
            user.setPassword(userViewBean.getPassword());
        } else {
            user = userService.loadUser(userViewBean.getId());
        }
        
        user.setEmail(userViewBean.getEmail());
        user.setName(userViewBean.getName());
        user.setLastChangedDate(System.currentTimeMillis());
        
        return user;
    }
    
    public UserViewBean transformUserToUserViewBean(User user) {
        UserViewBean userViewBean = new UserViewBean();
        
        userViewBean.setId(user.getId());
        userViewBean.setEmail(user.getEmail());
        userViewBean.setName(user.getName());
        
        return userViewBean;
    }
}
