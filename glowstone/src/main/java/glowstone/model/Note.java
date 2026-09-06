package glowstone.model;

public class Note {
    private int id;
    public String title;
    public String content;


    public Note(String title){
        this.title = title;
        id += 1;
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
