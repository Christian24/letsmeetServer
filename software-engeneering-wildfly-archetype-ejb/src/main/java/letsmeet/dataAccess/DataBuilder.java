package letsmeet.dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import letsmeet.meet.Category;
import letsmeet.meet.Conversation;
import letsmeet.meet.Meet;
import letsmeet.meet.Reply;
import letsmeet.user.User;
import letsmeet.web.OnlineIntegration;

/**
 * Creates sample data that is useful on every server startup (e.g when database
 * was dropped
 * @author Christian
 *
 */
@Singleton
@Startup
public class DataBuilder {
	protected final String[] categories = { "Sport", "Kultur", "Essen & Trinken", "Feiern", "Kennenlernen" };
	private static final Logger log = Logger.getLogger(OnlineIntegration.class.getName());
	@EJB
	protected DataAccessObject dataAccessObject;

	@PostConstruct
	private void createTestData() {
		createCategories();
		createUser();
        createUser2();
		createMeet();
	}

	/**
	 * Creates a test admin
	 */
	private void createUser() {
		User user = new User();
		user.setDescription("Master of Disaster");
		user.setPassword("WebWemser");
		user.setUserName("admin");
		dataAccessObject.persist(user);
		log.info("Test User created");
	}
    /**
     * Creates a test admin
     */
    private void createUser2() {
        User user = new User();
        user.setDescription("Mistress of Disaster");
        user.setPassword("WebWemser");
        user.setUserName("Charlotte");
        dataAccessObject.persist(user);
        log.info("Test User created");
    }

	/**
	 * Creates a sample Meet
	 */
	private void createMeet() {
		Meet meet = new Meet();
		Category category = dataAccessObject.findCategoryById("Feiern");
		User user = dataAccessObject.findUserByName("admin");
		if (category != null && user != null) {
			meet.setCategory(category);
			meet.setTitle("Einen trinken gehen");
			meet.setLocation("Haifischbar");
			meet.setAdmin(user);
			meet.setDescription("Nach der Arbeit kommt das Vergnügen.");
			// Adapated from
			// http://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
			Calendar cal = Calendar.getInstance(); // creates calendar
			cal.setTime(new Date()); // sets calendar time/date
			cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour

			meet.setDateTime(cal.getTime());
			dataAccessObject.persist(meet);
            Conversation conversation = new Conversation(user,"Gibt's auch Käse?",meet);
			dataAccessObject.persist(conversation);
            User charlotte = dataAccessObject.findUserByName("Charlotte");
            Reply reply = new Reply(conversation,charlotte,"Nein, gibt es nicht");
            dataAccessObject.persist(reply);

            log.info("Test Meet created");
		}

	}

	/**
	 * Creates our categories
	 */
	private void createCategories() {
		for (String category : categories) {
			createCategory(category);
		}
	}

	/**
	 * Creates a single category under the given name
	 * 
	 * @param name
	 */
	private void createCategory(String name) {
		Category category = dataAccessObject.findCategoryById(name);
		if (category == null) {
			Category newCategory = new Category();
			newCategory.setTitle(name);
			dataAccessObject.persist(newCategory);
			log.info("Category: " + name);
		}
	}

}
