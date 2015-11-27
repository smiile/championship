package bg.proxiad.demo.championship.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GroupResult implements Serializable {
    
    @Id
    @GeneratedValue
    Long id;
    
    @ManyToOne
    Grouping group;
    
    @ManyToOne
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
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
    
    
    
}
