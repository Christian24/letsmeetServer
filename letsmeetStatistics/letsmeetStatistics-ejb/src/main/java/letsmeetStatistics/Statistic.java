package letsmeetStatistics;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import meet.Meet;


/**
 * Created by Sergei Fladung
 */
	@MessageDriven (
			activationConfig = {
					@ActivationConfigProperty(
							propertyName = "destination", propertyValue = "java:/jms/queue/letsmeetStatistics"),
					@ActivationConfigProperty(
							propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
					@ActivationConfigProperty(
							propertyName = "messageSelector", propertyValue = "DocType LIKE 'Name'")
			})
public class Statistic implements MessageListener{
		@Override
	public void onMessage(Message message) {
		try{
			ObjectMessage msg = (ObjectMessage) message;
			Meet meet = (Meet) msg.getObject();
			
			
		}catch(JMSException ex){
			throw new EJBException(ex);
		}
	}
	
		
		
}
	