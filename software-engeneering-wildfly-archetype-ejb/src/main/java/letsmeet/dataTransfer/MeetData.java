package letsmeet.dataTransfer;

import letsmeet.helpers.ServerHelper;
import letsmeet.meet.Conversation;
import letsmeet.meet.Meet;
import letsmeet.user.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * The DataTransferObject for Meet
 * Created by Christian on 19.05.2016.
 */
public class MeetData extends DataTransferObject {


    protected int id;
    protected long dateTime;

    protected String category;
    protected String description;

    protected Set<ConversationData> conversations;

    protected UserData admin;
    protected String location;
    protected int maxGuests;
    protected String title;
    public MeetData() {
        visitors = new HashSet<UserData>();
        conversations = new HashSet<ConversationData>();
    }

    protected Set<UserData> visitors;

    public void setDateTime(long newDateTime) {
        this.dateTime = newDateTime;
    }
    public long getDateTime() {
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
    public Set<ConversationData> getConversations() {return conversations;}
    public void setConversations(Set<ConversationData> newConversations) {
        conversations = newConversations;
    }
    public int getId(){return id;}
    public void setId(int newId){id = newId;}
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

    /**
     * Creates a instance based on an actual Meet
     * @param meet
     */
    public MeetData(Meet meet) {
        this.category = meet.getCategory().getTitle();
        this.title = meet.getTitle();
        this.location = meet.getLocation();
        this.description = meet.getDescription();
        this.maxGuests = meet.getMaxGuests();
        this.dateTime = ServerHelper.getUnixTimestamp(meet.getDateTime());
        this.id = meet.getId();
        visitors = new HashSet<UserData>();
        conversations  = new HashSet<ConversationData>();
        for(User user : meet.getVisitors()) {
            UserData userData = new UserData(user);
            visitors.add(userData);
        }
        for(Conversation conversation : meet.getConversations()){
            conversations.add(new ConversationData(conversation));
        }
        this.admin = new UserData(meet.getAdmin());

    }
    /**
     * Checks if a user is already visitor or admin for a meet
     * @param check
     * @return
     */
    public boolean alreadyIn(UserData check) {
        if(getAdmin().equals(check) || visitors.contains(check))
            return true;

        return false;
    }
}
