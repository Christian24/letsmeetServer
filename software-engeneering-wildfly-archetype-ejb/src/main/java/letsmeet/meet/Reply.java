package letsmeet.meet;

import javax.persistence.*;

import letsmeet.user.User;

import java.util.Date;

/**
 * A reply to a conversation
 * Created by Christian on 12.06.2016.
 */
@Entity
public class Reply extends UserContent {

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
