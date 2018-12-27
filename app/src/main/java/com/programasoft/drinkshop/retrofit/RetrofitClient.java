package com.programasoft.drinkshop.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 16/12/2018.
 */

public class RetrofitClient {

    private static Retrofit retrofit=null;

    public static Retrofit getClient(String BaseUrl)
    { if(retrofit==null)
     {   Gson gson = new GsonBuilder()
             .setLenient()
             .create();

         retrofit=new Retrofit.Builder().baseUrl(BaseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).build();

     }
     return retrofit;
    }
}
