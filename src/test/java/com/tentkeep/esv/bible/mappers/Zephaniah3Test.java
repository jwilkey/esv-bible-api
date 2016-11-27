package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.tentkeep.esv.bible.mappers.MapHelpers.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class Zephaniah3Test {

    private static PassageQuery actual;

    @BeforeClass
    public static void beforeAll() throws Exception {
        PassageMapper subject = new PassageMapper();
        actual = subject.map(loadFixture("zephaniah3.txt"));
    }

    @Test
    public void topLevelProperties() {
        assertEquals("Zephaniah 3:10-20", actual.getPassage().getReference());
        expectedOptions(actual, false, false, false, false);
        assertEquals("The Holy Bible, English Standard Version copyright (c)2001 by Crossway Bibles, a division of Good News Publishers. Used by permission. All rights reserved. http://www.esv.org", actual.getCopyright());
    }

    @Test
    public void verseNumbers() {
        assertEquals(11, actual.getPassage().getVerses().size());
        expectedVerseNumbers(actual, 10,11,12,13,14,15,16,17,18,19,20);
    }

    @Test
    public void headings() {
        assertEquals("Israel's Joy and Restoration", actual.getPassage().getVerses().get(4).getHeading());
    }

    @Test
    public void texts() {
        expectedVerseTexts(actual,
                "From beyond the rivers of Cush my worshipers, the daughter of my dispersed ones, shall bring my offering.",
                "\"On that day you shall not be put to shame because of the deeds by which you have rebelled against me; for then I will remove from your midst your proudly exultant ones, and you shall no longer be haughty in my holy mountain.",
                "But I will leave in your midst a people humble and lowly. They shall seek refuge in the name of the LORD,",
                "those who are left in Israel; they shall do no injustice and speak no lies, nor shall there be found in their mouth a deceitful tongue. For they shall graze and lie down, and none shall make them afraid.\"",
                "Sing aloud, O daughter of Zion; shout, O Israel! Rejoice and exult with all your heart, O daughter of Jerusalem!",
                "The LORD has taken away the judgments against you; he has cleared away your enemies. The King of Israel, the LORD, is in your midst; you shall never again fear evil.",
                "On that day it shall be said to Jerusalem: \"Fear not, O Zion; let not your hands grow weak.",
                "The LORD your God is in your midst, a mighty one who will save; he will rejoice over you with gladness; he will quiet you by his love; he will exult over you with loud singing.",
                "I will gather those of you who mourn for the festival, so that you will no longer suffer reproach.",
                "Behold, at that time I will deal with all your oppressors. And I will save the lame and gather the outcast, and I will change their shame into praise and renown in all the earth.",
                "At that time I will bring you in, at the time when I gather you together; for I will make you renowned and praised among all the peoples of the earth, when I restore your fortunes before your eyes,\" says the LORD."
        );
    }

}
