package com.igeek.tools.api.service;

import com.igeek.tools.api.ApiService;
import com.igeek.tools.api.HostConfig;
import com.igeek.tools.api.Result;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ShadowSkyService extends ApiService {

    String BASE_URL = HostConfig.DEFAULT_HOST;

    @FormUrlEncoded
    @POST("/auth/login")
    Observable<Result> login(@FieldMap Map<String, Object> body);

    @POST("/user/checkin")
    Observable<Result> checkIn();
}
