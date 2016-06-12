package meet;

import user.User;


import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christian on 12.06.2016.
 */
@Entity
public class Conversation extends UserContent {
    @Id
    @GeneratedValue
    protected int id;
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

}
