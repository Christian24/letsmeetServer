package meet;

import user.User;
import user.UserPersist;

import javax.persistence.*;
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

    public Reply(){}
    public Reply(Conversation conversation, User user, String text){
        parent = conversation;
        poster = user.getUserPersist();
        content = text;
        postedAt = new Date();
    }



    /**
     * "Deletes" the reply
     */
    public void delete() {
        setText("This has been deleted");
    }
}
