package letsmeet.meet;

import javax.persistence.*;

import letsmeet.user.User;
import letsmeet.user.UserPersist;

import java.util.Date;

/**
 * A reply to a conversation
 * Created by Christian on 12.06.2016.
 */
@Entity
public class Reply extends UserContent {
    @Id
    @GeneratedValue
    protected int id;
    @ManyToOne
    protected UserPersist poster;
    protected Date postedAt;
    protected String content;
    @ManyToOne
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
        parent = conversation;
        poster = user.getUserPersist();
        content = text;
        postedAt = new Date();
    }

    /**
     * Gets the id
     * @return
     */
    public int getId(){
    return id;
}

    /**
     * Sets the id
     * @param newId
     */
    public void setId(int newId){
        id = newId;
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
        setText("This has been deleted");
    }
}
