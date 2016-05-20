package dataTransfer;

import meet.Category;
import session.Session;

/**
 * Created by Christian on 20.05.2016.
 */
public class CategoriesResponse extends SessionResponse {
    protected String[] categories;

    /**
     * Dummy constructor if there is no session
     */
    public CategoriesResponse(){
    super();
    }
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
