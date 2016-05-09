package meet;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Christian on 03.05.2016.
 * Stores a new meet
 */
public class Meet implements Serializable {
    @GeneratedValue @Id
    protected int id;
    protected LocalDateTime dateTime;
    protected Category category;
    protected String description;
    protected String location;
    protected int maxGuests;
    protected String title;

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
