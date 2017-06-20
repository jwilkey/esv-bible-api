package com.tentkeep.esv.bible.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tentkeep.esv.bible.models.PassageQuery;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Verse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "verse_id")
    private long id;
    private String book;
    private Integer chapter;
    private Integer number;
    @Column(length = 550)
    private String text;
    private String heading;
    private String subheading;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<VerseFootnote> footnotes;
    @JsonIgnore
    private Date expiration;

    public Verse() {}

    public Verse(String book, PassageQuery.Passage.VerseUnit verseUnit) {
        this.book = book;
        this.chapter = verseUnit.getChapter();
        this.number = verseUnit.getNumber();
        this.text = verseUnit.getText();
        this.heading = verseUnit.getHeading();
        this.subheading = verseUnit.getSubheading();
        this.footnotes = verseUnit.getFootnotes().stream()
                .map(f -> new VerseFootnote(f.getId(), f.getText()))
                .collect(Collectors.toList());
        this.expiration = generateExpiry();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHeading() {
        return heading;
    }

    public String getSubheading() {
        return subheading;
    }

    public List<VerseFootnote> getFootnotes() {
        return footnotes;
    }

    private Date generateExpiry() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 14);
        return c.getTime();
    }

    public Date getExpiration() {
        return expiration;
    }
}
