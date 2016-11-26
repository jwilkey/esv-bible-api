package com.tentkeep.esv.bible.services;

import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

@Component
public class ServiceProvider {
    public EsvService getEsvService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.esvapi.org")
                .build();

        return retrofit.create(EsvService.class);
    }
}
