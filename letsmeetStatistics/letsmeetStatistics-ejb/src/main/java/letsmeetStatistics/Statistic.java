package letsmeetStatistics;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import letsmeet.meet.Meet;

import java.util.logging.Logger;

/**
 * Created by Sergei Fladung
 */
	@MessageDriven (
			activationConfig = {
					@ActivationConfigProperty(
							propertyName = "destination", propertyValue = "java:/jms/queue/Queue1"),
					@ActivationConfigProperty(
							propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
					@ActivationConfigProperty(
							propertyName = "messageSelector", propertyValue = "DocType LIKE 'Name'")
			})
public class Statistic implements MessageListener{
		
		private static final Logger log = Logger.getLogger( Statistic.class.getName() );
		
		@Override
	public void onMessage(Message message) {
		try{
			ObjectMessage msg = (ObjectMessage) message;
			Meet meet = (Meet) msg.getObject();	
			log.info("Meet successfully deliverered via JMS");
		}catch(JMSException ex){
			throw new EJBException(ex);
		}
	}
	
		
		
}
	