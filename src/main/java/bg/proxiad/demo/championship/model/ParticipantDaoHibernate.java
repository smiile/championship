package bg.proxiad.demo.championship.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantDaoHibernate implements ParticipantDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(Participant participant) {
        sessionFactory.getCurrentSession().saveOrUpdate(participant);
    }

    @Override
    public Participant load(Long id) {
        return sessionFactory.getCurrentSession().get(Participant.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Participant> listAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Participant.class)
                .list();
    }

    @Override
    public Collection<Participant> listAllByIds(List<Long> participantIds) {
        if(participantIds.isEmpty()) {
            return new ArrayList();
        }
        
        return sessionFactory.getCurrentSession()
                .createCriteria(Participant.class)
                .add(Restrictions.in("id", participantIds))
                .list();
    }
    
    @Override
    public void delete(Participant participant) {
        sessionFactory.getCurrentSession().delete(Participant.class.getName(), participant);
    }

    
}
