package bg.proxiad.demo.championship.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MATCH_TABLE")
public class Match implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToOne
    private Participant participant1;
    
    @OneToOne
    private Participant participant2;
    
    @OneToOne
    private Grouping inGroup;
    
    private boolean isGroupMatch;
    private Long p1GamesWon;
    private Long p2GamesWon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participant getParticipant1() {
        return participant1;
    }

    public void setParticipant1(Participant participant1) {
        this.participant1 = participant1;
    }

    public Participant getParticipant2() {
        return participant2;
    }

    public void setParticipant2(Participant participant2) {
        this.participant2 = participant2;
    }

    public boolean isGroupMatch() {
        return isGroupMatch;
    }

    public void setIsGroupMatch(boolean isGroupMatch) {
        this.isGroupMatch = isGroupMatch;
    }

    public Long getP1GamesWon() {
        return p1GamesWon;
    }

    public void setP1GamesWon(Long p1GamesWon) {
        this.p1GamesWon = p1GamesWon;
    }

    public Long getP2GamesWon() {
        return p2GamesWon;
    }

    public void setP2GamesWon(Long p2GamesWon) {
        this.p2GamesWon = p2GamesWon;
    }

    public Grouping getInGroup() {
        return inGroup;
    }

    public void setInGroup(Grouping inGroup) {
        this.inGroup = inGroup;
    }
}
