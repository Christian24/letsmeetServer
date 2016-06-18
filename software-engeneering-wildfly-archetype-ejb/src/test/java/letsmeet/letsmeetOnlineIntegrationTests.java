/**
 * 
 */
package letsmeet;

import javax.ejb.EJB;
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
import letsmeet.web.OnlineIntegration;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertNull;

/**
 * @author Julian Handrup Tests for OnlineIntegration EJB
 *
 */
@RunWith(Arquillian.class)
public class letsmeetOnlineIntegrationTests {

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
		assertNull(onlineIntegration.getMeet(sessionID, meetID).getMeet());
		assertEquals(onlineIntegration.getMeet(sessionID, meetID).getMeet().getAdmin(), "admin");	
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
		MeetsResponse meets = onlineIntegration.getMeetsByCategory(sessionID, "Feiern");
		MeetPreviewData meet = meets.getMeets()[0];
		MeetResponse toJoin = onlineIntegration.getMeet(sessionID, meet.getId());
		onlineIntegration.joinMeet(sessionID, meet.getId());
		Set<UserData> users = toJoin.getMeet().getVisitors();
		assertTrue(users.contains(charlotte));
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
}