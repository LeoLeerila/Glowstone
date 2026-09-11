package glowstone.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private static int t_id; //remove later
    private int parentId;
    private int id;
    public String name;
    public List<Note> notes;

    public Category(String name){
        this.name = name;
        this.id = t_id ++;// this should later take id from created db table
        notes = new ArrayList<Note>();
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNotes(Note note) {
        note.setParentId(this.id);
        notes.add(note);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public List<Note> getNotes() {
        return notes;
    }

}
