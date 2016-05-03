import java.io.Serializable;

/**
 * Created by Christian on 03.05.2016.
 */
public class CategoryBean extends Serializable {
    protected String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String newTitle) {
        title = newTitle;
    }
}
