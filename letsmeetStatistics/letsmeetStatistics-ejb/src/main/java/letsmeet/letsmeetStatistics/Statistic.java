package letsmeet.letsmeetStatistics;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Sergei Fladung, Julian Handrup
 * No more functionality added due to issues with the datasource.
 * This is just an empty hull for the system that may be built here.
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/Queue1"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class Statistic implements MessageListener {

	private static final Logger log = Logger.getLogger(Statistic.class.getName());

	@Override
	public void onMessage(Message message) {
		try {
			ObjectMessage msg = (ObjectMessage) message;
			HashMap<String,String> map = (HashMap<String, String>) msg.getObject();
			log.info("Message successfully deliverered via JMS");
			
		} catch (JMSException ex) {
			throw new EJBException(ex);
		}
	}

}
