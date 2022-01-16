package dto;

import java.io.Serializable;

public class Picture implements Serializable {
    private int id;
    private String title;
    private String description;
    private byte[] image;

    public Picture(int id, String title, String description, byte[] image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getDescription() {
        return this.description;
    }
    public byte[] getImage() {
        return this.image;
    }
}