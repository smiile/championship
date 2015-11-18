package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.jsonbeans.GroupReceiver;
import bg.proxiad.demo.championship.model.Grouping;
import java.util.Collection;
import java.util.List;

public interface GroupingService {

    Grouping loadGrouping(Long id);

    void saveOrUpdateGrouping(Grouping grouping);

    Collection<Grouping> listAllGroupings();

    void deleteGrouping(Grouping grouping);
    
    void reassignPlayers(List<GroupReceiver> playerDistributions);
    
}
