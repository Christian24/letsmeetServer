package meet;

import user.User;
import user.UserPersist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christian on 12.06.2016.
 */
@Entity
public class Conversation implements Serializable,UserContent {
    @Id @GeneratedValue
    protected int id;
    @ManyToOne
    protected UserPersist poster;
    protected Date postedAt;
    protected String content;
    @OneToMany(mappedBy = "parent")
    protected Set<Reply> replies;
    @ManyToOne
    protected Meet origin;
    public Conversation() {
        replies = new HashSet<Reply>();
    }
    public Conversation(User user, String text, Meet meet) {
        this.poster= user.getUserPersist();
        this.content = text;
        this.origin = meet;
        this.setTimestamp(new Date());
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
}
