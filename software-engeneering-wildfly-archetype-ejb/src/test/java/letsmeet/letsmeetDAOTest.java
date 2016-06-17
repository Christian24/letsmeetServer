package letsmeet;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian; 
import org.jboss.shrinkwrap.api.ShrinkWrap; 
import org.jboss.shrinkwrap.api.spec.WebArchive; 
import org.junit.Test; 
import org.junit.runner.RunWith;

import letsmeet.dataAccess.DataAccessObject;
import letsmeet.meet.Category;
import letsmeet.meet.Meet;
import letsmeet.user.User;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * dataAccessObject tests
 * @author Christian, Sergei
 *
 */
@RunWith(Arquillian.class)
public class LetsmeetDAOTest {

    @EJB
    DataAccessObject dataAccessObject;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "letsmeet")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/ejb-jar.xml", "ejb-jar.xml");
    }

    @Test
    public void shouldCreateUserWithNamePasswordAndDescription() {
        User user = new User();
        user.setDescription("Ich bin ein Wemser.");
        user.setPassword("123");
        user.setUserName("Peterchen");

        dataAccessObject.persist(user);
        User peterchen = dataAccessObject.findUserByName("Peterchen");
        assertNotNull("Der User wurde nicht angelt", peterchen);
    }

    @Test(expected = EJBTransactionRolledbackException.class)
    public void shouldNotCreateUserWithoutPassword() {
        User user = new User();
        user.setDescription("Ich bin ein Wemser.");

        user.setUserName("Charlotte");

        dataAccessObject.persist(user);
        User charlotte = dataAccessObject.findUserByName("Charlotte");
        assertNull(charlotte);
    }
    @Test(expected =EJBTransactionRolledbackException.class)
    public void meetShouldNotBeCreatedWithoutAdmin() {
        Meet meet = new Meet();
        Category category = dataAccessObject.findCategoryById("Feiern");
        User user = dataAccessObject.findUserByName("admin");
        if (category != null && user != null) {
            meet.setCategory(category);
            meet.setTitle("Einen trinken gehen 2");
            meet.setLocation("Haifischbar");
            //meet.setAdmin(user);
            meet.setDescription("Nach der Arbeit kommt das Vergnügen.");
            //Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(new Date()); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour

            meet.setDateTime(cal.getTime());
            dataAccessObject.persist(meet);
            int id = meet.getId();
            Meet foundMeet = dataAccessObject.getMeetById(id);
            assertNull(foundMeet);
        }
    }

    @Test
    public void meetShouldBeCreatedWithAdmin() {
        Meet meet = new Meet();
        Category category = dataAccessObject.findCategoryById("Feiern");
        User user = dataAccessObject.findUserByName("admin");
        if (category != null && user != null) {
            meet.setCategory(category);
            meet.setTitle("Einen trinken gehen 2");
            meet.setLocation("Haifischbar");
            meet.setAdmin(user);
            meet.setDescription("Nach der Arbeit kommt das Vergnügen.");
            //Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(new Date()); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour

            meet.setDateTime(cal.getTime());
            dataAccessObject.persist(meet);
            int id = meet.getId();
            Meet foundMeet = dataAccessObject.getMeetById(id);
            assertNotNull(foundMeet);
        }
        
       

    }
    @Test
    public void shoulDeleteUser() {
    	 User user = dataAccessObject.findUserByName("admin");
    	 dataAccessObject.delete(user);
    	 user = dataAccessObject.findUserByName("admin");
    	 assertNull(user);
    }
}