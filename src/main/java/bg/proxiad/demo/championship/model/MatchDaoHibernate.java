package bg.proxiad.demo.championship.model;

import java.util.Collection;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MatchDaoHibernate implements MatchDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Collection<Match> listAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Match.class)
                .list();
    }

    @Override
    public void saveOrUpdate(Match match) {
        sessionFactory.getCurrentSession().saveOrUpdate(match);
    }

    @Override
    public void deleteAllGroupMatches() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("delete Match where isGroupMatch is true");
        query.executeUpdate();
    }

    @Override
    public Match load(Long id) {
        return sessionFactory.getCurrentSession().get(Match.class, id);
    }

}
