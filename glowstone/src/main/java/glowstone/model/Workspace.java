package glowstone.model;

import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private int id;
    private static int t_id; // remove later
    public String name;
    public int order; //for display order on the column tab
    public List<Category> categories;

    public Workspace(String name){
        this.name = name;
        this.id = t_id ++; // this should later take id from created db table
        categories = new ArrayList<Category>();
    }

    void createCategory(Category category){
        category.setParentId(this.id);
        categories.add(category);
    }
    void removeCategory(Category category){
        categories.remove(category);
    }

}
