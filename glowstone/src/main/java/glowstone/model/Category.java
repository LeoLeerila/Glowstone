package glowstone.model;

import java.util.List;

public class Category {
    private int id;
    public String name;
    public List<Note> notes;

    public Category(String name){
        this.name = name;
        id += 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNotes(Note newNote) {
        notes.add(newNote);
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
