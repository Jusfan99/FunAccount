package com.example.funaccount.retrofit;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//描述网络请求的接口

public interface GetUserData {
    @GET("userId")
    Observable<UserDataReception> getCall(@Query("userId") String userId);
}
