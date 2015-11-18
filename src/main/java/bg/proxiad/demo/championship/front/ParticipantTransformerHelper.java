package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.model.Participant;
import bg.proxiad.demo.championship.viewbeans.ParticipantViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParticipantTransformerHelper {

    @Autowired
    private ParticipantService participantService;
    
    public ParticipantViewBean transformParticipantToParticipantViewBean(Participant participant) {
        ParticipantViewBean participantViewBean = new ParticipantViewBean();
        
        participantViewBean.setId(participant.getId());
        participantViewBean.setEmail(participant.getEmail());
        participantViewBean.setFirstName(participant.getFirstName());
        participantViewBean.setLastName(participant.getLastName());
        participantViewBean.setPhotoFileName(participant.getPhotoFileName());
        
        return participantViewBean;
    }
    
    public Participant transformParticipantViewBeanToParticipant(ParticipantViewBean participantViewBean) {
        Participant participant;
        
        if(participantViewBean.isNew()) {
            participant = new Participant();
        } else {
            participant = participantService.loadParticipant(participantViewBean.getId());
        }
        
        participant.setEmail(participantViewBean.getEmail());
        participant.setFirstName(participantViewBean.getFirstName());
        participant.setLastName(participantViewBean.getLastName());
        participant.setPhotoFileName(participantViewBean.getPhotoFileName());
        
        return participant;
    }
}
