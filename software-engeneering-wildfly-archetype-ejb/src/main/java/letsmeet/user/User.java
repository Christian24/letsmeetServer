package letsmeet.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import letsmeet.Deletable;
import letsmeet.meet.Meet;
import letsmeet.session.Session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * User object for our database
 * @author Christian
 *
 */
@Entity
public class User extends Deletable implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    protected String userName;
    @NotNull
    protected String password;
    @NotNull
    protected String description;
    @ManyToMany(mappedBy = "visitors", cascade = CascadeType.REMOVE)
    protected Set<Meet> meetsToVisit;
    @OneToMany(mappedBy = "admin",cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected Set<Meet> meetsCreated;
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected Set<Session> sessions;




    /**
     * Dummy constructor
     */
    public User()
    {
        meetsCreated = new HashSet<Meet>();
        meetsToVisit = new HashSet<Meet>();
        sessions = new HashSet<Session>();

    }

    /**
     * Get all the Meet instances this User needs to visit
     * @return
     */
    public Collection<Meet> getMeetsToVisit()
    {
        return meetsToVisit;
    }

    /**
     * Get all the Meets this User is admin for
     * @return
     */
    public Set<Meet> getMeetsCreated() {
        return meetsCreated;
    }

    /**
     * Set the value
     * @param newMeetsToVisit
     */
    public void setMeetsToVisit(Set<Meet> newMeetsToVisit ) {
        meetsToVisit = newMeetsToVisit;
    }

    /**
     * Set the value
     * @param newMeetsCreated
     */
    public void setMeetsCreated(Set<Meet> newMeetsCreated) {meetsCreated = newMeetsCreated;}

    /**
     * Get the username
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the Username
     * @param newUserName
     */
    public void setUserName(String newUserName) {
        userName = newUserName;

    }

    /**
     * Get the description
     * @return
     */
    public String getDescription() {return description;}

    /**
     * Set the description
     * @param newDescription
     */
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    /**
     * Get the password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     * @param newPassword
     */
    public void setPassword(String newPassword) {
        password = newPassword;
    }

    @Override
    public void delete() {
      meetsToVisit.clear();
     for(Meet meet : meetsCreated) {
         meet.delete();
         dataAccessObject.delete(meet);
     }

    }
}
