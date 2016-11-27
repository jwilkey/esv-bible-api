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

        final StringBuilder sb = new StringBuilder();
        readVerseUnitNode(sb, node, verseUnit, true);
        verseUnit.setText(sb.toString());

        return verseUnit;
    }

    @Override
    public void write(OutputNode node, PassageQuery.Passage.VerseUnit value) throws Exception {
    }

    private void readVerseUnitNode(StringBuilder sb, InputNode root, PassageQuery.Passage.VerseUnit verseUnit, boolean ignoreRootNodeName) throws Exception {
        List<String> ignoreNodes = Arrays.asList("marker", "begin-chapter", "end-chapter", "begin-block-indent", "end-block-indent", "end-line");
        if (ignoreNodes.contains(root.getName())) return;

        if (!ignoreRootNodeName) {
            if ("verse-num".equals(root.getName())) {
                verseUnit.setNumber(Integer.parseInt(root.getValue()));
                return;
            }

            if ("heading".equals(root.getName())) {
                StringBuilder hBuilder = new StringBuilder();
                readVerseUnitNode(hBuilder, root, verseUnit, true);
                verseUnit.setHeading(hBuilder.toString().trim());
                return;
            }

            if ("subheading".equals(root.getName())) {
                StringBuilder shBuilder = new StringBuilder();
                readVerseUnitNode(shBuilder, root, verseUnit, true);
                verseUnit.setSubheading(shBuilder.toString().trim());
                return;
            }

            if ("footnote".equals(root.getName())) {
                readFootnote(sb, root, verseUnit);
                return;
            }

            if ("span".equals(root.getName())) {
                if ("divine-name".equals(root.getAttribute("esvApiClass").getValue())) {
                    sb.append(root.getValue().toUpperCase());
                    return;
                }
            }

            if ("begin-line".equals(root.getName())) {
                sb.append("\n");
                InputNode beginLineClass = root.getAttribute("esvApiClass");
                if (beginLineClass != null) {
                    String beginLineValue = beginLineClass.getValue();
                    if ("indent".equals(beginLineValue) || "psalm-doxology".equals(beginLineValue)) {
                        sb.append(" ");
                    }
                }
                return;
            }
        }

        String nodeName = getNodeName(root.getName());
        boolean shouldAppendNodeTag = shouldAppendNodeTag(root, ignoreRootNodeName, nodeName);

        if (shouldAppendNodeTag) {
//            System.out.println("ROOT: " + root.getName());
            sb.append("<").append(nodeName).append(">");
        }

        String value = root.getValue();
        if (value != null) sb.append(value);

        InputNode node = root.getNext();
        while (node != null) {
            readVerseUnitNode(sb, node, verseUnit, false);
            // Each time a sub-tree is 'over', getValue() will deserialize the potentially upcoming free-text
            value = root.getValue();
            if (value != null) sb.append(value);
            node = root.getNext();
        }

        if (shouldAppendNodeTag) {
            sb.append("</").append(nodeName).append(">");
        }
    }

    private boolean shouldAppendNodeTag(InputNode root, boolean ignoreRootNodeName, String nodeName) {
        return root.isElement() && !ignoreRootNodeName && nodeName != null;
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
            case "selah": return null;
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
