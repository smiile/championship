package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.model.MatchDao;
import bg.proxiad.demo.championship.model.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MatchServiceImpl implements MatchService{

    @Autowired
    MatchDao matchDao;
    
    @Autowired
    GroupingService groupingService;
    
    
    @Override
    public Collection<Match> listAllMatches() {
        return matchDao.listAll();
    }

    @Override
    public void saveOrUpdateMatch(Match match) {
        matchDao.saveOrUpdate(match);
    }

    @Override
    public void generateGroupMatches() {
        List<Grouping> groups = new ArrayList(groupingService.listAllGroupings());
        
        for(Grouping group : groups) {
            List<Participant> participants1 = group.getParticipants();
            List<Participant> participants2 = group.getParticipants();
            
            for(Participant participant1 : participants1) {
                for (Participant participant2 : participants2) {
                    if(!Objects.equals(participant1.getId(), participant2.getId())) {
                        Match match = new Match();
                        match.setIsGroupMatch(true);
                        match.setParticipant1(participant1);
                        match.setParticipant2(participant2);
                        match.setInGroup(group);
                        saveOrUpdateMatch(match);
                    }
                }
            }
        }
        
    }

    @Override
    public void deleteAllGroupMatches() {
        matchDao.deleteAllGroupMatches();
    }

    @Override
    public Match loadMatch(Long id) {
        return matchDao.load(id);
    }

    @Override
    public List<Match> listGroupMatches() {
        return matchDao.listGroupMatches();
    }
    
    @Override
    public List<Match> listGroupMatches(Grouping group) {
        return matchDao.listGroupMatches(group);
    }
}
