package glowstone.model;

public class Thumbnail {
    private int id;
    private byte[] data;

    public Thumbnail(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public int getId() {
        return id;
    }
}
