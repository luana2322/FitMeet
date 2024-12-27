package com.ravisaharan.videocall.api;

import com.ravisaharan.videocall.dto.CoachDto;
import com.ravisaharan.videocall.dto.CoachSigupDto;
import com.ravisaharan.videocall.dto.StudentDto;
import com.ravisaharan.videocall.model.CoachNotification;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceCoach {

    // CREATE
    @POST("coach")
    Call<Coache> createCoach(@Body Coache coache);

    // READ ALL
    @GET("/getallcoach")
    Call<List<Coache>> getCoachs();

    @GET("/getcoachbyId")
    Call<Coache> getCoacheById(@Query("coachId") Long coachId);

    @GET("/getlistcoachchat")
    Call<List<Coache>> getListCoachChat(@Query("student_id") Long student_id);

    @GET("/getlistnotificationcoach")
    Call<List<CoachNotification>> getListCoachNotification(@Query("coach_id") Long coach_id);


    // UPDATE
    @PUT("coach/{id}")
    Call<Coache> updateCoach(@Path("id") int id, @Body Coache student);

    // DELETE
    @DELETE("coach/{id}")
    Call<Void> deleteCoach(@Path("id") int id);


    @POST("/coachlogin")
    Call<Coache> loginCoach(@Body CoachDto student);

    @POST("/coachsignup")
    Call<Coache> signUpCoach(@Body CoachSigupDto student);
}
