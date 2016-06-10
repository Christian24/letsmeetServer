package web;

import javax.jms.*;
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
public class letsmeetStatisticsBean {
	
	@Resource(mappedName="java:/JmsXA")
	private static ConnectionFactory connectionFactory;
	
	@Resource(mappedName="java:/jms/queue/ExpiryQueue")
	private static Queue outputQueue;
	
	/**
	 * Sends an Message via JMS with user-name to the output queue, in 
	 * order to display statistic-numbers regarding user behaviour.
	 * @param name
	 */
	public static void displayStatistics(String name) {
		/**try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
			TextMessage message = context.createTextMessage();
			message.setStringProperty("DocType", "Name");
			message.setText(name);
			context.createProducer().send(outputQueue, message);
		} catch (JMSException e) {
			throw new EJBException(e);
		}**/
	}
}
