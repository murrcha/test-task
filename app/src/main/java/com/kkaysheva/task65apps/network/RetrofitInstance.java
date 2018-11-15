package com.kkaysheva.task65apps.network;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitInstance
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class RetrofitInstance {

    private static final String BASE_URL = "http://gitlab.65apps.com/65gb/static/raw/master/";

    private static Retrofit retrofit;

    private static RetrofitInstance instance;

    private RetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder()
                                .setLenient()
                                .create()))
                .client(client.build())
                .build();
    }

    public static RetrofitInstance getInstance() {
        if (instance == null) {
            instance = new RetrofitInstance();
        }
        return instance;
    }

    public Gitlab65AppsApi getApi() {
        return retrofit.create(Gitlab65AppsApi.class);
    }
}
