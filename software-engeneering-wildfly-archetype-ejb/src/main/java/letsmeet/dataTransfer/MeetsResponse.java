package letsmeet.dataTransfer;


import letsmeet.meet.Meet;
import letsmeet.session.Session;

/**
 * A response stream serving an array of Meet
 * @author Christian
 *
 */
public class MeetsResponse extends SessionResponse {

	private static final long serialVersionUID = -6817876601299121015L;
	
	protected MeetPreviewData[] meets;
    public MeetsResponse(Session session, Meet[] meets) {
        super(session);
       this.meets = new MeetPreviewData[meets.length];
        for(int i = 0; i<meets.length;i++) {
            this.meets[i]= new MeetPreviewData(meets[i]);
        }
    }

    /**
     * Dummy constructor that returns NO_ACCESS, because there is no valid session
     */
    public MeetsResponse() {
        super();
    }

    public void setMeets(MeetPreviewData[] newMeets) {
        meets = newMeets;
    }
    
    public MeetPreviewData[] getMeets() {
        return meets;
    }
}