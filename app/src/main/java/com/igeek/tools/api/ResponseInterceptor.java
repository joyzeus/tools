package com.igeek.tools.api;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ResponseInterceptor implements Interceptor {

    final String COOKES_NAME = "Set-Cookie";
    public static String sid = "";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String header = response.header(COOKES_NAME);
        if (!TextUtils.isEmpty(header)) {
            Arrays.stream(header.split(";")).filter(h -> !TextUtils.isEmpty(h)).forEach(h -> {
                String[] strings = h.split("=");
                if (strings.length == 2) {
                    if ("sid".equals(strings[0].trim())) {
                        sid = strings[1];
                    }
                }
            });
        }
        return response;
    }
}
