package letsmeet.meet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A category used to filter meets
 * @author Christian
 */
@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    protected String title;
    @OneToMany(mappedBy = "category")
    protected Set<Meet> meets;

    /**
     * Dummy constructor
     */
    public Category() {
        meets = new HashSet<Meet>();
    }

    /**
     * Short to create a new instance under the given name
     * @param name
     */
    public Category(String name){
        meets = new HashSet<>();
        setTitle(name);
    }
    
    /**
     * Gets the title of the category
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the new title
     * @param newTitle
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Gets all Meets for this category
     * @return
     */
    public Meet[] getMeets() {
        Meet[] meetsOutput = new Meet[meets.size()];
        meets.toArray(meetsOutput);
        return  meetsOutput;
    }
}
