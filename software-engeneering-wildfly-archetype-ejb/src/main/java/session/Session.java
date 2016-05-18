package session;

import user.User;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Christian on 18.05.2016.
 */
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany
    protected User user;
    protected boolean hasEnded;
    @Id
    protected String identifier;
    public Session() {
        super();
        hasEnded = false;
        identifier = UUID.randomUUID().toString();
    }
public String getIdentifier() {return identifier;}
    public void setIdentifier(String newUID) {
        identifier = newUID;
    }
    public void setHasEnded(boolean value) {
        hasEnded = value;
    }
    public boolean getHasEnded() {return hasEnded;}
    public User getUser() {return user;}
    public void setUser(User newUser) {
        user = newUser;
    }

    public void end() {
        hasEnded = true;
    }
}
