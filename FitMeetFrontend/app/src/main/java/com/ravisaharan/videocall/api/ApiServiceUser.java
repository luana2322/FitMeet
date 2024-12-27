package com.ravisaharan.videocall.api;

import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceUser {
    @GET("/api/finduserbyid")
    Call<User> finduserbyid(@Query("userId") int coachId);

    @GET("/api/finduserbyname")
    Call<User> findByName(@Query("userName") String coachId);
}
