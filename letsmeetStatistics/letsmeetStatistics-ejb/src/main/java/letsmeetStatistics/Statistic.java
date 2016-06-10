package letsmeetStatistics;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.jboss.logging.Logger;

/**
 * Created by Sergei Fladung
 */
	@MessageDriven (
			activationConfig = {
					@ActivationConfigProperty(
							propertyName = "destination", propertyValue = "java:/jms/queue/ExpiryQueue"),
					@ActivationConfigProperty(
							propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
					@ActivationConfigProperty(
							propertyName = "messageSelector", propertyValue = "DocType LIKE 'Name'")
			})
	
public class Statistic {
		//do something
}
	