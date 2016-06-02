package meet;


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
@Entity
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

    /**
     * Makes a new user join
     * @param newUser
     * @return returns true if maxVisitors is not reached and newUser is not part of meet already
     */
    public boolean join(User newUser) {

        if(visitors.size() < getMaxGuests() || !alreadyIn(newUser)) {
            visitors.add(newUser);
            return true;
        }
        return false;
    }

    /**
     * Checks if a user is already visitor or admin for a meet
     * @param check
     * @return
     */
    public boolean alreadyIn(User check) {
        if(getAdmin().equals(check) || visitors.contains(check))
            return true;

        return false;
    }

    /**
     * Makes a user leave
     * @param wantsToLeave
     * @return returns true if user is not admin
     */
    public boolean leave(User wantsToLeave) {

        if(wantsToLeave.equals(getAdmin()))
        return false;
        else {
           return visitors.remove(wantsToLeave);
        }
    }



}
