package com.tentkeep.esv.bible.helpers;

import com.tentkeep.esv.bible.data.Verse;
import com.tentkeep.esv.bible.data.VerseFootnote;
import com.tentkeep.esv.bible.data.VerseFootnoteRepository;
import com.tentkeep.esv.bible.data.VerseRepository;
import com.tentkeep.esv.bible.mappers.PassageMapper;
import com.tentkeep.esv.bible.models.PassageQuery;
import com.tentkeep.esv.bible.services.ServiceProvider;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EsvHelper {
    @Autowired private ServiceProvider serviceProvider;
    @Autowired private PassageMapper passageMapper;
    @Autowired private BibleHelper bibleHelper;
    @Autowired private VerseRepository verseRepository;
    @Autowired private VerseFootnoteRepository verseFootnoteRepository;

    public PassageQuery fetch(String esvApiKey, String query) throws Exception {
        Response response = serviceProvider.getEsvService().search(esvApiKey, query, "crossway-xml-1.0").execute();
        ResponseBody responseBody = (ResponseBody) response.body();
        String responseBodyString = new String(responseBody.bytes());

        PassageQuery passageQuery = passageMapper.map(responseBodyString);

        persist(passageQuery);

        return passageQuery;
    }

    public Iterable<Verse> fetch(String esvApiKey, String book, int chapter) throws Exception {
        Iterable<Verse> verses = verseRepository.findByBookAndChapter(book.toUpperCase(), chapter);

        if (verses.iterator().hasNext() == false) {
            return toVerses(this.fetch(esvApiKey, book + chapter));
        } else if (verses.iterator().next().getExpiration().before(new Date())) {
            verses.forEach(v -> verseFootnoteRepository.delete(v.getFootnotes()));
            verseRepository.delete(verses);
            return toVerses(this.fetch(esvApiKey, book + chapter));
        }

        return verses;
    }

    private void persist(PassageQuery passageQuery) {
        List<Verse> verses = toVerses(passageQuery);
        List<VerseFootnote> footnotes = new ArrayList<>();
        verses.forEach(v -> footnotes.addAll(v.getFootnotes()));
        verseFootnoteRepository.save(footnotes);
        verseRepository.save(verses);
    }

    private List<Verse> toVerses(PassageQuery passageQuery) {
        String book = bibleHelper.bookFromReference(passageQuery.getPassage().getReference());
        return passageQuery.getPassage().getVerses().stream()
                    .map(v -> new Verse(book, v))
                    .collect(Collectors.toList());
    }
}
