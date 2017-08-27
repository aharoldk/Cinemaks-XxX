package com.aharoldk.iak_final.service;

import com.aharoldk.iak_final.pojo.Movie.Home;
import com.aharoldk.iak_final.pojo.Review.Review;
import com.aharoldk.iak_final.pojo.Trailer.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("popular")
    Call<Home> getAPIPopular(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<Home> getAPITopRated(@Query("api_key") String apiKey);

    @GET("{id}/videos")
    Call<Trailer> getAPITrailer(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("{id}/reviews")
    Call<Review> getAPIReview(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

}
