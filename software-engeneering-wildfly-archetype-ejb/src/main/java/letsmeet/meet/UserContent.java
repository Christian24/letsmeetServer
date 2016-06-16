package letsmeet.meet;

import letsmeet.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The baseclass for Conversation and Reply
 * Created by Christian on 12.06.2016.
 */
@Entity
@Inheritance
public abstract class UserContent implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String poster;
    protected Date postedAt;
    protected String content;
   @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected int id;

    public UserContent() {

    }
    public UserContent(User user,String text) {
        setPoster(user.getUserName());
        setContent(text);
        setPostedAt(new Date());
    }

    /**
     * Who posted this
     * @return
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Set who posted this
     * @param name
     */
    public void setPoster(String name) {
        poster = name;
    }

    /**
     * Get when this was posted
     * @return
     */
    public Date getPostedAt() {
        return postedAt;
    }

    /**
     * Set when it was posted
     * @param date
     */
    public void setPostedAt(Date date) {
        postedAt = date;
    }

    /**
     * Get the actual text content
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the actual text content
     * @param text
     */
    public void setContent(String text) {
        content = text;
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


}
