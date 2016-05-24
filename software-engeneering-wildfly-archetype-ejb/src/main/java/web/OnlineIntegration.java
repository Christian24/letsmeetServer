package web;

import dataAccess.DataAccessObject;
import dataAccess.EntityManagerDAO;
import dataTransfer.*;
import helpers.ReturnCodeHelper;
import meet.Category;
import meet.Meet;
import session.Session;
import user.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Date;


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
    public ReturnCodeResponse logout(String sessionID) {
        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {
            session.setHasEnded(true);
            return new ReturnCodeResponse(ReturnCodeHelper.OK);
        }
        return new ReturnCodeResponse(ReturnCodeHelper.NOT_FOUND);
    }
    public MeetsResponse getMeets(String sessionID, Date start, Date end) {
        return new MeetsResponse();
    }
    public MeetResponse joinMeet(String sessionID, int meetID) {
        Session session = dataAccessObject.findSessionById(sessionID);
        Meet meet = dataAccessObject.getMeetById(meetID);

        if(session != null && meet != null ) {
            boolean result =meet.join(session.getUser());
            int returnCode = ReturnCodeHelper.OK;
            if(result) {
                //We were not able to join the meet, tell our client
                returnCode = ReturnCodeHelper.NO_ACCESS;
            }
            return new MeetResponse(session,meet,returnCode);
        }

        return new MeetResponse();
    }
    public MeetResponse leaveMeet(String sessionID, int meetID) {
        Session session = dataAccessObject.findSessionById(sessionID);
        Meet meet = dataAccessObject.getMeetById(meetID);

        if(session != null && meet != null ) {
            meet.leave(session.getUser());
            return new MeetResponse(session,meet);
        }

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
    public CategoriesResponse getCategories(String sessionId) {
        Session session = dataAccessObject.findSessionById(sessionId);
        Category[] categories = dataAccessObject.getCategories();
        if(session != null && categories != null) {
            return new CategoriesResponse(session,categories);
        }
        return new CategoriesResponse();
    }

    /**
     * Gets all meets for a given category
     * @param sessionId
     * @param categoryId
     * @return
     */
    public  MeetsResponse getMeets(String sessionId, String categoryId) {
        Session session = dataAccessObject.findSessionById(sessionId);
        Category category = dataAccessObject.findCategoryById(categoryId);
        if(session != null && category != null) {
            return new MeetsResponse(session,category.getMeets());
        }
        return  new MeetsResponse();
    }

    @PostConstruct
    public void init() {
        //Create our EntityManager
        dataAccessObject = new EntityManagerDAO();
    }

}
