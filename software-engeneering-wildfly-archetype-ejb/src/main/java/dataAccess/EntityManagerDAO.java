package dataAccess;

import meet.Category;
import meet.Meet;
import session.Session;
import user.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by Christian on 19.05.2016.
 * Our implementation of the DataAccessObject
 */
@Stateless
public class EntityManagerDAO implements DataAccessObject {
    @PersistenceContext
    protected EntityManager entityManager;

    protected EntityManagerFactory entityManagerFactory;
//    public EntityManagerDAO() {
//entityManager = entityManagerFactory.createEntityManager();
//    }
    @Override
    public User findUserByName(String name) {
    return entityManager.find(User.class,name);
    }

    @Override
    public void persist(Object obj) {
    entityManager.persist(obj);
    }

    @Override
    public void delete(Object obj) {
        entityManager.remove(obj);
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
    @Override
    public Meet[] findMeets(Date start, Date end) {
        TypedQuery<Meet> query = entityManager.createQuery("SELECT m FROM Meet m WHERE c.datetime BETWEEN :startdate AND :enddate",Meet.class);
      List<Meet> results = query.setParameter("startdate",start).setParameter("enddate",end).getResultList();
        Meet[] output = new Meet[results.size()];
        results.toArray(output);
        return output;


    }
    /**
     * Returns a list of all categories
     * @return
     */
    @Override
    public Category[] getCategories() {
       TypedQuery<Category> query =  entityManager.createQuery("SELECT c FROM Category c", Category.class);

       List<Category> categoriesResult = query.getResultList();

        Category[] categories = new Category[categoriesResult.size()];
       categoriesResult.toArray(categories);
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
