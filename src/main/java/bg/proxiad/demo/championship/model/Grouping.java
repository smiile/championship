package bg.proxiad.demo.championship.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GROUP_TABLE")
public class Grouping implements Serializable {

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<ParticipantResult> participantResults;

    @OneToMany(mappedBy = "inGroup", cascade = CascadeType.REMOVE)
    private List<Match> matches;
    
    @Id
    @GeneratedValue
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;
        
    @OneToMany(mappedBy = "grouping", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Participant> participants;

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

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    
    public void removeParticipants() {
        for(Participant participant : participants) {
            participant.setGrouping(null);
        }
        
        this.participants = null;
    }
    
}
