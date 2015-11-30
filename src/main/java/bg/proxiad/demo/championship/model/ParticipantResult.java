package bg.proxiad.demo.championship.model;

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ParticipantResult implements Serializable, Comparable<ParticipantResult> {
    
    @Id
    @GeneratedValue
    Long id;
    
    @OneToOne
    Grouping group;
    
    @OneToOne
    Participant participant;
    
    Integer position;
    Integer points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grouping getGroup() {
        return group;
    }

    public void setGroup(Grouping group) {
        this.group = group;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPoints() {
        return (!Objects.equal(points, null)) ? points : 0;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public int compareTo(ParticipantResult participantResult) {
        return participantResult.getPoints().compareTo(points);
    }
    
}
