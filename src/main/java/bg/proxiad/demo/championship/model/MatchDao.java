package bg.proxiad.demo.championship.model;

import java.util.Collection;

public interface MatchDao {
    
    Collection<Match> listAll();
    
    void saveOrUpdate(Match match);
    
    void deleteAllGroupMatches();
    
    Match load(Long id);
}
