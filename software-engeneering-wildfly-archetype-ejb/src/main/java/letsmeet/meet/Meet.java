package letsmeet.meet;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import letsmeet.IDeletable;
import letsmeet.user.User;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores a new meet
 * @author Christian
 *
 */
@Entity
public class Meet implements Serializable, IDeletable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue @Id
    protected int id;
    @NotNull
    protected Date dateTime;
    @ManyToOne @NotNull
    protected Category category;
    protected String description;
    @OneToMany(mappedBy = "origin",cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected Set<Conversation> conversations;
    @ManyToOne(cascade = CascadeType.REMOVE) @NotNull
    protected User admin;
    @NotNull
    protected String location;
    protected int maxGuests;
    @NotNull
    protected String title;
    public Meet() {
        visitors = new HashSet<User>();
        conversations = new HashSet<Conversation>();
        maxGuests = 10;
    }
    @ManyToMany(cascade = CascadeType.REMOVE)
    protected Set<User> visitors;

    /**
     * Sets the datetime
     * @param newDateTime
     */
    public void setDateTime(Date newDateTime) {
        this.dateTime = newDateTime;
    }

    /**
     * Gets the datetime
     * @return
     */
    public Date getDateTime() {
        return this.dateTime;
    }

    /**
     * Gets the description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a description
     * @param newDescription
     */
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    /**
     * Gets the number of allowed guests
     * @return
     */
    public int getMaxGuests() {
        return maxGuests;
    }

    /**
     * Sets the max number of guests allowed for this Meet
     * @param newMaxGuests
     */
    public void setMaxGuests(int newMaxGuests) {
        maxGuests = newMaxGuests;
    }

    /**
     * Gets the location
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location
     * @param newLocation
     */
    public void setLocation(String newLocation) {
        location = newLocation;
    }

    /**
     * Gets the title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     * @param newTitle
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Gets the Category this belongs to
     * @return
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the Category
     * @param newCategory
     */
    public void setCategory(Category newCategory) {
        category = newCategory;
    }

    /**
     * Sets the visitors
     * @param users
     */
    public void setVisitors(Set<User> users) {
        visitors = users;
    }

    /**
     * Gets all Visitors
     * @return
     */
    public Set<User> getVisitors() {return visitors;}

    /**
     * Sets the User responsible for this Meet
     * @param admin
     */
    public void setAdmin(User admin) {
        this.admin = admin;
    }

    /**
     * Gets the User responsible for this Meet
     * @return
     */
    public User getAdmin() {return admin;}

    /**
     * Sets the conversations
     * @param conversations
     */
    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }
    public Set<Conversation> getConversations() {
        return conversations;
    }

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

    /**
     * The id that identifies the Meet
     * @return
     */
    public int getId() {
        return id;
    }


    @Override
    public void delete() {
        conversations.clear();
        visitors.clear();
    }
}
