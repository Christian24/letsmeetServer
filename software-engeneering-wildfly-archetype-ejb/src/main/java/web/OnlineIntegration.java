package web;

import dataAccess.DataAccessObject;
import dataAccess.EntityManagerDAO;
import dataTransfer.*;
import helpers.ReturnCodeHelper;
import meet.Category;
import meet.Meet;
import session.Session;
import sun.rmi.runtime.Log;
import user.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Christian on 10.05.2016.
 */
@WebService
@Stateless
public class OnlineIntegration  {
    protected final String[] categories = {"Sport", "Kultur", "Essen & Trinken", "Feiern", "Kennenlernen"};
    private static final Logger log = Logger.getLogger( OnlineIntegration.class.getName() );
    @EJB
    protected DataAccessObject dataAccessObject = new EntityManagerDAO();
    public SessionResponse register(String name, String password, String description) {
        log.info("Register a new user: Name: "+name + " Password: " + password + " Descr.: " +description);
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
        log.info("User logs in");

        if (preExisting != null && preExisting.getPassword().equals(password)) {
                //User is authenticated
            log.info("User ist vorhanden");
            return new SessionResponse(preExisting);
        }
        //ELSE: Return not authenticated
        log.info("User ist nicht vorhanden");
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

        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {
            Meet[] meets = dataAccessObject.findMeets(start,end);
            return new MeetsResponse(session,meets);
        }
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
    public MeetResponse updateMeet(String sessionID, int meetID, String categoryId, String description, String title, String location,
                                   Date date, int maxUsers) {
        Session session = dataAccessObject.findSessionById(sessionID);
        Meet meet = dataAccessObject.getMeetById(meetID);
        Category category = dataAccessObject.findCategoryById(categoryId);

        if(session != null && meet != null  && category != null) {
            if(meet.getVisitors().size() > maxUsers) {
                return new MeetResponse(session,meet,ReturnCodeHelper.NO_ACCESS);
            } else {
                meet.setMaxGuests(maxUsers);
                meet.setDescription(description);
                meet.setTitle(title);
                meet.setLocation(location);
                meet.setDateTime(date);
                meet.setCategory(category);

            }
        }
        return new MeetResponse();
    }
    public MeetResponse leaveMeet(String sessionID, int meetID) {
        log.info("Session ID: " + sessionID );
        Session session = dataAccessObject.findSessionById(sessionID);
        Meet meet = dataAccessObject.getMeetById(meetID);

        if(session != null && meet != null ) {
            meet.leave(session.getUser());
            return new MeetResponse(session,meet);
        }

        return new MeetResponse();
    }
    public ReturnCodeResponse deleteUser(String sessionID) {
        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {
            dataAccessObject.delete(session.getUser());
            session.setHasEnded(true);
            return new ReturnCodeResponse(ReturnCodeHelper.OK);
        }
        return new ReturnCodeResponse();
    }

    /**
     * Updates a user's password and description
     * @param sessionID
     * @param password the new password
     * @param description the new description
     * @return
     */
    public SessionResponse updateUser(String sessionID,String password, String description) {
        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {
            User user = session.getUser();
            if(user != null) {
                user.setPassword(password);
                user.setDescription(description);
                return new SessionResponse(user);
            }
            return new SessionResponse(ReturnCodeHelper.NOT_FOUND);

        }
        return new SessionResponse();
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
    public  MeetsResponse getMeetsByCategory(String sessionId, String categoryId) {
        Session session = dataAccessObject.findSessionById(sessionId);
        Category category = dataAccessObject.findCategoryById(categoryId);
        if(session != null && category != null) {
            return new MeetsResponse(session,category.getMeets());
        }
        return  new MeetsResponse();
    }

    /**
     * Creates a new meet
     * @param sessionId
     * @param categoryId
     * @param description
     * @param title
     * @param location
     * @param date
     * @return
     */
    public MeetResponse createMeet(String sessionId,String categoryId, String description, String title, String location,
                                   Date date, int maxUsers) {
        Session session = dataAccessObject.findSessionById(sessionId);
        Category category = dataAccessObject.findCategoryById(categoryId);
        if(session != null && category != null) {
            Meet meet = new Meet();
            meet.setCategory(category);
            meet.setDescription(description);
            meet.setTitle(title);
            meet.setLocation(location);
            meet.setDateTime(date);
            meet.setMaxGuests(maxUsers);
            dataAccessObject.persist(meet);
            return new MeetResponse(session,meet);
        }
        return new MeetResponse();
    }

    @PostConstruct
    public void init() {
        //Create our EntityManager

        createCategories();
    }

    /**
     * Creates our categories
     */
    private void createCategories() {
        for(String category : categories) {
            createCategory(category);
        }
    }
    private void createCategory(String name) {
        Category category = dataAccessObject.findCategoryById(name);
        if(category != null) {
        Category newCategory = new Category();
            category.setTitle(name);
            dataAccessObject.persist(category);
        }
    }

}
