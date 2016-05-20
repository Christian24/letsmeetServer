package dataTransfer;

import meet.Category;
import meet.Meet;
import user.User;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Christian on 19.05.2016.
 */
public class MeetData implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected Date dateTime;

    protected String category;
    protected String description;

    protected UserData admin;
    protected String location;
    protected int maxGuests;
    protected String title;
    public MeetData() {
        visitors = new HashSet<UserData>();
    }

    protected Set<UserData> visitors;

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
    public String getCategory() {
        return category;
    }

    public void setCategory(String newCategory) {
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
        this.category = meet.getCategory().getTitle();
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
