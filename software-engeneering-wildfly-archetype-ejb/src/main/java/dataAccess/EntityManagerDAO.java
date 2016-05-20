package dataAccess;

import meet.Category;
import meet.Meet;
import session.Session;
import user.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

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

    /**
     * Returns a list of all categories
     * @return
     */
    @Override
    public Category[] getCategories() {
        Query query = entityManager.createQuery("SELECT e FROM Category e");
       List categoriesResult = query.getResultList();
        Category[] categories = new Category[categoriesResult.size()];
        for(Object categoryObj : categoriesResult) {
            Category category = (Category)categoryObj;

        }
        return categories;
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
