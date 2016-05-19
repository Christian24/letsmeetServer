package web;

import dataTransfer.MeetResponse;
import dataTransfer.MeetsResponse;
import dataTransfer.SessionResponse;
import helpers.ReturnCodeHelper;
import user.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;


/**
 * Created by Christian on 10.05.2016.
 */
@WebService
@Stateless
public class OnlineIntegration  {
    protected EntityManager entityManager;

    protected EntityManagerFactory entityManagerFactory;
    public SessionResponse register(String name, String password, String description) {

        User preExisting = entityManager.find(User.class,name);
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
        entityManager.persist(user);
        return new SessionResponse(user);
    }
    public SessionResponse login(String name, String password) {
        User preExisting = entityManager.find(User.class,name);

        if (preExisting != null && preExisting.getPassword().equals(password)) {
                //User is authenticated
            return new SessionResponse(preExisting);
        }

        //ELSE: Return not authenticated
        return new SessionResponse(ReturnCodeHelper.NO_ACCESS);
    }
    public MeetsResponse getMeets(String sessionID, LocalDateTime start, LocalDateTime end) {
        return null;
    }
    public MeetResponse joinMeet(String sessionID, int meetID) {

    }
    public MeetResponse leaveMeet(String sessionID, int meetID) {

    }
    public MeetResponse getMeet(String sessionID, int meetID) {
        return null;
    }
    public MeetsResponse getMeetsByUser(String sessionID) {

    }

    @PostConstruct
    public void init() {
        //Create our EntityManager
    entityManager =entityManagerFactory.createEntityManager();
    }

}
