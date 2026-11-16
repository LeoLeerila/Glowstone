package glowstone.model;

public class Thumbnail {
    private final int id;
    private final byte[] data;

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
