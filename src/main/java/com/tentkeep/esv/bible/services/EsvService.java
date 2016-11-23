package com.tentkeep.esv.bible.services;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EsvService {
    @GET("/v2/rest/passageQuery")
    Call<ResponseBody> search(@Query("key") String apiKey, @Query("passage") String query, @Query("output-format") String outputFormat);
}
