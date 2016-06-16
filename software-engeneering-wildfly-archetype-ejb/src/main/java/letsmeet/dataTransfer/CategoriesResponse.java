package letsmeet.dataTransfer;

import letsmeet.meet.Category;
import letsmeet.session.Session;

/**
 * A response that delivers an array of categories
 * (actually on the client they are just strings)
 * Created by Christian on 20.05.2016.
 */
public class CategoriesResponse extends SessionResponse {

	private static final long serialVersionUID = -6697756429135031290L;
	
	protected String[] categories;

    /**
     * Dummy constructor if there is no session
     */
    public CategoriesResponse(){
    super();
    }

    /**
     * The success constructor
     * @param session the current session
     * @param categoryObjects the array of Category
     */
    public CategoriesResponse(Session session, Category[] categoryObjects) {
        super(session);
        categories = new String[categoryObjects.length];
        int index = 0;
        for(Category category : categoryObjects) {
            categories[index] = category.getTitle();
            index++;
        }
    }
    public String[] getCategories() {return categories;}
    public void setCategories(String[] newCategories) {categories = newCategories;}
}
