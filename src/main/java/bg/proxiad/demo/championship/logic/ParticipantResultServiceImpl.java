package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.ParticipantResult;
import bg.proxiad.demo.championship.model.ParticipantResultDao;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ParticipantResultServiceImpl implements ParticipantResultService {

    @Autowired
    ParticipantResultDao participantResultDao;
    
    
    @Override
    public void saveOrUpdateParticipantResult(ParticipantResult participantResult) {
        participantResultDao.saveOrUpdate(participantResult);
    }

    @Override
    public Collection<ParticipantResult> listAllParticipantResult() {
        return participantResultDao.listAll();
    }

    @Override
    public void deleteAllParticipantResultByGroup(Grouping group) {
        participantResultDao.deleteAllByGroup(group);
    }

}
