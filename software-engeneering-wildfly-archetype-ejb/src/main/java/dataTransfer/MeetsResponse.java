package dataTransfer;


import meet.Meet;
import session.Session;

/**
 * A response stream serving an array of Meet
 * Created by Christian on 19.05.2016.
 *
 */
public class MeetsResponse extends SessionResponse {
    protected MeetData[] meets;
    public MeetsResponse(Session session, Meet[] meets) {
        super(session);
       this.meets = new MeetData[meets.length];
        for(int i = 0; i<meets.length;i++) {
            this.meets[i]= new MeetData(meets[i]);
        }
    }

    /**
     * Dummy constructor that returns NO_ACCESS, because there is no valid session
     */
    public MeetsResponse() {
        super();
    }

    /**
     * If no meets were found, but the session is valid
     * @param session
     *//*
    public MeetsResponse(Session session) {
        super(session);
        setReturnCode(ReturnCodeHelper.NOT_FOUND);
    }*/
    public void setMeets(MeetData[] newMeets) {
        meets = newMeets;
    }
    public MeetData[] getMeets() {
        return meets;
    }
}
