package letsmeet.dataAccess;


import javax.ejb.Stateless;
import javax.persistence.*;

import letsmeet.meet.Category;
import letsmeet.meet.Conversation;
import letsmeet.meet.Meet;
import letsmeet.meet.Reply;
import letsmeet.session.Session;
import letsmeet.user.User;

import java.util.Date;
import java.util.List;

/**
 * Our implementation of the DataAccessObject
 * @author Christian
 *
 */
@Stateless
public class EntityManagerDAO implements DataAccessObject {
    @PersistenceContext
    protected EntityManager entityManager;


    /**
     * Finds a user by name
     * @param name
     * @return
     */
    @Override
    public User findUserByName(String name) {
    return entityManager.find(User.class,name);
    }

    /**
     * Persists the given object to the database.
     * @param obj
     */
    @Override
    public void persist(Object obj) {
    entityManager.persist(obj);
    }

    /**
     * Deletes the given object from the database
     * Adapted from http://stackoverflow.com/questions/17027398/java-lang-illegalargumentexception-removing-a-detached-instance-com-test-user5
     * @param obj
     */
    @Override
    public void delete(Object obj) {

       
        //Prepare deleteContent
        //if(obj instanceof Deletable)
          //  ((Deletable)obj).delete();
        entityManager.remove(entityManager.contains(obj) ? obj : entityManager.merge(obj));

    }

    /**
     * Finds a session by id
     * @param id
     * @return
     */
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

    /**
     * Creates a new session for a given user
     * @param user
     * @return
     */
    @Override
    public Session createSession(User user) {
        Session session = new Session();
        session.setUser(user);
        persist(session);
        return session;
    }

    /**
     * Finds all Meets in the given timeframe
     * @param start
     * @param end
     * @return
     */
    @Override
    public Meet[] findMeets(Date start, Date end) {
        TypedQuery<Meet> query = entityManager.createQuery("SELECT m FROM Meet m WHERE m.dateTime BETWEEN :startdate AND :enddate",Meet.class);
        List<Meet> results = query.setParameter("startdate",start).setParameter("enddate",end).getResultList();
        Meet[] output = new Meet[results.size()];
        results.toArray(output);
        return output;


    }

    /**
     * Finds a conversation by the given id
     * @param id
     * @return
     */
    @Override
    public Conversation findConversationById(int id){
        return entityManager.find(Conversation.class,id);
    }

    /**
     * Finds a Reply by the given id
     * @param id
     * @return
     */
    @Override
    public Reply findReplyById(int id){
        return entityManager.find(Reply.class,id);
    }
    /**
     * Returns a list of all categories
     * @return
     */
    @Override
    public Category[] getCategories() {
       TypedQuery<Category> query =  entityManager.createQuery("SELECT c FROM Category c", Category.class);

       List<Category> categoriesResult = entityManager.createQuery("SELECT c FROM Category c").getResultList();

        Category[] categories = new Category[categoriesResult.size()];
       categoriesResult.toArray(categories);
        return categories;
    }

    /**
     * Flushes the entity manager
     */
    @Override
    public void flush(){
    entityManager.flush();
    }

    /**
     * Returns a category based on a categoryId
     * @param categoryId
     * @return
     */
    @Override
    public Category findCategoryById(String categoryId) {
        return entityManager.find(Category.class,categoryId);
    }
}
