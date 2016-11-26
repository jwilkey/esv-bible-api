package com.tentkeep.esv.bible.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class VerseOptions {
    @JsonSerialize private boolean showParagraphMarkings;
    @JsonSerialize private boolean showWordsOfChristMarkings;
    @JsonSerialize private boolean showFootnotes;
    @JsonSerialize private boolean showFormatting;

    public boolean showParagraphMarkings() {
        return showParagraphMarkings;
    }

    public boolean showWordsOfChristMarkings() {
        return showWordsOfChristMarkings;
    }

    public boolean showFootnotes() {
        return showFootnotes;
    }

    public boolean showFormatting() {
        return showFormatting;
    }
}
