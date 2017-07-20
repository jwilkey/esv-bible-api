package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.tentkeep.esv.bible.mappers.MapHelpers.loadFixture;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class John1Test {

    private static PassageQuery actual;

    @BeforeClass
    public static void beforeAll() throws Exception {
        PassageMapper subject = new PassageMapper();
        actual = subject.map(loadFixture("john1.txt"));
    }

    @Test
    public void texts() {
        assertEquals("And he said to him, \"Truly, truly, I say to you, you will see heaven opened, and the angels of God ascending and descending on the Son of Man.\"",
                actual.getPassage().getVerses().get(50).getText());
    }
}
