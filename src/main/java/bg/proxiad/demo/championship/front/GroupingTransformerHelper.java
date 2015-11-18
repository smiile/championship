package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.GroupingService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.viewbeans.GroupingViewBean;
import bg.proxiad.demo.championship.model.Participant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupingTransformerHelper {
    @Autowired
    private ParticipantService participantService;
    
    @Autowired
    private GroupingService groupingService;
    
    public Grouping transformGroupingViewBeanToGrouping(GroupingViewBean groupViewBean) {
        Grouping grouping;
        if(groupViewBean.isNew()) {
            grouping = new Grouping();
        } else {
            grouping = groupingService.loadGrouping(groupViewBean.getId());
        }
        
        grouping.setName(groupViewBean.getName());
        List<Participant> participants = new ArrayList(participantService.listAllByIds(groupViewBean.getParticipants()));
        
        for(Participant participant : participants) {
            participant.setGrouping(grouping);
        }
        
        grouping.setParticipants(participants);
        
        return grouping;
    }
    
    public GroupingViewBean transformGroupingToGroupingViewBean(Grouping grouping) {
        GroupingViewBean groupingViewBean = new GroupingViewBean();
        groupingViewBean.setId(grouping.getId());
        groupingViewBean.setName(grouping.getName());
        
        return groupingViewBean;
    }
    
    
}
