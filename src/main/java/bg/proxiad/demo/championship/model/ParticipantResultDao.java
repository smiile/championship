package bg.proxiad.demo.championship.model;

import java.util.Collection;

public interface ParticipantResultDao {
    
    void deleteAllByGroup(Grouping group);
    
    void saveOrUpdate(ParticipantResult participantResult);
    
    Collection<ParticipantResult> listAll();
}
