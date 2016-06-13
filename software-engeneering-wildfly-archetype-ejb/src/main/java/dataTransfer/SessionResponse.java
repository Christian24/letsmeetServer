package dataTransfer;

import helpers.ReturnCodeHelper;
import session.Session;
import user.User;

import javax.jws.soap.SOAPBinding;

/**
 * A response that gives back a session
 * Created by Christian on 18.05.2016.
 */
public class SessionResponse extends ReturnCodeResponse {
    protected SessionData session;

    /**
     * Whetever there is no sessionData
     * @return
     */
    public boolean isEmpty() {return session == null;}
    public SessionData getSession() {return session;}
    public void setSession(SessionData newSession) {
        session = newSession;
    }
    public SessionResponse(int returnCode) {
        super(returnCode);
    }

    /**
     * Dummy constructor that basically says we're not allowed/able to do that
     */
    public SessionResponse() {
        super(ReturnCodeHelper.NO_ACCESS);
    }

    /**
     * Our success constuctor, that takes a session to deliver to the
     * client
     * @param session
     */
    public SessionResponse(Session session) {
        super(ReturnCodeHelper.OK);
        this.setSession(new SessionData(session));

    }
}
