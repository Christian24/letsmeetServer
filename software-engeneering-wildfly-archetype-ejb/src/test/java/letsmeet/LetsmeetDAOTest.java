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
import letsmeet.dataTransfer.ReturnCodeResponse;
import letsmeet.dataTransfer.SessionResponse;
import letsmeet.meet.Category;
import letsmeet.meet.Meet;
import letsmeet.user.User;
import letsmeet.web.OnlineIntegration;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertEquals;

/**
 * dataAccessObject tests
 * @author Christian, Sergei, Julian
 *
 */
@RunWith(Arquillian.class)
public class LetsmeetDAOTest {

    @EJB
    DataAccessObject dataAccessObject;
    
    @EJB
	OnlineIntegration onlineIntegration;
    
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

        user.setUserName("Terrence");

        dataAccessObject.persist(user);
        User terrence = dataAccessObject.findUserByName("Terrence");
        assertNull(terrence);
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
    	 if(user != null) {
    		 
    	
    	 dataAccessObject.delete(user);
    	 user = dataAccessObject.findUserByName("admin");
    	 assertNull(user);
    	 }
    }
    
    @Test
    public void shouldNotCreateUserWithTakenUsername() {
    	User duplicateUser = new User();
    	duplicateUser.setUserName("Charlotte");
    	duplicateUser.setPassword("1234");
    	duplicateUser.setDescription("Schön aber dafür kann sie nichts");
    	
    	dataAccessObject.persist(duplicateUser);
    	
        User originalUser = dataAccessObject.findUserByName("Charlotte");
        
        String password = originalUser.getPassword();
        String description = originalUser.getDescription();
        
        if(password != duplicateUser.getPassword() && description != duplicateUser.getDescription()) {
        	assertNotNull(originalUser);
        }   
    }   
    
    @Test
    public void shouldLoginUser(){
    	SessionResponse session = onlineIntegration.login("admin", "WebWemser");
    	assertEquals(letsmeet.helpers.ReturnCodeHelper.NO_ACCESS, session.getReturnCode());   	
    }
    
    @Test
    public void shouldLogutUser(){
    	//login user "admin" from DataBuilder
    	SessionResponse session = onlineIntegration.login("admin", "WebWemser");
    	//logout user and compare ReturnResponseCode
    	ReturnCodeResponse response = onlineIntegration.logout(session.getSession().getIdentifier());
    	assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, response.getReturnCode());
    }
    
    
    
    
    
}