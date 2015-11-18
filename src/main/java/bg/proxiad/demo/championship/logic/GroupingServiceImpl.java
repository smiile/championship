package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.jsonbeans.GroupReceiver;
import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.GroupingDao;
import bg.proxiad.demo.championship.model.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupingServiceImpl implements GroupingService {

    @Autowired
    private GroupingDao groupingDao;

    @Autowired
    private ParticipantService participantService;

    @Override
    public Grouping loadGrouping(Long id) {
        return groupingDao.load(id);
    }

    @Override
    public void saveOrUpdateGrouping(Grouping grouping) {
        groupingDao.saveOrUpdate(grouping);
    }

    @Override
    public Collection<Grouping> listAllGroupings() {
        return groupingDao.listAll();
    }

    @Override
    public void deleteGrouping(Grouping grouping) {
        groupingDao.delete(grouping);
    }

    @Override
    public void reassignPlayers(List<GroupReceiver> playerDistributions) {

        for (GroupReceiver singleGroupDistribution : playerDistributions) {

            if (singleGroupDistribution.getGroupId() == -1) {
                List<Participant> participants = new ArrayList(participantService.listAllByIds(singleGroupDistribution.getParticipants()));
                
                for(Participant participant : participants) {
                    participant.setGrouping(null);
                }
                
            } else {
                Grouping grouping = this.loadGrouping(singleGroupDistribution.getGroupId());

                List<Participant> participants = new ArrayList(participantService.listAllByIds(singleGroupDistribution.getParticipants()));

                for (Participant participant : participants) {
                    participant.setGrouping(grouping);
                }

                grouping.setParticipants(participants);
            }
        }
    }

}
