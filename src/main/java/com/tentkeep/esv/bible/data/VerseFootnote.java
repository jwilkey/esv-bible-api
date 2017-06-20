package com.tentkeep.esv.bible.data;

import javax.persistence.*;

@Entity
public class VerseFootnote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String key;
    @Column(length = 500)
    private String text;

    public VerseFootnote() {}

    public VerseFootnote(String key, String text) {
        this.key = key;
        this.text = text.length() > 499 ? text.substring(0, 498) : text;
    }

    public String getText() {
        return text;
    }

    public String getKey() {
        return key;
    }
}
