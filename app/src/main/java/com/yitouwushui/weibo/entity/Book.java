package com.yitouwushui.weibo.entity;

import com.orm.SugarRecord;

public class Book extends SugarRecord {
    String title;
    String edition;

    public Book() {

    }

    public Book(String edition, String title) {
        this.edition = edition;
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "edition='" + edition + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
