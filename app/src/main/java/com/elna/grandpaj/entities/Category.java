package com.elna.grandpaj.entities;

/**
 * Created by amitm on 27/12/17.
 */

public class Category {

    private Integer id;
    private final String tableLink;
    private final Integer sectionLink;

    public Category(Integer id, String tableLink, Integer sectionLink) {
        this.id = id;
        this.tableLink = tableLink;
        this.sectionLink = sectionLink;
    }

    public Integer getId() {
        return id;
    }

    public String getTableLink() {
        return tableLink;
    }

    public Integer getSectionLink() {
        return sectionLink;
    }
}
