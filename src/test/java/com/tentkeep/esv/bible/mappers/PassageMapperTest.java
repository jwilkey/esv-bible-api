package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static com.tentkeep.esv.bible.mappers.MapHelpers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class PassageMapperTest {

    @InjectMocks PassageMapper subject;

    @Test
    public void map_matthew9_noOptions() throws Exception {
        PassageQuery actual = subject.map(loadFixture("matt9.xml"));

        assertEquals("Matthew 9:1-4", actual.getPassage().getReference());
        assertEquals(4, actual.getPassage().getVerses().size());

        expectedVerseNumbers(actual, 1,2,3,4);
        expectedOptions(actual, false, false, false, false);
        assertEquals("Jesus Heals a Paralytic", actual.getPassage().getVerses().get(0).getHeading());
        expectedVerseTexts(actual,
                "And getting into a boat he crossed over and came to his own city.",
                "And behold, some people brought to him a paralytic, lying on a bed. And when Jesus saw their faith, he said to the paralytic, \"Take heart, my son; your sins are forgiven.\"",
                "And behold, some of the scribes said to themselves, \"This man is blaspheming.\"",
                "But Jesus, knowing their thoughts, said, \"Why do you think evil in your hearts?"
        );
    }
}