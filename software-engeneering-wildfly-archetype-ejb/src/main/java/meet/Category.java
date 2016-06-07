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
 * A category used to filter meets
 */
@Entity
public class Category implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
