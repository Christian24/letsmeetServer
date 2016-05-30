package dataTransfer;

import helpers.ReturnCodeHelper;
import session.Session;
import user.User;

import javax.jws.soap.SOAPBinding;

/**
 * Created by Christian on 18.05.2016.
 */
public class SessionResponse extends ReturnCodeResponse {
    protected SessionData session;
    public boolean isEmpty() {return session == null;}
    public SessionData getSession() {return session;}
    public void setSession(SessionData newSession) {
        session = newSession;
    }
    public SessionResponse(int returnCode) {
        super(returnCode);
    }

    public SessionResponse() {
        super(ReturnCodeHelper.NO_ACCESS);
    }
    public SessionResponse(Session session) {
        super(ReturnCodeHelper.OK);
        this.setSession(new SessionData(session));

    }
}
