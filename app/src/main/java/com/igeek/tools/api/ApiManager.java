package com.igeek.tools.api;


import android.util.Log;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static volatile ApiManager apiManager;

    private ApiManager() {
    }

    public static ApiManager getInstance() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    private ConcurrentHashMap<String, ApiService> apiServiceMap = new ConcurrentHashMap<>();

    public <T extends ApiService> T getApiService(@NonNull Class<T> clazz) {
        ApiService apiService = apiServiceMap.get(clazz.getName());
        if (apiService == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    try {
                        String text = URLDecoder.decode(message, "utf-8");
                        Log.e("-----OKHttp-----", text);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Log.e("-----OKHttp-----", message);
                    }
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(OkHttpConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(OkHttpConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(OkHttpConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .callTimeout(OkHttpConfig.CALL_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(new ResponseInterceptor())
                    .addInterceptor(new HeaderInterceptor())
                    .build();

            Field field = null;
            try {
                field = clazz.getField("BASE_URL");
                field.setAccessible(true);
                Object o = field.get(null);
                if (o != null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(o.toString())
                            .client(okHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    apiService = retrofit.create(clazz);
                    apiServiceMap.put(clazz.getName(), apiService);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) apiService;
    }
}
