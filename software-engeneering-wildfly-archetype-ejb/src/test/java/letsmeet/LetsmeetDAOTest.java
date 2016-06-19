package letsmeet;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap; 
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test; 
import org.junit.runner.RunWith;

import letsmeet.dataAccess.DataAccessObject;
import letsmeet.dataAccess.DataBuilder;
import letsmeet.dataTransfer.ConversationData;
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
import static org.junit.Assert.*;
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
    
    @Test(expected =EJBTransactionRolledbackException.class)
    public void shouldNotCreateUserWithTakenUsername() throws EJBTransactionRolledbackException {
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
		SessionResponse register = onlineIntegration.register("RegisterUser", "Register", "RegisterUser");
		assertEquals(register.getReturnCode(),letsmeet.helpers.ReturnCodeHelper.OK);
		onlineIntegration.deleteUser(register.getSession().getIdentifier());
	}
	
	@Test
	public void shouldLoginUser() {
		SessionResponse login = onlineIntegration.register("LoginUser", "Login", "LoginUser");
		onlineIntegration.logout(login.getSession().getIdentifier());
		SessionResponse loginR = onlineIntegration.login("LoginUser", "Login");
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, loginR.getReturnCode());
		
		ReturnCodeResponse response = onlineIntegration.logout(loginR.getSession().getIdentifier());
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, response.getReturnCode());
	}

	@Test
	public void shouldLogoutUser() {
		SessionResponse logout = onlineIntegration.register("LogoutUser", "WebWemser","LogoutUser");
		ReturnCodeResponse logoutR = onlineIntegration.logout(logout.getSession().getIdentifier());
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, logoutR.getReturnCode());
	}
	
	@Test
	public void shouldCreateMeet(){
		SessionResponse create = onlineIntegration.register("CreateUser", "Create", "CreateUser");
		onlineIntegration.logout(create.getSession().getIdentifier());
		
		SessionResponse createR = onlineIntegration.login("CreateUser", "Create");
		String sessionID = createR.getSession().getIdentifier();
	        //Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
	        Calendar cal = Calendar.getInstance(); // creates calendar
	        cal.setTime(new Date()); // sets calendar time/date
	        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		MeetResponse createRes = onlineIntegration.createMeet(createR.getSession().getIdentifier(), "Feiern", "Jetzt wird gefeiert!", "China ist Europameister", "Mexico", cal.getTime(), 2);
		MeetData data = createRes.getMeet();
		int meetID = data.getId();
		assertNotNull(onlineIntegration.getMeet(sessionID, meetID).getMeet());
		assertEquals(onlineIntegration.getMeet(sessionID, meetID).getMeet().getAdmin().getUserName(), "CreateUser");	
		ReturnCodeResponse createL = onlineIntegration.logout(sessionID);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, createL.getReturnCode());
	}
	
	@Test
	public void shouldReturnMeets(){
		SessionResponse register = onlineIntegration.register("ReturnUser", "ReturnUser", "ReturnUser");
		onlineIntegration.logout(register.getSession().getIdentifier());
			SessionResponse session = onlineIntegration.login("ReturnUser","ReturnUser");
			String sessionID = session.getSession().getIdentifier();
		        //Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
		        Calendar cal = Calendar.getInstance(); // creates calendar
		        cal.setTime(new Date()); // sets calendar time/date
		        Date start = cal.getTime();
		        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		        Date end = cal.getTime();
			MeetsResponse response = onlineIntegration.getMeets(sessionID, start, end);
			assertTrue(!response.isEmpty());
		ReturnCodeResponse logout = onlineIntegration.logout(sessionID);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, logout.getReturnCode());
	}
	
	
	public void createTestMeet1(){
		SessionResponse session = onlineIntegration.register("TestUser", "TestUser", "TestUser");
		String sessionID = session.getSession().getIdentifier();
			//Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
	        Calendar cal = Calendar.getInstance(); // creates calendar
	        cal.setTime(new Date()); // sets calendar time/date
	        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		onlineIntegration.createMeet(session.getSession().getIdentifier(), "Feiern", "TestFeier", "China ist Europameister", "Mexico", cal.getTime(), 2);
		onlineIntegration.logout(sessionID);
	}
	
	@Test 
	public void shouldJoinAMeet(){
		SessionResponse register = onlineIntegration.register("JoinUser", "JoinUser", "JoinUser");
		onlineIntegration.logout(register.getSession().getIdentifier());

		createTestMeet1();
		SessionResponse session = onlineIntegration.login("JoinUser", "JoinUser");
		String sessionID = session.getSession().getIdentifier();
		UserData join = session.getSession().getUser();
		
		//meet (id) she wants to join:
		MeetsResponse meets = onlineIntegration.getMeetsByCategory(sessionID, "Feiern");
		MeetPreviewData[] meetArray = meets.getMeets();
		MeetPreviewData meet = meetArray[0];
		for(MeetPreviewData p : meetArray){
			if(p.getAdmin().equals("TestUser")) meet = p; 
		}
		int meetIDSheWantsToJoin = meet.getId();
		
		//join meet:
		MeetResponse joined = onlineIntegration.joinMeet(sessionID, meetIDSheWantsToJoin);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK,joined.getReturnCode());
		
		//check if she is registered to the meet:
		MeetResponse joinedMeet = onlineIntegration.getMeet(sessionID, meetIDSheWantsToJoin);
		MeetData joinedMeetData = joinedMeet.getMeet();
		boolean alreadyIn = joinedMeetData.alreadyIn(join);
		
		assertEquals(true,alreadyIn);
		
		ReturnCodeResponse response = onlineIntegration.logout(sessionID);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, response.getReturnCode());
	}
	
	public void createTestMeet2(){
		SessionResponse session = onlineIntegration.register("Test1User", "Test1User", "Test1User");
		String sessionID = session.getSession().getIdentifier();
			//Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
	        Calendar cal = Calendar.getInstance(); // creates calendar
	        cal.setTime(new Date()); // sets calendar time/date
	        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		onlineIntegration.createMeet(sessionID, "Feiern", "TestFeier1", "China ist Europameister", "Mexico", cal.getTime(), 5);
		onlineIntegration.logout(sessionID);
	}
	@Test
	public void shouldLeaveAMeet(){
		SessionResponse register = onlineIntegration.register("LeaveUser", "LeaveUser", "LeaveUser");
		onlineIntegration.logout(register.getSession().getIdentifier());
//		As used in shoulJoinAMeet()
		createTestMeet2();
		SessionResponse session = onlineIntegration.login("LeaveUser", "LeaveUser");
		String sessionID = session.getSession().getIdentifier();
		UserData leaver = session.getSession().getUser();
		
		//meet (id) she wants to join:
		MeetsResponse meets = onlineIntegration.getMeetsByCategory(sessionID, "Feiern");
		MeetPreviewData[] meetArray = meets.getMeets();
		MeetPreviewData meet = meetArray[0];
		for(MeetPreviewData p : meetArray){
			if(p.getAdmin().equals("Test1User")) meet = p; 
		}
		int meetIDSheWantsToJoin = meet.getId();
		MeetResponse joined = onlineIntegration.joinMeet(sessionID, meetIDSheWantsToJoin);
		
		MeetResponse res = onlineIntegration.leaveMeet(sessionID, joined.getMeet().getId());
		Set<UserData> users = res.getMeet().getVisitors();
		
		assertFalse(users.contains(session.getSession().getUser()));
		ReturnCodeResponse response = onlineIntegration.logout(sessionID);
	}
	
	

	@Test
	public void shouldDeleteMeet(){
		SessionResponse register = onlineIntegration.register("Deleter", "Deleter", "Deleter");
		onlineIntegration.logout(register.getSession().getIdentifier());
		
		SessionResponse deleteResponse = onlineIntegration.login("Deleter", "Deleter");
		String sessionID = deleteResponse.getSession().getIdentifier();
		//meet to delete:
		
	        //Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
	        Calendar cal = Calendar.getInstance(); // creates calendar
	        cal.setTime(new Date()); // sets calendar time/date
	        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		MeetResponse response = onlineIntegration.createMeet(deleteResponse.getSession().getIdentifier(), "Feiern", "Jetzt wird gefeiert! DELETER", "China ist Europameister", "Mexico", cal.getTime(), 2);
		MeetData data = response.getMeet();
		int meetID = data.getId();
		
		//delete
		SessionResponse delete = onlineIntegration.deleteMeet(deleteResponse.getSession().getIdentifier(), meetID);
		ReturnCodeResponse logout = onlineIntegration.logout(delete.getSession().getIdentifier());
	}
	
	@Test
	public void shouldDeleteUser(){
		//register new user
		SessionResponse session = onlineIntegration.register("ManfredNoppe", "Noppenschaum", "Ich bin der Landvogt");
		assertEquals(session.getReturnCode(),letsmeet.helpers.ReturnCodeHelper.OK);
		String sessionID = session.getSession().getIdentifier();
		//delete user
		ReturnCodeResponse response = onlineIntegration.deleteUser(sessionID);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, response.getReturnCode());
		//test if login fails:
		session = onlineIntegration.login("ManfredNoppe", "Noppenschaum");
		assertEquals(letsmeet.helpers.ReturnCodeHelper.NO_ACCESS, session.getReturnCode());
	}
	
	@Test
	public void shouldEditMeet(){
		SessionResponse editor = onlineIntegration.register("Editor", "Edit", "IchPutzNur");
        //Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		MeetResponse editable = onlineIntegration.createMeet(editor.getSession().getIdentifier(), "Feiern", "EditorMeet", "Editer", "editi", cal.getTime(), 3);
		String descOrig = editable.getMeet().getDescription();
		MeetResponse edited = onlineIntegration.updateMeet(editor.getSession().getIdentifier(), editable.getMeet().getId(), "Feiern", "DasistNeu", "Editer", "editi", cal.getTime(), 3);
		String descNew = edited.getMeet().getDescription();
		assertNotEquals(descOrig,descNew);
	}
	
	/**
	 * The purpose of this test is to perform a full scaled scenario where a user registers 
	 * and creates a meet. Another user registers and joins that meet. A third user registers, joins 
	 * and asks a question in the comments. The first user(admin) then answers this question.
	 * The second user also answers and then leaves the meet.
	 * Later the admin deletes the meet.
	 */
	@Test
	public void superTest(){
		//register first user
		SessionResponse session1 = onlineIntegration.register("first", "first", "first user");
		String sessionID1 = session1.getSession().getIdentifier();
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK,session1.getReturnCode());
		
		//create first users meet
			//Adapated from http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
	        Calendar cal = Calendar.getInstance(); // creates calendar
	        cal.setTime(new Date()); // sets calendar time/date
	        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		MeetResponse response = onlineIntegration.createMeet(sessionID1, "Feiern", "Feierschweinerei", "China ist Europameister", "Mexico", cal.getTime(), 2);
		MeetData data = response.getMeet();
		int meetId = data.getId();
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK,response.getReturnCode());
		
		//register second user
		SessionResponse session2 = onlineIntegration.register("second", "second", "second user");
		String sessionID2 = session2.getSession().getIdentifier();
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, session2.getReturnCode());
		
		//let second user join first users meet
		MeetResponse response2 = onlineIntegration.joinMeet(sessionID2, data.getId());
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, response2.getReturnCode());
		
		//register third user
		SessionResponse session3 = onlineIntegration.register("third", "third", "third user");
		String sessionID3 = session3.getSession().getIdentifier();
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, session3.getReturnCode());
		
		//let third user join meet
		MeetResponse response3 = onlineIntegration.joinMeet(sessionID3, data.getId());
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, response3.getReturnCode());
		
		//third user makes a comment
		MeetResponse conversation = onlineIntegration.createNewConversation(sessionID3, meetId, "Third users has a Question oO");
		Set<ConversationData> conversations = conversation.getMeet().getConversations();
		int conversationId = 0;
		for(ConversationData cd : conversations){
			if(cd.getOrigin() == meetId) conversationId = cd.getId();
		}
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK,conversation.getReturnCode());
		
		
		//first user answers
		MeetResponse answer1 = onlineIntegration.addToConversation(sessionID1, conversationId, "Answer from user1");
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, answer1.getReturnCode());
		
		//second user answers
		MeetResponse answer2 = onlineIntegration.addToConversation(sessionID2, conversationId, "Answer from user2");
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK, answer2.getReturnCode());
		
		//second user leaves meet and logs out
		//leaves
		SessionResponse leave2 = onlineIntegration.leaveMeet(sessionID2, meetId);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK,leave2.getReturnCode());
		//logout
		ReturnCodeResponse logout2 = onlineIntegration.logout(session2.getSession().getIdentifier());
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK,logout2.getReturnCode());
		
		//admin deletes
		SessionResponse delete1 = onlineIntegration.deleteMeet(sessionID1, meetId);
		assertEquals(letsmeet.helpers.ReturnCodeHelper.OK,delete1.getReturnCode());
		
		//admin logs out
		ReturnCodeResponse logout1 = onlineIntegration.logout(session1.getSession().getIdentifier());
		
		//third user logs out
		ReturnCodeResponse logout3 = onlineIntegration.logout(session3.getSession().getIdentifier());
		
	}
}





