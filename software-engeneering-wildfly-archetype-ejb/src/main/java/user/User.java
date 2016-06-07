package user;

import meet.Meet;
import session.Session;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christian on 03.05.2016.
 * User object for our database
 */
@Entity
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    protected String userName;
    @NotNull
    protected String password;
    @NotNull
    protected String description;
    @ManyToMany(mappedBy = "visitors")
    protected Set<Meet> meetsToVisit;
    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    protected Set<Meet> meetsCreated;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Set<Session> sessions;

    public User()
    {
        meetsCreated = new HashSet<Meet>();
        meetsToVisit = new HashSet<Meet>();
        sessions = new HashSet<Session>();
    }
    public Collection<Meet> getMeetsToVisit()
    {
        return meetsToVisit;
    }
    public Set<Meet> getMeetsCreated() {
        return meetsCreated;
    }
    public void setMeetsToVisit(Set<Meet> newMeetsToVisit ) {
        meetsToVisit = newMeetsToVisit;
    }
    public void setMeetsCreated(Set<Meet> newMeetsCreated) {meetsCreated = newMeetsCreated;}
    public String getUserName() {
        return userName;
    }
    public void setUserName(String newUserName) {
        userName = newUserName;
    }
    public String getDescription() {return description;}
    public void setDescription(String newDescription) {
        description = newDescription;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
}
