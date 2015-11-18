package bg.proxiad.demo.championship.viewbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GroupingViewBean {
    
    private Long id;

    private String name;
    
    private List<Long> participants;
    
    private HashMap<Long, String> participantMap = new HashMap<>();
    
    public GroupingViewBean() {
        this.participants = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getParticipants() {
        if(Objects.equals(this.participants, null)) {
            return new ArrayList<>();
        }
        
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public HashMap<Long, String> getParticipantMap() {
        return participantMap;
    }

    public void setParticipantMap(HashMap<Long, String> participantMap) {
        this.participantMap = participantMap;
    }
    
    public void clearParticipantMap() {
        this.participantMap.clear();
    }

    public void addToParticipantMap(Long id, String participantName) {
        this.participantMap.put(id, participantName);
    }
    
    public boolean isNew() {
        return (this.id == null);
    }
}
