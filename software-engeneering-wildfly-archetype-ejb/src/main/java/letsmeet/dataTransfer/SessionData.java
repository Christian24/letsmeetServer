package letsmeet.dataTransfer;



import letsmeet.session.Session;


/**
 * DataTransferObject for Session
 * @author Christian
 */
public class SessionData extends DataTransferObject {

	private static final long serialVersionUID = -4997071719190646206L;
	
	protected UserData user;
    protected boolean hasEnded;

    protected String identifier;

    /**
     * Creates a new SessionData given the session
     * @param session the Session a SessionData is based on
     */
    public SessionData(Session session) {
        super();
        hasEnded = session.getHasEnded();
        identifier = session.getIdentifier();
        if(session.getUser() != null) {
            UserData userData = new UserData(session.getUser());
            setUser(userData);
        }
    }

    /**
     * Dummy constructor
     */
    public SessionData() {

    }
    
    public String getIdentifier() {
    	return identifier;
    }
    
    public void setIdentifier(String newUID) {
        identifier = newUID;
    }
    
    public void setHasEnded(boolean value) {
        hasEnded = value;
    }
    
    public boolean getHasEnded() {
    	return hasEnded;
    }
    
    public UserData getUser() {
    	return user;
    }
    
    public void setUser(UserData newUser) {
        user = newUser;
    }
}