package letsmeet.web;

import javax.jms.*;

import letsmeet.meet.Meet;
import letsmeet.user.User;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Created by Sergei Fladung
 * Session Bean implementation class letsmeetStatisticsBean
 **/

@Stateless
@LocalBean
public class LetsmeetStatisticsBean {
	
	@Resource(mappedName="java:/JmsXA")
	private ConnectionFactory connectionFactory;
	
	@Resource(mappedName="java:/jms/queue/Queue3")
	private Queue outputQueue;
	
	/**
	 * Sends an Message via JMS with user-name to the output queue, in 
	 * order to display statistic-numbers regarding user behaviour.
	 * @param name
	 */
	public void newMeetStatistics(Meet meet) {

		try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
			ObjectMessage message = context.createObjectMessage();
			message.setObject(meet);
			context.createProducer().send(outputQueue, message);
		} catch (JMSException e) {
			throw new EJBException(e);
		}
	}
}
