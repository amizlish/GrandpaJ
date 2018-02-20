package com.elna.grandpaj.entities;

/**
 * Created by amitm on 20/02/18.
 */

public class Chapter {
    private String name;
    private final Integer id;

    public Chapter(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}



