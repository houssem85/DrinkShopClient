package com.programasoft.drinkshop.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programasoft.drinkshop.Utils.Common;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ASUS on 16/12/2018.
 */

public class RetrofitClient {

    private static Retrofit retrofit=null;

    public static Retrofit getClient(String BaseUrl)
    { if(retrofit==null)
    {   Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();

        okhttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request=chain.request();
                Request.Builder builder= request.newBuilder().header("auth","houssem");
                return chain.proceed(builder.build());
            }
        });


        okhttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(60, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS);
        Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Common.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(okhttpClientBuilder.build()).addConverterFactory(ScalarsConverterFactory.create());
        retrofit=builder.build();
    }

        return retrofit;
    }
}
