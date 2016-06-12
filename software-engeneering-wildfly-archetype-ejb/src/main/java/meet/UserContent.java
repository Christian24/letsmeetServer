package meet;

import user.UserPersist;


import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Christian on 12.06.2016.
 */
public class UserContent {

    @ManyToOne
    protected UserPersist poster;
    protected Date postedAt;
    protected String content;

    public UserPersist getPostedBy() {
        return poster;
    }


    public void setPostedBy(UserPersist name) {
        poster = name;
    }


    public Date getTimestamp() {
        return postedAt;
    }


    public void setTimestamp(Date date) {
        postedAt = date;
    }


    public String getText() {
        return content;
    }


    public void setText(String text) {
        content = text;
    }


}
