package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.tentkeep.esv.bible.mappers.MapHelpers.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class Psalm9Test {

    private static PassageQuery actual;

    @BeforeClass
    public static void beforeAll() throws Exception {
        PassageMapper subject = new PassageMapper();
        actual = subject.map(loadFixture("psalm9.txt"));
    }

    @Test
    public void topLevelProperties() {
        assertEquals("Psalm 9", actual.getPassage().getReference());
        expectedOptions(actual, false, false, false, false);
        assertEquals("The Holy Bible, English Standard Version copyright (c)2001 by Crossway Bibles, a division of Good News Publishers. Used by permission. All rights reserved. http://www.esv.org", actual.getCopyright());
    }

    @Test
    public void verseNumbers() {
        assertEquals(20, actual.getPassage().getVerses().size());
        expectedVerseNumbers(actual, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
    }

    @Test
    public void headings() {
        assertEquals("I Will Recount Your Wonderful Deeds", actual.getPassage().getVerses().get(0).getHeading());
        assertEquals("To the choirmaster: according to Muth-labben. A Psalm of David.", actual.getPassage().getVerses().get(0).getSubheading());
    }

    @Test
    public void texts() {
        expectedVerseTexts(actual,
                "I will give thanks to the LORD with my whole heart; I will recount all of your wonderful deeds.",
                "I will be glad and exult in you; I will sing praise to your name, O Most High.",
                "When my enemies turn back, they stumble and perish before your presence.",
                "For you have maintained my just cause; you have sat on the throne, giving righteous judgment.",
                "You have rebuked the nations; you have made the wicked perish; you have blotted out their name forever and ever.",
                "The enemy came to an end in everlasting ruins; their cities you rooted out; the very memory of them has perished.",
                "But the LORD sits enthroned forever; he has established his throne for justice,",
                "and he judges the world with righteousness; he judges the peoples with uprightness.",
                "The LORD is a stronghold for the oppressed, a stronghold in times of trouble.",
                "And those who know your name put their trust in you, for you, O LORD, have not forsaken those who seek you.",
                "Sing praises to the LORD, who sits enthroned in Zion! Tell among the peoples his deeds!",
                "For he who avenges blood is mindful of them; he does not forget the cry of the afflicted.",
                "Be gracious to me, O LORD! See my affliction from those who hate me, O you who lift me up from the gates of death,",
                "that I may recount all your praises, that in the gates of the daughter of Zion I may rejoice in your salvation.",
                "The nations have sunk in the pit that they made; in the net that they hid, their own foot has been caught.",
                "The LORD has made himself known; he has executed judgment; the wicked are snared in the work of their own hands. Higgaion. Selah",
                "The wicked shall return to Sheol, all the nations that forget God.",
                "For the needy shall not always be forgotten, and the hope of the poor shall not perish forever.",
                "Arise, O LORD! Let not man prevail; let the nations be judged before you!",
                "Put them in fear, O LORD! Let the nations know that they are but men! Selah"
        );
    }
}
