package letsmeet.web;

import javax.jms.*;

import letsmeet.dataTransfer.MeetResponse;
import letsmeet.meet.Meet;
import letsmeet.user.User;

import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class letsmeetStatisticsBean
 * @author Sergei Fladung, Julian Handrup
 **/
@Stateless
@LocalBean
public class LetsmeetStatisticsBean {
	
	@Resource(mappedName="java:/JmsXA")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName="java:/jms/queue/Queue1")
	private Queue outputQueue;
	
	private void send(HashMap<String, String> map) throws JMSException{
		try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
			ObjectMessage message = context.createObjectMessage();
			message.setObject(map);
			context.createProducer().send(outputQueue, message);
		} catch (JMSException e) {
			throw new EJBException(e);
		}
	}
	
	/**
	 * Sends an Message via JMS with user-name to the output queue, in 
	 * order to display statistic-numbers regarding user behaviour.
	 * @param name
	 */
	public void newMeetStatistics(Meet meet) {
		HashMap<String,String> map = new HashMap<String,String>();
    	map.put("user", meet.getAdmin().getUserName());
    	map.put("category", meet.getCategory().getTitle());
    	map.put("maxGuests",Integer.toString(meet.getMaxGuests()));
    	map.put("meetDate", meet.getDateTime().toString());
    	map.put("dateCreated", Calendar.getInstance().getTime().toString());
    	try {
			send(map);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends the user to letsmeetStatistics where he gets stored for further use.
	 * @param user User that has been created
	 */
	public void newUserStatistics(User user){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("name", user.getUserName());
		map.put("dateCreated", Calendar.getInstance().getTime().toString());
    	try {
			send(map);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends the user to letsmeetStatistics so the stored user data can be updated
	 * @param user User that has just joined a Meet
	 * @param meet MeetResponse that contains the meet that was just joined.
	 */
	public void userJoinedMeet(User user, Meet meet){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("user",user.getUserName());
		map.put("meetID", Integer.toString(meet.getId()));
    	try {
			send(map);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	
}