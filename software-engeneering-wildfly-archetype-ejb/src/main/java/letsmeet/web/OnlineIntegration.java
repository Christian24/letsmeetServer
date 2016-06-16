package letsmeet.web;

import letsmeet.dataAccess.DataAccessObject;
import letsmeet.dataTransfer.*;
import letsmeet.helpers.ReturnCodeHelper;
import letsmeet.meet.Category;
import letsmeet.meet.Conversation;
import letsmeet.meet.Meet;
import letsmeet.meet.Reply;
import letsmeet.session.Session;
import letsmeet.user.User;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 * The Webservice that will be consumed by the Android Client
 * Created by Christian on 10.05.2016.
 */
@WebService
@Stateless
@WebContext(contextRoot="/letsmeet")
public class OnlineIntegration  {

    private static final Logger log = Logger.getLogger( OnlineIntegration.class.getName() );
    
    @EJB
    protected DataAccessObject dataAccessObject;

	@EJB
	private LetsmeetStatisticsBean letsmeetStatisticsBean;
    
    /**
     * Registers a new user
     * @param name
     * @param password
     * @param description
     * @return
     */
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

    /**
     * Helper for creation of user
     * @param name
     * @param password
     * @param description
     * @return the SessionResponse
     */
    private SessionResponse createUser(String name, String password, String description) {
        User user = new User();
        user.setUserName(name);
        user.setDescription(description);
        user.setPassword(password);
        dataAccessObject.persist(user);
        Session session = createSession(user);
        return new SessionResponse(session);
    }

    /**
     * Logs a user in
     * @param name
     * @param password
     * @return
     */
    public SessionResponse login(String name, String password) {

        User preExisting = dataAccessObject.findUserByName(name);
        log.info("User logs in");

        if (preExisting != null && preExisting.getPassword().equals(password)) {
                //User is authenticated
            log.info("User ist vorhanden");
            Session session = createSession(preExisting);
            return new SessionResponse(session);
        }
        //ELSE: Return not authenticated
        log.info("User ist nicht vorhanden oder Password falsch.");
        return new SessionResponse(ReturnCodeHelper.NO_ACCESS);
    }

    /**
     * Logs a user out
     * @param sessionID
     * @return
     */
    public ReturnCodeResponse logout(String sessionID) {
        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {
            session.setHasEnded(true);
            return new ReturnCodeResponse(ReturnCodeHelper.OK);
        }
        return new ReturnCodeResponse(ReturnCodeHelper.NOT_FOUND);
    }

    /**
     * Gets meets for start and end time
     * @param sessionID
     * @param start unix timestamp
     * @param end unix timestamp
     * @return
     */
    public MeetsResponse getMeets(String sessionID, Date start, Date end) {

        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {
            Meet[] meets = dataAccessObject.findMeets(start,
                    end);
            return new MeetsResponse(session,meets);
        }
        return new MeetsResponse();
    }

    /**
     * Makes a user associated with the given session join the given Meet
     * @param sessionID
     * @param meetID
     * @return
     */
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

    /**
     * Updates a Meet
     * @param sessionID
     * @param meetID
     * @param categoryId
     * @param description
     * @param title
     * @param location
     * @param date a unix timestamp
     * @param maxUsers
     * @return
     */
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

    /**
     * Makes the user associated with the session leave the specified Meet
     * @param sessionID
     * @param meetID
     * @return
     */
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

    /**
     * Deletes a Meet
     * @param sessionID
     * @param meetID
     * @return
     */
    public SessionResponse deleteMeet(String sessionID, int meetID){
        Session session = dataAccessObject.findSessionById(sessionID);
        Meet meet = dataAccessObject.getMeetById(meetID);

        if(session != null && meet != null && session.getUser().equals(meet.getAdmin()) ) {
           dataAccessObject.delete(meet);
            return new SessionResponse(session);
        }
        return new SessionResponse();
    }

    /**
     * Deletes a user
     * @param sessionID
     * @return
     */
    public ReturnCodeResponse deleteUser(String sessionID) {
        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {

            session.setHasEnded(true);
            dataAccessObject.delete(session.getUser());

            return new ReturnCodeResponse(ReturnCodeHelper.OK);
        }
        return new ReturnCodeResponse();
    }

    /**
     * Updates a user's password
     * @param sessionID
     * @param password the new password
     *
     * @return
     */
    public SessionResponse updateUserPassword(String sessionID,String password) {
        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {
            User user = session.getUser();
            if(user != null) {
                user.setPassword(password);


                return new SessionResponse(session);
            }
            return new SessionResponse(ReturnCodeHelper.NOT_FOUND);

        }
        return new SessionResponse();
    }
    /**
     * Updates a user's description
     * @param sessionID
     * @param description the new description
     *
     * @return
     */
    public SessionResponse updateUserDescription(String sessionID,String description) {
        Session session = dataAccessObject.findSessionById(sessionID);
        if(session != null) {
            User user = session.getUser();
            if(user != null) {
                user.setDescription(description);


                return new SessionResponse(session);
            }
            return new SessionResponse(ReturnCodeHelper.NOT_FOUND);

        }
        return new SessionResponse();
    }

    /**
     * Gets a specific meet
     * @param sessionID
     * @param meetID
     * @return
     */
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

    /**
     * Gets all meets by the user who is associated with the session
     * @param sessionID
     * @return
     */
    public MeetsResponse getMeetsByUser(String sessionID) {
        Session session = dataAccessObject.findSessionById(sessionID);
if(session != null) {
    Meet[] meets = new Meet[session.getUser().getMeetsCreated().size()];
    session.getUser().getMeetsCreated().toArray(meets);
  return new MeetsResponse(session, meets );

}
        return new MeetsResponse();
    }

    /**
     * Returns all the categories available on the server
     * @param sessionId
     * @return
     */
    public CategoriesResponse getCategories(String sessionId) {
        Session session = dataAccessObject.findSessionById(sessionId);
        Category[] categories = dataAccessObject.getCategories();
        if(session != null && categories != null) {
            return new CategoriesResponse(session,categories);
        }
        return new CategoriesResponse();
    }

    /**
     * Returns only categories which have at least one Meet in them
     * @param sessionId
     * @return
     */
    public CategoriesResponse getCategoriesWithMeets(String sessionId) {
    	Session session = dataAccessObject.findSessionById(sessionId);
        Category[] categories = dataAccessObject.getCategories();
        if(session != null && categories != null) {
        	ArrayList<Category> categoryList = new ArrayList<Category>();
        for(Category category : categories) {
            if(category.getMeets().length > 0) {
                categoryList.add(category);
            }
        }
            categories = new Category[categoryList.size()];
            categoryList.toArray(categories);

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
     * @param date a unix timestamp
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
            meet.setAdmin(session.getUser());
            meet.setTitle(title);
            meet.setLocation(location);
            meet.setDateTime(date);
            meet.setMaxGuests(maxUsers);
            dataAccessObject.persist(meet);
            return new MeetResponse(session,meet);
        }
        return new MeetResponse();
    }
    public MeetResponse createNewConversation(String sessionId, int meetId, String text){
        Session session = dataAccessObject.findSessionById(sessionId);
        Meet meet = dataAccessObject.getMeetById(meetId);
        if(session != null && meet != null) {
            Conversation conversation = new Conversation(session.getUser(),text,meet);
            dataAccessObject.persist(conversation);
            //Get again to the database
            meet = dataAccessObject.getMeetById(meetId);
            return new MeetResponse(session,meet);
        }
        return new MeetResponse();
    }
    public MeetResponse replyToConversation(String sessionId, int conversationId, String text){
        Session session = dataAccessObject.findSessionById(sessionId);
        Conversation conversation = dataAccessObject.findConversationById(conversationId);
        if(session != null && conversation != null){
            Reply reply = new Reply(conversation,session.getUser(),text);
            dataAccessObject.persist(reply);
            return new MeetResponse(session,conversation.getOrigin());
        }
        return new MeetResponse();
    }
    public MeetResponse deleteReply(String sessionId, int replyId) {
        Session session = dataAccessObject.findSessionById(sessionId);
        Reply reply = dataAccessObject.findReplyById(replyId);
        if(session != null && reply != null){
            reply.delete();
            return new MeetResponse(session,reply.getParent().getOrigin());
        }
        return new MeetResponse();
    }
    public  MeetResponse deleteConversation(String sessionId, int conversationId){
        Session session = dataAccessObject.findSessionById(sessionId);
        Conversation conversation = dataAccessObject.findConversationById(conversationId);
        if(session != null && conversation != null) {
            Meet meet = conversation.getOrigin();
        dataAccessObject.delete(conversation);
            return new MeetResponse(session,meet);
        }
        return new MeetResponse();

        }
    

    /**
     * Instructs the DataAccessObject to create a new session
     * @param user
     * @return
     */
    private Session createSession(User user) {
       return dataAccessObject.createSession(user);
    }
    
    /**
     * Delivers the meet to the statistics component so it can be stored for further use.
     * @param user
     */
    private void newMeetStatistics(Meet meet) {
    	letsmeetStatisticsBean.newMeetStatistics(meet);
    }
}