package dataTransfer;

import helpers.ReturnCodeHelper;
import meet.Meet;
import session.Session;

/**
 * Created by Christian on 19.05.2016.
 * Returns a single Meet
 */
public class MeetResponse extends SessionResponse {
    protected MeetData meet;
    public MeetResponse(Session session,Meet meet) {
        super(session);
        this.meet = new MeetData( meet);
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

    public void setMeet(Meet meet) {
        this.meet = new MeetData( meet);
    }
    public MeetData getMeet() {return meet;}

}
