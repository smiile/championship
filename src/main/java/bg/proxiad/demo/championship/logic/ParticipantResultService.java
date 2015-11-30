package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.ParticipantResult;
import java.util.Collection;

public interface ParticipantResultService {
    
    void deleteAllParticipantResultByGroup(Grouping group);
    
    void saveOrUpdateParticipantResult(ParticipantResult participantResult);
    
    Collection<ParticipantResult> listAllParticipantResult();
}
