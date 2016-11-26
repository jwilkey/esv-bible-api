package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class PassageMapperTest {

    @InjectMocks PassageMapper subject;

    @Test
    public void map() throws Exception {
        PassageQuery actual = subject.map(loadFixture("matt9.xml"));

        assertEquals("Matthew 9:1-4", actual.getPassage().getReference());
        assertEquals(4, actual.getPassage().getVerses().size());
        List<PassageQuery.Passage.VerseUnit> verses = actual.getPassage().getVerses();
        assertEquals(1, verses.get(0).getNumber());
        assertEquals(2, verses.get(1).getNumber());
        assertEquals(3, verses.get(2).getNumber());
        assertEquals(4, verses.get(3).getNumber());
        assertEquals("And getting into a boat he crossed over and came to his own city.", verses.get(0).getText());
        assertEquals("And behold, some people brought to him a paralytic, lying on a bed. And when Jesus saw their faith, he said to the paralytic, \"Take heart, my son; your sins are forgiven.\"", verses.get(1).getText());
        assertEquals("And behold, some of the scribes said to themselves, \"This man is blaspheming.\"", verses.get(2).getText());
        assertEquals("But Jesus, knowing their thoughts, said, \"Why do you think evil in your hearts?", verses.get(3).getText());
        assertEquals("Jesus Heals a Paralytic", verses.get(0).getHeading());

        assertFalse(actual.showParagraphMarkings());
        assertFalse(actual.showWordsOfChristMarkings());
        assertFalse(actual.showFootnotes());
        assertFalse(actual.showFormatting());
    }

    private String loadFixture(String path) throws IOException, URISyntaxException {
        ClassPathResource resource = new ClassPathResource("fixtures/" + path);
        java.util.Scanner scanner = new java.util.Scanner(resource.getInputStream())
                .useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}