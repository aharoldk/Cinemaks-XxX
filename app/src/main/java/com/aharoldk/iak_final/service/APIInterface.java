package com.aharoldk.iak_final.service;

import com.aharoldk.iak_final.model.Trailer;
import com.aharoldk.iak_final.pojo.Home;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("movie/now_playing")
    Call<Home> getAPINowPlaying(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") String page);

    @GET("movie/upcoming")
    Call<Home> getAPIComingSoon(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") String page);

    @GET("movie/{id}")
    Call<Trailer> getAPITrailer(@Path("id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String append_to_response);
}
