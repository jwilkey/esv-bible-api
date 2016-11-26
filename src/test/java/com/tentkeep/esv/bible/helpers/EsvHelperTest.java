package com.tentkeep.esv.bible.helpers;

import com.tentkeep.esv.bible.mappers.PassageMapper;
import com.tentkeep.esv.bible.models.PassageQuery;
import com.tentkeep.esv.bible.services.EsvService;
import com.tentkeep.esv.bible.services.ServiceProvider;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EsvHelperTest {

    @Mock ServiceProvider serviceProvider;
    @Mock PassageMapper passageMapper;

    @InjectMocks EsvHelper subject;

    @Mock private EsvService esvService;
    @Mock Call<ResponseBody> callResponse;

    @Before
    public void beforeEach() {
        when(serviceProvider.getEsvService()).thenReturn(esvService);
    }

    @Test
    public void fetch() throws Exception {
        when(esvService.search("the-key", "matthew", "crossway-xml-1.0")).thenReturn(callResponse);
        byte[] bytes = "esv response body".getBytes();
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text"), bytes);
        Response<ResponseBody> response = Response.success(responseBody);
        when(callResponse.execute()).thenReturn(response);

        PassageQuery passageQuery = new PassageQuery();
        when(passageMapper.map("esv response body")).thenReturn(passageQuery);

        assertSame(passageQuery, subject.fetch("the-key", "matthew"));
    }
}