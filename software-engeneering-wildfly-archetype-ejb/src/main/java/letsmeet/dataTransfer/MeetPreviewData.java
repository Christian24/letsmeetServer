package letsmeet.dataTransfer;

import letsmeet.meet.Meet;

import java.util.Date;

/**
 * A preview object used on the client to minimize data transfer
 * @author Christian
 */
public class MeetPreviewData extends DataTransferObject {
    private static final long serialVersionUID = 8836148056420838703L;

    protected int id;
    protected Date dateTime;

    protected String category;
    protected String description;
    protected String location;
    protected int maxGuests;
    protected String title;
    protected int currentGuests;
    protected String admin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCurrentGuests() {
        return currentGuests;
    }

    public void setCurrentGuests(int currentGuests) {
        this.currentGuests = currentGuests;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public MeetPreviewData() {

}

    /**
     * Creates a new preview from the given Meet
     * @param meet
     */
    public MeetPreviewData(Meet meet) {
        setId(meet.getId());
        setDescription(meet.getDescription());
        setDateTime(meet.getDateTime());
        setTitle(meet.getTitle());
        setLocation(meet.getLocation());
        setCurrentGuests(1 + meet.getVisitors().size());
        setAdmin(meet.getAdmin().getUserName());
        setMaxGuests(meet.getMaxGuests());
        setCategory(meet.getCategory().getTitle());
    }
}
