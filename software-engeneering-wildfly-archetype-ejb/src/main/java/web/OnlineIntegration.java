package web;

import user.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;


/**
 * Created by Christian on 10.05.2016.
 */
@WebService
@Stateless
public class OnlineIntegration  {
    protected EntityManager entityManager;
    @PersistenceContext
    protected EntityManagerFactory entityManagerFactory;
    public void register(String name, String password, String description) {

        User preExisting = entityManager.find(User.class,name);
        if(preExisting == null) {
            //Create user, because there is no such user
            createUser(name,password,description);
            //Return a new session
        } else  {
            //User already exists return an error
        }
    }
    private void createUser(String name, String password, String description) {
        User user = new User();
        user.setUserName(name);
        user.setDescription(description);
        user.setPassword(password);
    }
    public void login(String name, String password) {
        User preExisting = entityManager.find(User.class,name);

        if (preExisting != null && preExisting.getPassword().equals(password)) {
                //User is authenticated
        }

        //ELSE: Return not authenticated

    }
    @PostConstruct
    public void init() {
        //Create our EntityManager
    entityManager =entityManagerFactory.createEntityManager();
    }

}
