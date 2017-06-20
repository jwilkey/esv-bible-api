package com.tentkeep.esv.bible.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VerseFootnote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String key;
    private String text;

    public VerseFootnote() {}

    public VerseFootnote(String key, String text) {
        this.key = key;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getKey() {
        return key;
    }
}
