package letsmeet.meet;

import javax.persistence.*;

import letsmeet.user.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A Conversation is a UserContent Instance that is the parent to many
 * Reply instances
 * Created by Christian on 12.06.2016.
 */
@Entity
public class Conversation extends UserContent {

	private static final long serialVersionUID = 5306229874557709687L;
	
	@OneToMany(mappedBy = "parent")
    protected Set<Reply> replies;
    @ManyToOne
    protected Meet origin;

    /**
     * Dummy constructor
     */
    public Conversation() {
        replies = new HashSet<Reply>();
    }

    /**
     * Constructor to create a new conversation instance
     * @param user
     * @param text
     * @param meet
     */
    public Conversation(User user, String text, Meet meet) {
        super(user,text);
        this.origin = meet;

    }


    /**
     * Sets the Meet this belongs to
     * @param meet
     */
    public void setOrigin(Meet meet) {
        origin = meet;
    }

    /**
     * Gets the Meet this belongs to
     * @return
     */
    public Meet getOrigin() {
       return origin;
    }

    /**
     * Gets all the Replies
     * @return
     */
    public Set<Reply> getReplies(){
        return replies;
    }

    /**
     * Sets the Replies
     * @param newReplies
     */
    public void setReplies(Set<Reply> newReplies) {
        replies = newReplies;
    }

}
