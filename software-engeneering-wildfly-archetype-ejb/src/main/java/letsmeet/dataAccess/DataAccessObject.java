package letsmeet.dataAccess;

import javax.ejb.Local;
import javax.jws.soap.SOAPBinding;

import letsmeet.meet.Category;
import letsmeet.meet.Conversation;
import letsmeet.meet.Meet;
import letsmeet.meet.Reply;
import letsmeet.session.Session;
import letsmeet.user.User;

import java.util.Date;

/**
 * Created by Christian on 19.05.2016.
 * DataAccessObject interface which tells us what an implementation
 * needs to be able to do
 * @author Christian
 */
@Local
public interface DataAccessObject {
    public User findUserByName(String name);
    public void persist(Object obj);
    public Meet getMeetById(int id);
    public Session findSessionByIdSimple(String id);
    public Session findSessionById(String id);
    public Session createSession(User user);
    public Category[] getCategories();
    public Category findCategoryById(String categoryId);
    public Conversation findConversationById(int id);
    public Reply findReplyById(int id);
    public Meet[] findMeets(Date start, Date end);
    public void delete(Object obj);
    public void flush();

}
