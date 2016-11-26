package com.tentkeep.esv.bible.mappers;

import com.tentkeep.esv.bible.models.PassageQuery;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.springframework.stereotype.Component;

@Component
public class PassageMapper {

    public PassageQuery map(String esvResponseString) throws Exception {
        esvResponseString = esvResponseString.replaceAll(" class=", " esvApiClass=");
        Serializer serializer = new Persister(new AnnotationStrategy());

        return serializer.read(PassageQuery.class, esvResponseString);
    }
}
