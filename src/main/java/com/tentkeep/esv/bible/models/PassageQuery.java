package com.tentkeep.esv.bible.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import java.util.ArrayList;
import java.util.List;

@Root(name = "crossway-bible", strict = false)
public class PassageQuery {

    @Element(name = "passage")
    private Passage passage;

    @Element(name = "copyright")
    private String copyright;

    private VerseOptions options;

    public Passage getPassage() {
        return passage;
    }

    public String getCopyright() {
        return copyright;
    }

    public VerseOptions getOptions() {
        return options;
    }

    public void setOptions(VerseOptions options) {
        this.options = options;
    }

    @Root(strict = false)
    public static class Passage {

        @Element(name = "reference")
        private String reference;

        @ElementList(name = "content")
        private List<VerseUnit> verses;

        public String getReference() {
            return reference;
        }

        public List<VerseUnit> getVerses() {
            return verses;
        }

        @Root(name = "verse-unit", strict = false)
        @Convert(VerseUnitConverter.class)
        public static class VerseUnit {
            private int number;
            private String text;
            private String heading;
            private String subheading;
            private List<Footnote> footnotes;

            public VerseUnit() {
                this.footnotes = new ArrayList<>();
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
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

            public void setHeading(String heading) {
                this.heading = heading;
            }

            public String getSubheading() {
                return subheading;
            }

            public List<Footnote> getFootnotes() {
                return this.footnotes;
            }

            public void setSubheading(String subheading) {
                this.subheading = subheading;
            }

            public static class Footnote {
                private String text;
                private String id;

                public Footnote(String id, String text) {
                    this.id = id;
                    this.text = text;
                }

                public String getText() {
                    return text;
                }

                public String getId() {
                    return id;
                }
            }
        }
    }
}
