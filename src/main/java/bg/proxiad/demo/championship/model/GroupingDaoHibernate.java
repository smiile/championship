package bg.proxiad.demo.championship.model;

import java.util.Collection;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupingDaoHibernate implements GroupingDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void saveOrUpdate(Grouping group) {
        sessionFactory.getCurrentSession().saveOrUpdate(group);
    }

    @Override
    public Grouping load(Long id) {
        return sessionFactory.getCurrentSession().get(Grouping.class, id);
    }

    @Override
    public Collection<Grouping> listAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Grouping.class)
                .list();
    }

    @Override
    public void delete(Grouping group) {
        sessionFactory.getCurrentSession().delete(Grouping.class.getName(), group);
    }

}
