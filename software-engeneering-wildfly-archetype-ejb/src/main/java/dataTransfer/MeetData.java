package dataTransfer;

import meet.Category;
import meet.Meet;
import user.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Christian on 19.05.2016.
 */
public class MeetData implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected LocalDateTime dateTime;

    protected Category category;
    protected String description;

    protected UserData admin;
    protected String location;
    protected int maxGuests;
    protected String title;
    public MeetData() {
        visitors = new HashSet<UserData>();
    }

    protected Set<UserData> visitors;

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
    public void setVisitors(Set<UserData> users) {
        visitors = users;
    }
    public Set<UserData> getVisitors() {return visitors;}
    public void setAdmin(UserData admin) {
        this.admin = admin;
    }
    public UserData getAdmin() {return admin;}

    public MeetData(Meet meet) {
        this.category = meet.getCategory();
        this.title = meet.getTitle();
        this.location = meet.getLocation();
        this.description = meet.getDescription();
        this.maxGuests = meet.getMaxGuests();
        this.dateTime = meet.getDateTime();

        visitors = new HashSet<UserData>();
        for(User user : meet.getVisitors()) {
            UserData userData = new UserData(user);
            visitors.add(userData);
        }
        this.admin = new UserData(meet.getAdmin());

    }
}
