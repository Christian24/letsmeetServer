package meet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christian on 03.05.2016.
 */
@Entity
public class Category implements Serializable {
    @Id
    protected String title;
    @OneToMany(mappedBy = "category")
    protected Set<Meet> meets;
    public Category() {
        meets = new HashSet<Meet>();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String newTitle) {
        title = newTitle;
    }
    public Meet[] getMeets() {
        return (Meet[]) meets.toArray();
    }

}
