package com.example.dto;

import java.io.Serializable;

public class CountLog implements Serializable {
    private int id;
    private int pictureId;
    private int accessCount;

    public CountLog(int id, int pictureId) {
        this.id = id;
        this.pictureId = pictureId;
    }

    public int id() {
        return this.id;
    }
    public int getPictureId() {
        return this.pictureId;
    }
    public int getAccessCount() {
        return this.accessCount;
    }
}