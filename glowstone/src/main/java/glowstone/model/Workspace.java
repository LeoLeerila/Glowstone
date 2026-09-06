package glowstone.model;

import java.util.List;

public class Workspace {
    private int id;
    public String name;
    public int order; //for display order on the column tab
    public List<Category> categories;

    public Workspace(String name){
        this.name = name;
        id += 1;
    }
}
