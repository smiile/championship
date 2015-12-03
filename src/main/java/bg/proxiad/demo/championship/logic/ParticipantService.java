package bg.proxiad.demo.championship.logic;

import java.util.Collection;

import bg.proxiad.demo.championship.model.Participant;
import java.util.List;

public interface ParticipantService {
	
	Participant loadParticipant(Long id);
	
	void saveOrUpdateParticipant(Participant participant);
	
	Collection<Participant> listAllParticipants();
        
        void deleteParticipant(Participant participant);
        
        public Collection<Participant> listAllByIds(List<Long> participantIds);
        
        public Collection<Participant> listAllUnassignedParticipants();
}
