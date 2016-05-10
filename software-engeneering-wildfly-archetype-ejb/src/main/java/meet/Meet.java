package meet;

import org.omg.CORBA.PUBLIC_MEMBER;
import user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christian on 03.05.2016.
 * Stores a new meet
 */
public class Meet implements Serializable {
    @GeneratedValue @Id
    protected int id;
    protected LocalDateTime dateTime;
    @ManyToOne
    protected Category category;
    protected String description;
    @ManyToOne
    protected User admin;
    protected String location;
    protected int maxGuests;
    protected String title;
    public Meet() {
        visitors = new HashSet<User>();
    }
    @ManyToMany
    protected Set<User> visitors;

    public void setDateTime(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
    }
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String newDescription) {
        description = newDescription;
    }
    public int getMaxGuests() {
        return maxGuests;
    }
    public void setMaxGuests(int newMaxGuests) {
        maxGuests = newMaxGuests;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String newLocation) {
        location = newLocation;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String newTitle) {
        title = newTitle;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category newCategory) {
        category = newCategory;
    }



}
