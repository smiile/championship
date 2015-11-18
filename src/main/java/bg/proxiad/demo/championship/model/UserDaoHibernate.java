package bg.proxiad.demo.championship.model;

import java.util.Collection;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoHibernate implements UserDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void saveOrUpdate(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public User load(Long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> listAll() {
        return sessionFactory.getCurrentSession()
			.createCriteria(User.class)
			.list();
    }
    
    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(User.class.getName(), user);
    }

    @Override
    public User loadAuthenticated(String email, String password) {
        List<User> users = sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.and(Restrictions.eq("email", email), Restrictions.eq("password", password))).list();
        
        User result = (!users.isEmpty()) ? users.get(0) : null ;
        
        return result;
    }
    
    @Override
    public User loadByEmail(String email) {
        
        List<User> users = sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("email", email)).list();
        
        User result = (!users.isEmpty()) ? users.get(0) : null ;
        
        return result;
    }

}
