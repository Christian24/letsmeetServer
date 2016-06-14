package meet;

import user.UserPersist;


import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * The baseclass for Conversation and Reply
 * Created by Christian on 12.06.2016.
 */
public abstract class UserContent {

    @ManyToOne
    protected UserPersist poster;
    protected Date postedAt;
    protected String content;

    /**
     * Who posted this
     * @return
     */
    public UserPersist getPostedBy() {
        return poster;
    }

    /**
     * Set who posted this
     * @param name
     */
    public void setPostedBy(UserPersist name) {
        poster = name;
    }

    /**
     * Get when this was posted
     * @return
     */
    public Date getTimestamp() {
        return postedAt;
    }

    /**
     * Set when it was posted
     * @param date
     */
    public void setTimestamp(Date date) {
        postedAt = date;
    }

    /**
     * Get the actual text content
     * @return
     */
    public String getText() {
        return content;
    }

    /**
     * Set the actual text content
     * @param text
     */
    public void setText(String text) {
        content = text;
    }


}
