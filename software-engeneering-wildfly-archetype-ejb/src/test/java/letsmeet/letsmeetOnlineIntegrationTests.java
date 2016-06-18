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
import letsmeet.dataTransfer.ReturnCodeResponse;
import letsmeet.dataTransfer.SessionData;
import letsmeet.dataTransfer.SessionResponse;
import letsmeet.web.OnlineIntegration;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertEquals;

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
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, "letsmeet")
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
	

}
