package bg.proxiad.demo.championship.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PARTICIPANT")
public class Participant implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "PARTICIPANT_ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHOTO_FILE_NAME")
    private String photoFileName;
    
    @ManyToOne
    private Grouping grouping;
    
    @OneToMany(mappedBy = "participant", cascade = CascadeType.REMOVE)
    private List<ParticipantResult> participantResults;
    
    @OneToMany(mappedBy = "participant1", cascade = CascadeType.REMOVE)
    private List<Match> matchesHost;
    
    @OneToMany(mappedBy = "participant2", cascade = CascadeType.REMOVE)
    private List<Match> matchesGuest;

    public Grouping getGrouping() {
        return grouping;
    }

    public void setGrouping(Grouping grouping) {
        this.grouping = grouping;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }
    
    public boolean isNew() {
        return (this.id == null);
    }
    
    public Participant() {

    }
    
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

}
