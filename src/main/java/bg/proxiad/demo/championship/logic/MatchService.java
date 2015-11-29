package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.Match;
import java.util.Collection;
import java.util.List;

public interface MatchService {

    public Collection<Match> listAllMatches();
    
    public void saveOrUpdateMatch(Match match);
    
    public void generateGroupMatches();
    
    public void deleteAllGroupMatches();
    
    public Match loadMatch(Long id);
    
    public List<Match> listGroupMatches();
    
    public List<Match> listGroupMatches(Grouping group);
}
