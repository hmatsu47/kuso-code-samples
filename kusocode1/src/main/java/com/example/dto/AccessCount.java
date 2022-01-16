package dto;

import java.io.Serializable;

public class AccessCount implements Serializable {
    private int id;
    private int pictureId;
    private int accessCount;

    public AccessCount(int id, int pictureId, int accessCount) {
        this.id = id;
        this.pictureId = pictureId;
        this.accessCount = accessCount;
    }

    public int getId() {
        return this.id;
    }
    public int getPictureId() {
        return this.pictureId;
    }
    public int getAccessCount() {
        return this.accessCount;
    }
    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }
}