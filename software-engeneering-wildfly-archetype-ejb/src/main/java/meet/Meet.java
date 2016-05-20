package meet;

import org.omg.CORBA.PUBLIC_MEMBER;
import user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christian on 03.05.2016.
 * Stores a new meet
 */
public class Meet implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue @Id
    protected int id;
    protected Date dateTime;
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

    public void setDateTime(Date newDateTime) {
        this.dateTime = newDateTime;
    }
    public Date getDateTime() {
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
    public void setVisitors(Set<User> users) {
        visitors = users;
    }
    public Set<User> getVisitors() {return visitors;}
    public void setAdmin(User admin) {
        this.admin = admin;
    }
    public User getAdmin() {return admin;}



}
