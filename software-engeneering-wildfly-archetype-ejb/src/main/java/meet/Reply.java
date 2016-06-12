package meet;

import user.User;
import user.UserPersist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A reply to a conversation
 * Created by Christian on 12.06.2016.
 */
@Entity
public class Reply implements Serializable,UserContent {
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

    @Override
    public UserPersist getPostedBy() {
        return poster;
    }

    @Override
    public void setPostedBy(UserPersist name) {
        poster = name;
    }

    @Override
    public Date getTimestamp() {
        return postedAt;
    }

    @Override
    public void setTimestamp(Date date) {
        postedAt = date;
    }

    @Override
    public String getText() {
        return content;
    }

    @Override
    public void setText(String text) {
        content = text;
    }

    /**
     * "Deletes" the reply
     */
    public void delete() {
        setText("This has been deleted");
    }
}
