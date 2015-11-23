package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.model.Match;
import java.util.Collection;

public interface MatchService {

    public Collection<Match> listAllMatches();
    
    public void saveOrUpdateMatch(Match match);
    
    public void generateGroupMatches();
    
    public void deleteAllGroupMatches();
    
    public Match loadMatch(Long id);
    
}
