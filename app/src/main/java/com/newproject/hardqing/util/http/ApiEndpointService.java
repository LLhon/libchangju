package com.newproject.hardqing.util.http;


import com.newproject.hardqing.entity.SunSongRequest;
import com.newproject.hardqing.entity.SunSongResponse;
import com.newproject.hardqing.entity.SunTime;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiEndpointService {

    @GET("times")
    Call<SunTime> synchronizationTime();

    @POST("song")
    Call<SunSongResponse> getSongInfo(@Body SunSongRequest body);

    @POST("singer")
    Call<SunSongResponse> getSingerInfo(@Body SunSongRequest body);

}
