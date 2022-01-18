package com.example.model;

import java.io.Serializable;

public class AccessCount implements Serializable {
    private int pictureId;
    private int accessCount;

    public AccessCount(int pictureId, int accessCount) {
        this.pictureId = pictureId;
        this.accessCount = accessCount;
    }

    public int getPictureId() {
        return this.pictureId;
    }
    public int getAccessCount() {
        return this.accessCount;
    }
}
