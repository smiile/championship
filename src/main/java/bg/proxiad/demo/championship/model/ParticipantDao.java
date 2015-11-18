package bg.proxiad.demo.championship.model;

import java.util.Collection;
import java.util.List;

public interface ParticipantDao {

	void saveOrUpdate(Participant participant);

	Participant load(Long id);
	
	Collection<Participant> listAll();
        
        Collection<Participant> listAllByIds(List<Long> participants);

        void delete(Participant participant);
}