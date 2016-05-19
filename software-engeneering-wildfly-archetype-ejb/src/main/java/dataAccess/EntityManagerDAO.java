package dataAccess;

import meet.Meet;
import session.Session;
import user.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Christian on 19.05.2016.
 * Our implementation of the DataAccessObject
 */
public class EntityManagerDAO implements DataAccessObject {
    protected EntityManager entityManager;

    protected EntityManagerFactory entityManagerFactory;
    public EntityManagerDAO() {
      entityManager =  entityManagerFactory.createEntityManager();
    }
    @Override
    public User findUserByName(String name) {
    return entityManager.find(User.class,name);
    }

    @Override
    public void persist(Object obj) {
    entityManager.persist(obj);
    }

    @Override
    public Meet getMeetById(int id) {
        return entityManager.find(Meet.class,id);
    }

    /**
     * Finds a session by id, but does not care if it is active
     * @param id
     * @return
     */
    @Override
    public Session findSessionByIdSimple(String id) {
        return entityManager.find(Session.class,id);
    }

    /**
     * Returns a session given an id.
     * Also checks if the session is currently active.
     * @param id
     * @return
     */
    @Override
    public Session findSessionById(String id) {
      Session session = entityManager.find(Session.class,id);
        if(session != null && !session.getHasEnded())
            return session;
        else
            return null;
    }
}
