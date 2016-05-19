package dataTransfer;

import session.Session;
import user.User;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Christian on 19.05.2016.
 */
public class SessionData implements Serializable {
    private static final long serialVersionUID = 1L;

    protected UserData user;
    protected boolean hasEnded;

    protected String identifier;
    public SessionData(Session session) {
        super();
        hasEnded = session.getHasEnded();
        identifier = session.getIdentifier();
    }
    public SessionData() {

    }
    public String getIdentifier() {return identifier;}
    public void setIdentifier(String newUID) {
        identifier = newUID;
    }
    public void setHasEnded(boolean value) {
        hasEnded = value;
    }
    public boolean getHasEnded() {return hasEnded;}
    public UserData getUser() {return user;}
    public void setUser(UserData newUser) {
        user = newUser;
    }

    public void end() {
        hasEnded = true;
    }
}
