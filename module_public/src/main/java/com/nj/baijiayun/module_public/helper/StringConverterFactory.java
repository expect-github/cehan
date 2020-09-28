package com.nj.baijiayun.module_public.helper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by YJ.Song on 2018/10/15.
 */

public class StringConverterFactory extends Converter.Factory {


    public StringConverterFactory() {
    }

    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new CppConverter<>();
    }

    public static class CppConverter<T> implements Converter<ResponseBody, T> {
        CppConverter() {
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            return (T) value.string();
        }
    }
}
