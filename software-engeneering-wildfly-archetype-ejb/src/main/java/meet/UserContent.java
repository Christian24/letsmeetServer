package meet;

import user.UserPersist;

import java.util.Date;

/**
 * Created by Christian on 12.06.2016.
 */
public interface UserContent {
   UserPersist getPostedBy();

    /**
     * Sets who posted this
     * @param name
     */
  void setPostedBy(UserPersist name);

    /**
     * Gets the date this was posted
     * @return
     */
    Date getTimestamp();

    /**
     * Sets the time this was posted
     * @param date
     */
   void setTimestamp(Date date);

    /**
     * Gets the content
     * @return
     */
    String getText();

    /**
     * Sets the content
     * @param text
     */
    void setText(String text);

}
