package dataTransfer;

import helpers.ReturnCodeHelper;
import session.Session;
import user.User;

import javax.jws.soap.SOAPBinding;

/**
 * Created by Christian on 18.05.2016.
 */
public class SessionResponse extends ReturnCodeResponse {
    protected Session session;
    public boolean isEmpty() {return session == null;}
    public Session getSession() {return session;}
    public void setSession(Session newSession) {
        session = newSession;
    }
    public SessionResponse(int returnCode) {
        super(returnCode);
    }
    public SessionResponse(User user) {
        super(ReturnCodeHelper.OK);
        session = new Session();
        session.setUser(user);
    }
}
