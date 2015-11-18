package bg.proxiad.demo.championship.viewbeans;

public class ParticipantViewBean {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String photoFileName;
    

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
    
    
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
