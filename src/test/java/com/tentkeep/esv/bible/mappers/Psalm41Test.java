package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.tentkeep.esv.bible.mappers.MapHelpers.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class Psalm41Test {
    private static PassageQuery actual;

    @BeforeClass
    public static void beforeAll() throws Exception {
        PassageMapper subject = new PassageMapper();
        actual = subject.map(loadFixture("psalm41.txt"));
    }

    @Test
    public void topLevelProperties() {
        assertEquals("Psalm 41", actual.getPassage().getReference());
        expectedOptions(actual, false, false, false, false);
        assertEquals("The Holy Bible, English Standard Version copyright (c)2001 by Crossway Bibles, a division of Good News Publishers. Used by permission. All rights reserved. http://www.esv.org", actual.getCopyright());
    }

    @Test
    public void verseNumbers() {
        assertEquals(13, actual.getPassage().getVerses().size());
        expectedVerseNumbers(actual, 1,2,3,4,5,6,7,8,9,10,11,12,13);
    }

    @Test
    public void headings() {
        assertEquals("O LORD, Be Gracious to Me", actual.getPassage().getVerses().get(0).getHeading());
        assertEquals("To the choirmaster. A Psalm of David.", actual.getPassage().getVerses().get(0).getSubheading());
    }

    @Test
    public void texts() {
        expectedVerseTexts(actual,
                "Blessed is the one who considers the poor! In the day of trouble the LORD delivers him;",
                "the LORD protects him and keeps him alive; he is called blessed in the land; you do not give him up to the will of his enemies.",
                "The LORD sustains him on his sickbed; in his illness you restore him to full health.",
                "As for me, I said, \"O LORD, be gracious to me; heal me, for I have sinned against you!\"",
                "My enemies say of me in malice, \"When will he die, and his name perish?\"",
                "And when one comes to see me, he utters empty words, while his heart gathers iniquity; when he goes out, he tells it abroad.",
                "All who hate me whisper together about me; they imagine the worst for me.",
                "They say, \"A deadly thing is poured out on him; he will not rise again from where he lies.\"",
                "Even my close friend in whom I trusted, who ate my bread, has lifted his heel against me.",
                "But you, O LORD, be gracious to me, and raise me up, that I may repay them!",
                "By this I know that you delight in me: my enemy will not shout in triumph over me.",
                "But you have upheld me because of my integrity, and set me in your presence forever.",
                "Blessed be the LORD, the God of Israel, from everlasting to everlasting! Amen and Amen."
        );
    }
}
