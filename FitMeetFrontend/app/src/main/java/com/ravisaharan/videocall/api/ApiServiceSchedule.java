package com.ravisaharan.videocall.api;

import com.ravisaharan.videocall.model.Schedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServiceSchedule {    // CREATE
    @POST("schedule")
    Call<Schedule> createSchedule(@Body Schedule schedule);

    // READ ALL
    @GET("/getallschedule")
    Call<List<Schedule>> getSchedule();

    // READ BY ID
    @GET("schedule/{id}")
    Call<Schedule> getScheduleById(@Path("id") int id);

    // UPDATE
    @PUT("schedule/{id}")
    Call<Schedule> updateSchedule(@Path("id") int id, @Body Schedule schedule);

    // DELETE
    @DELETE("schedule/{id}")
    Call<Void> deleteSchedule(@Path("id") int id);
}
