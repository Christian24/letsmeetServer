package letsmeet.meet;

import javax.persistence.*;

import letsmeet.IDeletable;
import letsmeet.user.User;

import java.util.HashSet;
import java.util.Set;

/**
 * A Conversation is a UserContent Instance that is the parent to many
 * Reply instances
 * @author Christian
 *
 */
@Entity
public class Conversation extends UserContent implements IDeletable {

	private static final long serialVersionUID = 5306229874557709687L;
	
	@OneToMany(mappedBy = "parent",cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected Set<Reply> replies;
    @ManyToOne(cascade = CascadeType.REMOVE)
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
        replies = new HashSet<Reply>();
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

    @Override
    public void delete() {
        replies.clear();
    }
}
