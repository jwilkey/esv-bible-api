package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.tentkeep.esv.bible.mappers.MapHelpers.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FirstThessalonians1Test {
    private static PassageQuery actual;

    @BeforeClass
    public static void beforeAll() throws Exception {
        PassageMapper subject = new PassageMapper();
        actual = subject.map(loadFixture("1thess1.txt"));
    }

    @Test
    public void topLevelProperties() {
        assertEquals("1 Thessalonians 1:1-2", actual.getPassage().getReference());
        expectedOptions(actual, false, false, false, false);
        assertEquals("The Holy Bible, English Standard Version copyright (c)2001 by Crossway Bibles, a division of Good News Publishers. Used by permission. All rights reserved. http://www.esv.org", actual.getCopyright());
    }

    @Test
    public void verseNumbers() {
        assertEquals(2, actual.getPassage().getVerses().size());
        expectedVerseNumbers(actual, 1,2);
    }

    @Test
    public void headings() {
        assertEquals("Greeting", actual.getPassage().getVerses().get(0).getHeading());
    }

    @Test
    public void texts() {
        expectedVerseTexts(actual,
                "Paul, Silvanus, and Timothy, To the church of the Thessalonians in God the Father and the Lord Jesus Christ: Grace to you and peace.",
                "We give thanks to God always for all of you, constantly mentioning you in our prayers,"
        );
    }
}
