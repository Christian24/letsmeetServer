package letsmeet.session;

import javax.persistence.*;

import letsmeet.user.User;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Session
 * @author Christian
 */
@Entity
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne(cascade = CascadeType.REMOVE)
    protected User user;
    protected boolean hasEnded;
    @Id
    protected String identifier;

    /**
     * Creates a new session
     */
    public Session() {
        super();
        hasEnded = false;
        identifier = UUID.randomUUID().toString();
    }

    /**
     * Gets the identifier
     * @return
     */
    public String getIdentifier() {
    	return identifier;
    }

    /**
     * Sets the identifier
     * @param newUID
     */
    public void setIdentifier(String newUID) {
        identifier = newUID;
    }
    
    public void setHasEnded(boolean value) {
        hasEnded = value;
    }
    
    public boolean getHasEnded() {
    	return hasEnded;
    }

    /**
     * Gets the User associated with this session instance
     * @return
     */
    public User getUser() {
    	return user;
    }

    /**
     * Sets the User associated with this session
     * @param newUser
     */
    public void setUser(User newUser) {
        user = newUser;
    }

    public void end() {
        hasEnded = true;
    }
}