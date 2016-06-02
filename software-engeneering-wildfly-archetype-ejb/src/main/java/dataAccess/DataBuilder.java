package dataAccess;

import meet.Category;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created by Christian on 30.05.2016.
 */
@Singleton
@Startup
public class DataBuilder {
    protected final String[] categories = {"Sport", "Kultur", "Essen & Trinken", "Feiern", "Kennenlernen"};
    @EJB
    protected DataAccessObject dataAccessObject;
    @PostConstruct
    private void createTestData() {
    createCategories();
}
    /**
     * Creates our categories
     */
    private void createCategories() {
        for(String category : categories) {
            createCategory(category);
        }
    }
    private void createCategory(String name) {
        Category category = dataAccessObject.findCategoryById(name);
        if(category != null) {
            Category newCategory = new Category();
            newCategory.setTitle(name);
            dataAccessObject.persist(newCategory);
        }
    }

}
