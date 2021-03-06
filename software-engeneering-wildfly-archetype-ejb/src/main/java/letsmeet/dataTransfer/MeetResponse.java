package letsmeet.dataTransfer;

import letsmeet.helpers.ReturnCodeHelper;
import letsmeet.meet.Meet;
import letsmeet.session.Session;

/**
 * Returns a single Meet
 * @author Christian
 */
public class MeetResponse extends SessionResponse {

	private static final long serialVersionUID = -1929891227404874726L;

    protected MeetData meet;
    
    public MeetData getMeet() {
        return meet;
    }

    public void setMeet(MeetData meet) {
        this.meet = meet;
    }
    
    /**
     * The default success constructor takes the session and the Meet
     * @param session
     * @param meet
     */
    public MeetResponse(Session session,Meet meet) {
        super(session);
        this.meet = new MeetData( meet);
    }

    /**
     * Success construcot
     * @param session the session
     * @param meet the Meet to deliver
     * @param returnCode the return code
     */
    public MeetResponse(Session session, Meet meet, int returnCode) {
        super(session);
        this.meet = new MeetData(meet);
        this.setReturnCode(returnCode);
    }

    /**
      *   Dummy constructor when we could not find a meet or session
     *   Return code is NO_ACCESS, because there is no valid session
     */
    public MeetResponse() {
        super();
    }

    /**
     * Gives back a MeetResponse if the meet cannot be found, but session is valid
     * ReturnCode is not found
     * @param session
     */
    public MeetResponse(Session session) {
        super(session);
        this.setReturnCode(ReturnCodeHelper.NOT_FOUND);
    }
}
