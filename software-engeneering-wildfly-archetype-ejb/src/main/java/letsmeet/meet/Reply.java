package letsmeet.meet;

import javax.persistence.*;

import letsmeet.user.User;

/**
 * A reply to a conversation
 * @author Christian
 */
@Entity
public class Reply extends UserContent {

	private static final long serialVersionUID = -5778681781175253550L;

	@ManyToOne(cascade = CascadeType.REMOVE)
    protected Conversation parent;

    /**
     * Dummy constructor
     */
    public Reply(){}

    /**
     * Constructs a new Reply
     * @param conversation
     * @param user
     * @param text
     */
    public Reply(Conversation conversation, User user, String text){
        super(user,text);
        parent = conversation;

    }



    /**
     * Sets the Conversation this belongs to
     * @param newParent
     */
    public void setParent(Conversation newParent){
        parent = newParent;
    }

    /**
     * Gets the conversation this belongs to
     * @return
     */
    public Conversation getParent(){
        return parent;
    }


    /**
     * "Deletes" the reply
     */
    public void delete() {
        setContent("This has been deleted");
    }
}
