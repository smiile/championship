package bg.proxiad.demo.championship.model;

import java.util.Collection;

public interface UserDao {

	void saveOrUpdate(User user);

	User load(Long id);
	
	Collection<User> listAll();
        
        void delete(User user);

        User loadAuthenticated(String email, String password);
        
        public User loadByEmail(String email);
}