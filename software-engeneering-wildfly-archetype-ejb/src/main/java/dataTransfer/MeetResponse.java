package dataTransfer;

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
    public void setMeet(Meet meet) {
        this.meet = new MeetData( meet);
    }
    public MeetData getMeet() {return meet;}

}
