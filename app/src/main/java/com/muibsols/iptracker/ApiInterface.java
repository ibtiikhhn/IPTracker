package com.muibsols.iptracker;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/{ip}/{format}/")
        // specify the sub url for our base url
    Call<DataModel> getData(@Path("ip") String ip, @Path("format") String format);
}

