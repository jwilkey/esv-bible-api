package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class MapHelpers {
    public static void expectedVerseNumbers(PassageQuery actual, int... numbers) {
        List<PassageQuery.Passage.VerseUnit> verses = actual.getPassage().getVerses();
        for (int i = 0; i < verses.size(); i++) {
            assertEquals(numbers[i], verses.get(i).getNumber());
        }
    }

    public static void expectedOptions(PassageQuery actual,
                                 boolean paragraphs,
                                 boolean wocMarkings,
                                 boolean footnotes,
                                 boolean formatting) {
        assertEquals(paragraphs, actual.getOptions().showParagraphMarkings());
        assertEquals(wocMarkings, actual.getOptions().showWordsOfChristMarkings());
        assertEquals(footnotes, actual.getOptions().showFootnotes());
        assertEquals(formatting, actual.getOptions().showFormatting());
    }

    public static void expectedVerseTexts(PassageQuery actual, String... expectedTexts) {
        List<String> texts = actual.getPassage().getVerses().stream()
                .map(v -> v.getText())
                .collect(Collectors.toList());
        for (int i = 0; i < texts.size(); i++) {
            assertEquals(expectedTexts[i], texts.get(i));
        }
    }

    public static String loadFixture(String path) throws IOException, URISyntaxException {
        ClassPathResource resource = new ClassPathResource("fixtures/" + path);
        java.util.Scanner scanner = new java.util.Scanner(resource.getInputStream())
                .useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
