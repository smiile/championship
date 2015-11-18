package bg.proxiad.demo.championship.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TABLE")
public class User implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password; // TODO: add salt and hash password

    @Column(name = "LAST_CHANGED_DATE")
    private Date lastChangedDate;

    public User() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(long timestamp) {
        this.lastChangedDate = new Date(timestamp);
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isNew() {
        return (this.id == null);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
