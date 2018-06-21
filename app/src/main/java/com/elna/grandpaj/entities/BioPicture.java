package com.elna.grandpaj.entities;

/**
 * Created by amitm on 21/02/18.
 */

public class BioPicture {
    private byte[] image;
    private String title;
    private int id;

    public BioPicture() {};

    public BioPicture(int id, String title, byte[] image) {
        this.image = image;
        this.title = title;
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setID(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
