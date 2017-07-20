package com.tentkeep.esv.bible.services;

import com.tentkeep.esv.bible.models.BooksResponse;
import com.tentkeep.esv.bible.models.VersesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BiblesService {
    @GET("/v2/versions/{translation}/books.js")
    Call<BooksResponse> fetchBooks(@Path("translation") String translation);

    @GET("/v2/chapters/{translation}:{book}.{chapter}/verses.js")
    Call<VersesResponse> fetchVerses(@Path("translation") String translation,
                                     @Path("book") String book,
                                     @Path("chapter") String chapter);
}
