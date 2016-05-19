package web;

import dataAccess.DataAccessObject;
import dataAccess.EntityManagerDAO;
import dataTransfer.MeetResponse;
import dataTransfer.MeetsResponse;
import dataTransfer.SessionResponse;
import helpers.ReturnCodeHelper;
import meet.Meet;
import session.Session;
import user.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.time.LocalDateTime;


/**
 * Created by Christian on 10.05.2016.
 */
@WebService
@Stateless
public class OnlineIntegration  {
    protected DataAccessObject dataAccessObject;
    public SessionResponse register(String name, String password, String description) {

        User preExisting = dataAccessObject.findUserByName(name);
        if(preExisting == null) {
            //Create user, because there is no such user
           return createUser(name,password,description);
            //Return a new session
        }

        return new SessionResponse(ReturnCodeHelper.NO_ACCESS);
    }
    private SessionResponse createUser(String name, String password, String description) {
        User user = new User();
        user.setUserName(name);
        user.setDescription(description);
        user.setPassword(password);
        dataAccessObject.persist(user);
        return new SessionResponse(user);
    }
    public SessionResponse login(String name, String password) {
        User preExisting = dataAccessObject.findUserByName(name);

        if (preExisting != null && preExisting.getPassword().equals(password)) {
                //User is authenticated
            return new SessionResponse(preExisting);
        }

        //ELSE: Return not authenticated
        return new SessionResponse(ReturnCodeHelper.NO_ACCESS);
    }
    public MeetsResponse getMeets(String sessionID, LocalDateTime start, LocalDateTime end) {
        return new MeetsResponse();
    }
    public MeetResponse joinMeet(String sessionID, int meetID) {

        return new MeetResponse();
    }
    public MeetResponse leaveMeet(String sessionID, int meetID) {

        return new MeetResponse();
    }
    public MeetResponse getMeet(String sessionID, int meetID) {
        Meet meet = dataAccessObject.getMeetById(meetID);
        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null && meet != null) {
            return new MeetResponse(session,meet);
        }
        if(session != null){
            return new MeetResponse(session);
        }
        return new MeetResponse();
    }
    public MeetsResponse getMeetsByUser(String sessionID) {
        Session session = dataAccessObject.findSessionById(sessionID);
if(session != null) {
    Meet[] meets = new Meet[session.getUser().getMeetsCreated().size()];
    session.getUser().getMeetsCreated().toArray(meets);
  return new MeetsResponse(session, meets );

}
        return new MeetsResponse();
    }

    @PostConstruct
    public void init() {
        //Create our EntityManager
        dataAccessObject = new EntityManagerDAO();
    }

}
