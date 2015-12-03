package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.ParticipantResult;
import java.util.Collection;
import java.util.List;

public interface ParticipantResultService {
    
    void deleteAllParticipantResultByGroup(Grouping group);
    
    void saveOrUpdateParticipantResult(ParticipantResult participantResult);
    
    Collection<ParticipantResult> listAllParticipantResult();
    
    public List<ParticipantResult> calculateAndSetParticipantResultsPerGroup(Grouping group);
}
