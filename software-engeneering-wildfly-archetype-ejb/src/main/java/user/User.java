package user;

import meet.Meet;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christian on 03.05.2016.
 */
@Entity
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    protected String userName;
    protected String password;
    protected String description;
    @ManyToMany(mappedBy = "visitors")
    protected Collection<Meet> meetsToVisit;
    @OneToMany(mappedBy = "admin")
    protected Set<Meet> meetsCreated;
    public User()
    {
        meetsCreated = new HashSet<Meet>();
    }
    public Collection<Meet> getMeetsToVisit()
    {
        return meetsToVisit;
    }
    public Set<Meet> getMeetsCreated() {
        return meetsCreated;
    }
    public void setMeetsToVisit(Collection<Meet> newMeetsToVisit ) {
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
    public String getPassword(String password) {
        return password;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
}
