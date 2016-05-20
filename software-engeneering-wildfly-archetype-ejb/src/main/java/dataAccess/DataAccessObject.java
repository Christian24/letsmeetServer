package dataAccess;

import meet.Category;
import meet.Meet;
import session.Session;
import user.User;

/**
 * Created by Christian on 19.05.2016.
 * DataAccessObject interface which tells us what an implementation needs to be able to do
 */
public interface DataAccessObject {
    public User findUserByName(String name);
    public void persist(Object obj);
    public Meet getMeetById(int id);
    public Session findSessionByIdSimple(String id);
    public Session findSessionById(String id);
    public Category[] getCategories();
    public Category findCategoryById(String categoryId);
}
