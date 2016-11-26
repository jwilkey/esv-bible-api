package com.tentkeep.esv.bible.models;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.Arrays;
import java.util.List;

public class VerseUnitConverter implements Converter<PassageQuery.Passage.VerseUnit> {
    @Override
    public PassageQuery.Passage.VerseUnit read(InputNode node) throws Exception {
        PassageQuery.Passage.VerseUnit verseUnit = new PassageQuery.Passage.VerseUnit();
        final StringBuilder sb = new StringBuilder(1024);
        readVerseUnitNode(sb, node, verseUnit);

        String text = generateText(sb);
        verseUnit.setText(text);

        return verseUnit;
    }

    private String generateText(StringBuilder sb) {
        String text = sb.toString();

        text = text.replaceAll("“", "\"");
        text = text.replaceAll("”", "\"");

        text = removeNode("bp", text, false);
        text = removeNode("ep", text, false);

        text = removeNode("woc", text, true);

        text = removeNode("f", text, false);

        text = text.trim();

        // formatting
        text = text.replaceAll("[ ]{2,}", " ");
        text = text.replaceAll("\n", "");

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

    @Override
    public void write(OutputNode node, PassageQuery.Passage.VerseUnit value) throws Exception {
    }

    private void readVerseUnitNode(StringBuilder sb, InputNode root, PassageQuery.Passage.VerseUnit verseUnit) throws Exception {
        List<String> ignoreNodes = Arrays.asList("marker", "begin-chapter");
        if (ignoreNodes.contains(root.getName())) return;

        if ("verse-num".equals(root.getName())) {
            verseUnit.setNumber(Integer.parseInt(root.getValue()));
            return;
        }

        if ("heading".equals(root.getName())) {
            verseUnit.setHeading(root.getValue());
            return;
        }

        if ("footnote".equals(root.getName())) {
            readFootnote(sb, root, verseUnit);
            return;
        }

        List<String> ignoreNodeNames = Arrays.asList("verse-unit");
        if (root.isElement() && !ignoreNodeNames.contains(root.getName())) {
//            System.out.println("ROOT: " + root.getName());
            sb.append("<").append(getNodeName(root.getName())).append(">");
        }

        String value = root.getValue();
        if (value != null) sb.append(value);

        InputNode node = root.getNext();
        while (node != null) {
            readVerseUnitNode(sb, node, verseUnit);
            // Each time a sub-tree is 'over', getValue() will deserialize the potentially upcoming free-text
            value = root.getValue();
            if (value != null) sb.append(value);
            node = root.getNext();
        }

        if (root.isElement() && !ignoreNodeNames.contains(root.getName())) {
            sb.append("</").append(getNodeName(root.getName())).append(">");
        }
    }

    private void readFootnote(StringBuilder sb, InputNode root, PassageQuery.Passage.VerseUnit verseUnit) throws Exception {
        String footnoteId = root.getAttribute("id").getValue().replace("f", "");
        sb.append("<f>").append(footnoteId).append("</f>");

        StringBuilder footnoteSb = new StringBuilder();
        concatNodesTree(footnoteSb, root, false);
        verseUnit.getFootnotes().add(new PassageQuery.Passage.VerseUnit.Footnote(footnoteId,footnoteSb.toString()));
    }

    private String getNodeName(String xmlName) {
        switch (xmlName) {
            case "begin-paragraph": return "bp";
            case "end-paragraph": return "ep";
            default: return xmlName;
        }
    }

    private void concatNodesTree(StringBuilder sb, InputNode root, boolean includeRootTag) throws Exception {
        if (root.isElement() && includeRootTag) {
            sb.append("<").append(root.getName()).append(">");
        }

        String value = root.getValue();
        if (value != null) sb.append(value);

        InputNode node = root.getNext();
        while (node != null) {
            concatNodesTree(sb, node, true);
            // Each time a sub-tree is 'over', getValue() will deserialize the potentially upcoming free-text
            value = root.getValue();
            if (value != null) sb.append(value);
            node = root.getNext();
        }

        if (root.isElement() && includeRootTag) {
            sb.append("</").append(root.getName()).append(">");
        }
    }
}
