package bg.proxiad.demo.championship.model;

import java.util.Collection;
import java.util.List;

public interface MatchDao {
    
    Collection<Match> listAll();
    
    void saveOrUpdate(Match match);
    
    void deleteAllGroupMatches();
    
    Match load(Long id);
    
    List<Match> listGroupMatches();
}
