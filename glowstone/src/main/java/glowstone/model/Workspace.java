package glowstone.model;

import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private int id;
    private static int t_id; // remove later
    public String name;
    public List<Category> categories;

    public Workspace(String name){
        this.name = name;
        this.id = t_id ++; // this should later take id from created db table
        categories = new ArrayList<Category>();
    }

    public void createCategory(Category category){
        category.setParentId(this.id);
        categories.add(category);
    }
    public List<Category> getCategories(){
        return categories;
    }
    public void removeCategory(Category category){
        categories.removeIf(c -> c.getId() == category.getId());
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
//    public void addCategoryToTab(Category category){
//        for (Category c : categories) {
//            if (!(c.getId() == category.getId())) {
//                categories.add(category);
//            }
//        }
//    }
}
