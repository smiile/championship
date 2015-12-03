package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.model.ParticipantResult;
import bg.proxiad.demo.championship.model.ParticipantResultDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ParticipantResultServiceImpl implements ParticipantResultService {

    @Autowired
    ParticipantResultDao participantResultDao;
    
    @Autowired
    MatchService matchService;
    
    
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
    
    // Generates a "ranking" for each group and returns it as a list
    @Override
    public List<ParticipantResult> calculateAndSetParticipantResultsPerGroup(Grouping group) {
        List<Match> groupMatches = matchService.listGroupMatches(group);
        
        HashMap<Long, ParticipantResult> hMap = new HashMap<>();
        
        // Accumulate results
        for(Match match : groupMatches) {
            ParticipantResult participantResultP1;
            ParticipantResult participantResultP2;
            
            if(hMap.containsKey(match.getParticipant1().getId())) {
                participantResultP1 = hMap.get(match.getParticipant1().getId());
            } else {
                participantResultP1 = new ParticipantResult();
            }

            participantResultP1.setPoints(participantResultP1.getPoints() + match.getP1GamesWon().intValue());
            participantResultP1.setGroup(group);
            participantResultP1.setParticipant(match.getParticipant1());
            hMap.put(match.getParticipant1().getId(), participantResultP1);

            if(hMap.containsKey(match.getParticipant2().getId())) {
                participantResultP2 = hMap.get(match.getParticipant2().getId());
            } else {
                participantResultP2 = new ParticipantResult();
            }

            participantResultP2.setPoints(participantResultP2.getPoints() + match.getP2GamesWon().intValue());
            participantResultP2.setGroup(group);
            participantResultP2.setParticipant(match.getParticipant2());
            hMap.put(match.getParticipant2().getId(), participantResultP2);
        }
        
        List<ParticipantResult> participantResults = new ArrayList(hMap.values());
        
        // Sort results
        Collections.sort(participantResults);
        
        // Delete previous participant results
        this.deleteAllParticipantResultByGroup(group);
        
        // Set positions and save entities
        for(int i = 0; i < participantResults.size(); i++) {
            ParticipantResult tmp = participantResults.get(i);
            tmp.setPosition(i+1);
            
//            participantResultService.saveOrUpdateParticipantResult(tmp); // Should be unnecesary in the service
        }
        
        return participantResults;
    }

}
