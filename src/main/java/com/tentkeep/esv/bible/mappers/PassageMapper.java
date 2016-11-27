package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import com.tentkeep.esv.bible.models.VerseOptions;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.springframework.stereotype.Component;

@Component
public class PassageMapper {

    public PassageQuery map(String esvResponseString) throws Exception {
        esvResponseString = esvResponseString.replaceAll(" class=", " esvApiClass=");
        Serializer serializer = new Persister(new AnnotationStrategy());

        PassageQuery passageQuery = serializer.read(PassageQuery.class, esvResponseString);
        passageQuery.setOptions(new VerseOptions());

        passageQuery.getPassage().getVerses().forEach(v -> {
            String text = applyOptions(v.getText(), passageQuery.getOptions());
            v.setText(text);
            if (v.getSubheading() != null) {
                v.setSubheading(applyOptions(v.getSubheading(), passageQuery.getOptions()));
            }
        });

        return passageQuery;
    }

    private String applyOptions(String text, VerseOptions options) {
        text = text.replaceAll("“", "\"");
        text = text.replaceAll("”", "\"");

        text = removeNode("bp", text, false);
        text = removeNode("ep", text, false);

        text = removeNode("woc", text, true);

        text = removeNode("f", text, false);

        text = text.trim();

        // formatting
        text = text.replaceAll("\n", "");
        text = text.replaceAll("\t", " ");
        text = text.replaceAll("[ ]{2,}", " ");

        return text;
    }

    private String removeNode(String nodeName, String text, boolean keepContents) {
        if (keepContents) {
            String temp = text.replaceAll("<" + nodeName + ">", "");
            return temp.replaceAll("</" + nodeName + ">", "");
        } else {
            String regex = "<" + nodeName + ">*.</" + nodeName + ">";
            return text.replaceAll(regex, "");
        }
    }
}
