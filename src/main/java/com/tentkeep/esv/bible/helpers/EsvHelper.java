package com.tentkeep.esv.bible.helpers;

import com.tentkeep.esv.bible.models.PassageQuery;
import com.tentkeep.esv.bible.services.EsvService;
import okhttp3.ResponseBody;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;

@Component
public class EsvHelper {
    public PassageQuery fetch(String esvApiKey, String query) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.esvapi.org")
                .build();

        EsvService service = retrofit.create(EsvService.class);

        Response response = service.search(esvApiKey, query, "crossway-xml-1.0").execute();
        ResponseBody responseBody = (ResponseBody) response.body();
        String s = new String(responseBody.bytes());
        s = s.replaceAll(" class=", " foo=");
        Serializer ser = new Persister(new AnnotationStrategy());
        PassageQuery p = ser.read(PassageQuery.class, s);
        return p;
    }
}
