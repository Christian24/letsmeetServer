package letsmeet;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment; 
import org.jboss.arquillian.junit.Arquillian; 
import org.jboss.shrinkwrap.api.ShrinkWrap; 
import org.jboss.shrinkwrap.api.spec.WebArchive; 
import org.junit.Test; 
import org.junit.runner.RunWith;

import dataAccess.DataAccessObject;
import user.User;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Sergei
 */
@RunWith (Arquillian.class)
public class letsmeetDAOTest {
	
	@EJB
	DataAccessObject letsmeetDAO;
	
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

		letsmeetDAO.persist(user);
		User peterchen = letsmeetDAO.findUserByName("Peterchen");
		assertNotNull("Der User wurde nicht angelt", peterchen);
	}
	@Test
	public void shouldNotCreateUserWithoutPassword(){
		User user = new User();
		user.setDescription("Ich bin ein Wemser.");

		user.setUserName("Charlotte");

		letsmeetDAO.persist(user);
		User charlotte = letsmeetDAO.findUserByName("Charlotte");
		assertNull( charlotte);
	}

}