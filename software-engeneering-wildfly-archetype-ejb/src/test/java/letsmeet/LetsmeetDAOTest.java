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
import letsmeet.dataTransfer.MeetData;
import letsmeet.dataTransfer.MeetPreviewData;
import letsmeet.dataTransfer.MeetResponse;
import letsmeet.dataTransfer.MeetsResponse;
import letsmeet.dataTransfer.ReturnCodeResponse;
import letsmeet.dataTransfer.SessionData;
import letsmeet.dataTransfer.SessionResponse;
import letsmeet.dataTransfer.UserData;
import letsmeet.meet.Category;
import letsmeet.meet.Meet;
import letsmeet.user.User;
import letsmeet.web.OnlineIntegration;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertTrue;
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
        
        if(!password.equals(duplicateUser.getPassword()) && !description.equals(duplicateUser.getDescription())) {
        	assertNotNull(originalUser);
        }   
    }   
//========================================================================================================================//
    //TESTS FOR ONLINEINTEGRATION EJB
//========================================================================================================================//
   
    
    
    @Test
	public void shouldRegisterUser(){
		SessionResponse register = onlineIntegration.register("Manfred", "Mullemaus", "Ich bin der Landvogt");
		assertEquals(register.getReturnCode(),letsmeet.helpers.ReturnCodeHelper.OK);
	}
	
	@Test
	public void shouldLoginUser() {
		SessionResponse session = onlineIntegration.login("admin", "WebWemser");
		assertEquals(letsmeet.helpers.ReturnCodeHelper.NO_ACCESS, session.getReturnCode());
	}

	@Test
	public void shouldLogoutUser() {
		// login user "admin" from DataBuilder
		SessionResponse session = onlineIntegration.login("admin", "WebWemser");
		// logout user and compare ReturnResponseCode
		SessionData sessionData = session.getSession();
		String sessionID = sessionData.getIdentifier();
		ReturnCodeResponse response = onlineIntegration.logout(sessionID);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, response.getReturnCode());
	}
	
	@Test
	public void shoulCreateMeet(){
		SessionResponse session = onlineIntegration.login("admin", "WebWemser");
		String sessionID = session.getSession().getIdentifier();
	        //Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
	        Calendar cal = Calendar.getInstance(); // creates calendar
	        cal.setTime(new Date()); // sets calendar time/date
	        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		MeetResponse response = onlineIntegration.createMeet(sessionID, "Feiern", "Jetzt wird gefeiert!", "China ist Europameister", "Mexico", cal.getTime(), 2);
		MeetData data = response.getMeet();
		int meetID = data.getId();
		assertNotNull(onlineIntegration.getMeet(sessionID, meetID).getMeet());
		assertEquals(onlineIntegration.getMeet(sessionID, meetID).getMeet().getAdmin().getUserName(), "admin");	
	}
	
	@Test
	public void shouldReturnMeets(){
			SessionResponse session = onlineIntegration.login("admin","WebWemser");
			String sessionID = session.getSession().getIdentifier();
		        //Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
		        Calendar cal = Calendar.getInstance(); // creates calendar
		        cal.setTime(new Date()); // sets calendar time/date
		        Date start = cal.getTime();
		        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		        Date end = cal.getTime();
			MeetsResponse response = onlineIntegration.getMeets(sessionID, start, end);
			assertTrue(!response.isEmpty());
	}
	
	@Test 
	public void shouldJoinAMeet(){
		//uses example meet from databuilder
		SessionResponse session = onlineIntegration.login("Charlotte", "WebWemser");
		String sessionID = session.getSession().getIdentifier();
		UserData charlotte = session.getSession().getUser();
		
		//meet (id) she wants to join:
		MeetsResponse meets = onlineIntegration.getMeetsByCategory(sessionID, "Feiern");
		MeetPreviewData[] meetArray = meets.getMeets();
		assertNotNull(meetArray[0]);
		MeetPreviewData meet = meetArray[0];
		int meetIDSheWantsToJoin = meet.getId();
		
		//join meet:
		MeetResponse joined = onlineIntegration.joinMeet(sessionID, meetIDSheWantsToJoin);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK,joined.getReturnCode());
		
		//check if she is registered to the meet:
		MeetResponse joinedMeet = onlineIntegration.getMeet(sessionID, meetIDSheWantsToJoin);
		MeetData joinedMeetData = joinedMeet.getMeet();
		boolean alreadyIn = joinedMeetData.alreadyIn(charlotte);
		
		assertEquals(true,alreadyIn);
	}

	@Test
	public void shouldLeaveAMeet(){
//		As used in shoulJoinAMeet()
		SessionResponse session = onlineIntegration.login("Charlotte", "WebWemser");
		String sessionID = session.getSession().getIdentifier();
		UserData charlotte = session.getSession().getUser();
		MeetsResponse meets = onlineIntegration.getMeetsByCategory(sessionID, "Feiern");
		MeetPreviewData meet = meets.getMeets()[0];
		MeetResponse toJoin = onlineIntegration.getMeet(sessionID, meet.getId());
		onlineIntegration.joinMeet(sessionID, meet.getId());
		
		onlineIntegration.leaveMeet(sessionID, meet.getId());
		Set<UserData> users = toJoin.getMeet().getVisitors();
		assertTrue(!users.contains(charlotte));
	}
	
	@Test
	public void shouldDeleteMeet(){
		SessionResponse sessionResponse = onlineIntegration.login("admin", "WebWemser");
		String sessionID = sessionResponse.getSession().getIdentifier();
		//meet to delete:
		MeetsResponse meetsResponse = onlineIntegration.getMeetsByUser(sessionID);
		MeetPreviewData[] meetsByUser = meetsResponse.getMeets();
		assertTrue(meetsByUser.length>0);
		int meetID = meetsByUser[0].getId();
		//delete
		SessionResponse delete = onlineIntegration.deleteMeet(sessionID, meetID);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, delete.getReturnCode());
	}
	
	
}