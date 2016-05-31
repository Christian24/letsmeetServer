package letsmeet;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment; 
import org.jboss.arquillian.junit.Arquillian; 
import org.jboss.shrinkwrap.api.ShrinkWrap; 
import org.jboss.shrinkwrap.api.spec.WebArchive; 
import org.junit.Test; 
import org.junit.runner.RunWith;

import dataAccess.DataAccessObject;

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
				.addPackages(true, "de/xbank")
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("META-INF/ejb-jar.xml", "ejb-jar.xml");
	}

}