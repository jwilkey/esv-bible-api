package com.tentkeep.esv.bible.services;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static okhttp3.Credentials.basic;

@Component
public class ServiceProvider {
    @Value("${biblesorgkey}")
    private String biblesorgkey;

    public EsvService getEsvService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.esvapi.org")
                .build();

        return retrofit.create(EsvService.class);
    }

    public BiblesService getBiblesService() {
        AuthenticationInterceptor interceptor =
                new AuthenticationInterceptor(basic(biblesorgkey, "X"));

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://:X@bibles.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.newBuilder().addInterceptor(interceptor).build())
                .build();

        return retrofit.create(BiblesService.class);
    }

    public class AuthenticationInterceptor implements Interceptor {

        private String authToken;

        public AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Authorization", authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }
}
