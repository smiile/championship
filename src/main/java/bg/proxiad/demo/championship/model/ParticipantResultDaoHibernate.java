package bg.proxiad.demo.championship.model;

import java.util.Collection;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantResultDaoHibernate implements ParticipantResultDao {

    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public void saveOrUpdate(ParticipantResult participantResult) {
        sessionFactory.getCurrentSession().saveOrUpdate(participantResult);
    }

    @Override
    public Collection<ParticipantResult> listAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(ParticipantResult.class)
                .list();
    }

    @Override
    public void deleteAllByGroup(Grouping group) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from ParticipantResult where group = :group")
                .setParameter("group", group)
                .executeUpdate();
    }
}
