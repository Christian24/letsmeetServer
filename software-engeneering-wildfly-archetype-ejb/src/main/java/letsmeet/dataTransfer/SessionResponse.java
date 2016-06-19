package letsmeet.dataTransfer;

import letsmeet.helpers.ReturnCodeHelper;
import letsmeet.session.Session;

/**
 * A response that gives back a session
 * @author Christian
 */
public class SessionResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = -6610605189488926392L;
	
	protected SessionData session;

    /**
     * Whetever there is no sessionData
     * @return
     */
    public boolean isEmpty() {
    	return session == null;
    }
    
    public SessionData getSession() {
    	return session;
    }
    
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