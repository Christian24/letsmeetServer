package dataTransfer;

import helpers.ReturnCodeHelper;
import meet.Meet;
import session.Session;

/**
 * Created by Christian on 19.05.2016.
 * A response stream in the server of meets
 */
public class MeetsResponse extends SessionResponse {
    protected Meet[] meets;
    public MeetsResponse(Session session, Meet[] meets) {
        super(session);
        this.meets = meets;
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
    public void setMeets(Meet[] newMeets) {
        meets = newMeets;
    }
    public Meet[] getMeets() {
        return meets;
    }
}
