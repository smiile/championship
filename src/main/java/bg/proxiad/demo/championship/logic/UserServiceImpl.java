package bg.proxiad.demo.championship.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.proxiad.demo.championship.model.User;
import bg.proxiad.demo.championship.model.UserDao;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User loadUser(Long id) {
        return userDao.load(id);
    }

    @Override
    public void saveOrUpdateUser(User user) {
        user.setLastChangedDate(System.currentTimeMillis());
        userDao.saveOrUpdate(user);
    }

    @Override
    public Collection<User> listAllUsers() {
        return userDao.listAll();
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    @Override
    public User loadAuthenticated(String email, String password) {
        return userDao.loadAuthenticated(email, password);
    }
    
    @Override
    public User loadUserByEmail(String email) {
        
        return userDao.loadByEmail(email);
    }

}
