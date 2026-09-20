package glowstone.model;

public class Note {
    private static int t_id; //remove later
    private int parentId;
    private int id;
    public String title;
    public String content;


    public Note(String title){
        this.title = title;
    }

    public int getParentId() {
        return parentId;
    }

    public void setId(int id) {this.id = id;}

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public int getId() {
        return id;
    }
}
