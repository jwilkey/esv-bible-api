package com.tentkeep.esv.bible.helpers;

import com.tentkeep.esv.bible.mappers.PassageMapper;
import com.tentkeep.esv.bible.models.PassageQuery;
import com.tentkeep.esv.bible.services.ServiceProvider;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;

@Component
public class EsvHelper {
    @Autowired private ServiceProvider serviceProvider;
    @Autowired private PassageMapper passageMapper;

    public PassageQuery fetch(String esvApiKey, String query) throws Exception {
        Response response = serviceProvider.getEsvService().search(esvApiKey, query, "crossway-xml-1.0").execute();
        ResponseBody responseBody = (ResponseBody) response.body();
        String responseBodyString = new String(responseBody.bytes());

        return passageMapper.map(responseBodyString);
    }
}
