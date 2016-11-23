package com.tentkeep.esv.bible.services;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class EsvConverter<T, F> implements Converter {

    @Override
    public Object convert(Object value) throws IOException {
        return "foo";
    }

    public class EsvConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return new EsvConverter<T, F>();
        }
    }
}

