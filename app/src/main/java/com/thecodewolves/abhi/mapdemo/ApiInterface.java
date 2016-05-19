package com.thecodewolves.abhi.mapdemo;

import com.thecodewolves.abhi.mapdemo.Model.NearByShopsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abhi on 17-05-2016.
 */
public interface ApiInterface {
    @GET("nearbysearch/json")
    Call<NearByShopsResponse> getNearByShops(@Query("location")String location,
                                             @Query("radius")int radius,
                                             @Query("type")String type,
                                             @Query("key")String ApiKey);
}
